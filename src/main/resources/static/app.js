const bookingForm = document.getElementById('create-reservation-form');
const roomsTable = document.querySelector('#rooms-table');
const reservationsTable = document.querySelector('#reservations-table');
const toastContainer = document.getElementById('toast-container');

// Modal elements
const modalUpdate = document.getElementById('modal-update');
const modalCancel = document.getElementById('modal-cancel');
const modalReserveId = document.getElementById('modal-reserve-id');
const modalCancelId = document.getElementById('modal-cancel-id');
const modalNewState = document.getElementById('modal-new-state');

let currentReserveId = null;

document.addEventListener('DOMContentLoaded', () => {
    loadRooms();
    loadReservations();
});

bookingForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(bookingForm);
    const data = {
        guestName: formData.get('guestName'),
        documentID: formData.get('documentID'),
        roomNumber: formData.get('roomNumber'),
        enterDate: formData.get('enterDate'),
        exitDate: formData.get('exitDate'),
        bookingState: formData.get('bookingState')
    };

    try {
        await requestJson('/booking/nueva_reserva', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        showToast('Reserva creada correctamente.', 'success');
        bookingForm.reset();
        await loadReservations();
        await loadRooms();
    } catch (err) {
        console.error(err);
        showToast(err.message, 'error');
    }
});

async function requestJson(url, options) {
    const response = await fetch(url, options);
    if (!response.ok) {
        let errorMessage = 'Error en la solicitud';
        try {
            const errorData = await response.json();
            errorMessage = errorData.message || errorData.error || JSON.stringify(errorData);
        } catch {
            errorMessage = await response.text() || `Error ${response.status}`;
        }
        throw new Error(errorMessage);
    }
    return response.json();
}

function showToast(message, type = 'info') {
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;

    const icon = type === 'success' ? '✅' : type === 'error' ? '❌' : type === 'warning' ? '⚠️' : 'ℹ️';

    toast.innerHTML = `
        <div class="toast-icon">${icon}</div>
        <div class="toast-body">
            <div class="toast-title">${type === 'success' ? 'Éxito' : type === 'error' ? 'Error' : type === 'warning' ? 'Advertencia' : 'Información'}</div>
            <div class="toast-msg">${message}</div>
        </div>
        <button class="toast-close" onclick="this.parentElement.remove()">×</button>
    `;

    toastContainer.appendChild(toast);

    setTimeout(() => {
        toast.classList.add('removing');
        setTimeout(() => toast.remove(), 280);
    }, 5000);
}

async function loadRooms() {
    try {
        const rooms = await requestJson('/booking/habitaciones');
        roomsTable.innerHTML = rooms.length === 0
            ? `<div class="table-empty-state">
                <div class="empty-icon">🏨</div>
                <p>No hay habitaciones registradas</p>
              </div>`
            : rooms
                .map(room => `
                    <tr>
                        <td>${room.id}</td>
                        <td>${room.numero}</td>
                        <td><span class="estado-badge estado-${room.estado}">${room.estado}</span></td>
                    </tr>`)
                .join('');
    } catch (err) {
        console.error(err);
        roomsTable.innerHTML = `
            <div class="table-error-state">
                <div class="err-icon">⚠️</div>
                <p>No se pudieron cargar las habitaciones.</p>
                <button onclick="loadRooms()">Reintentar</button>
            </div>`;
        showToast('No se pudieron cargar las habitaciones. Intenta actualizar.', 'error');
    }
}

async function loadReservations() {
    try {
        const reservations = await requestJson('/booking/reservas');
        reservationsTable.innerHTML = reservations.length === 0
            ? `<div class="table-empty-state">
                <div class="empty-icon">📅</div>
                <p>No hay reservas registradas</p>
              </div>`
            : reservations
                .map(reserve => `
                    <tr>
                        <td>${reserve.id}</td>
                        <td>${reserve.guestName}</td>
                        <td>${reserve.documentID}</td>
                        <td>${reserve.roomNumber}</td>
                        <td>${reserve.enterDate}</td>
                        <td>${reserve.exitDate}</td>
                        <td><span class="estado-badge estado-${reserve.bookingState}">${reserve.bookingState}</span></td>
                        <td>
                            <button class="primary-button" onclick="openCancelModal(${reserve.id})">Cancelar</button>
                            <button class="primary-button" onclick="openUpdateModal(${reserve.id})">Actualizar</button>
                        </td>
                    </tr>`)
                .join('');
    } catch (err) {
        console.error(err);
        reservationsTable.innerHTML = `
            <div class="table-error-state">
                <div class="err-icon">⚠️</div>
                <p>No se pudieron cargar las reservas.</p>
                <button onclick="loadReservations()">Reintentar</button>
            </div>`;
        showToast('No se pudieron cargar las reservas. Intenta actualizar.', 'error');
    }
}

function openUpdateModal(id) {
    currentReserveId = id;
    modalReserveId.textContent = id;
    modalUpdate.classList.add('open');
}

function closeUpdateModal() {
    modalUpdate.classList.remove('open');
    currentReserveId = null;
}

async function confirmUpdate() {
    const nuevoEstado = modalNewState.value;
    if (!nuevoEstado) {
        showToast('Selecciona un estado válido.', 'warning');
        return;
    }
    try {
        await requestJson(`/booking/actualizarReserva/${currentReserveId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ bookingState: nuevoEstado })
        });
        showToast('Reserva actualizada correctamente.', 'success');
        closeUpdateModal();
        await loadReservations();
        await loadRooms();
    } catch (err) {
        console.error(err);
        showToast(err.message, 'error');
    }
}

function openCancelModal(id) {
    currentReserveId = id;
    modalCancelId.textContent = id;
    modalCancel.classList.add('open');
}

function closeCancelModal() {
    modalCancel.classList.remove('open');
    currentReserveId = null;
}

async function confirmCancel() {
    try {
        await fetch(`/booking/cancelarReserva/${currentReserveId}`, { method: 'DELETE' });
        showToast('Reserva cancelada correctamente.', 'success');
        closeCancelModal();
        await loadReservations();
        await loadRooms();
    } catch (err) {
        console.error(err);
        showToast('No se pudo cancelar la reserva. Intenta nuevamente.', 'error');
    }
}