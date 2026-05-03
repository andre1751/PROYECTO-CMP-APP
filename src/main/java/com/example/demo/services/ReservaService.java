package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.BookingRequest; 
import com.example.demo.entity.Reserva;
import com.example.demo.entity.Huesped;
import com.example.demo.entity.Habitacion;
import com.example.demo.repository.ReservaRepo;

@Service

import com.example.demo.dto.BookingDto.BookingRequest;
public class ReservaService {
    private static ReservaService instance;

    @Autowired
    private ReservaRepo reservaRepository;

    @Autowired
    private HuespedService huespedService;

    @Autowired
    private HabitacionService habitacionService;

     private ReservaService() {
    }
    public static ReservaService getInstance() {
        if (instance == null) {
            instance = new ReservaService();
        }
        return instance;
    }
    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public Reserva obtenerPorId(Integer id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
    }

    public Reserva actualizarFechas(Integer id, java.time.LocalDate inicio, java.time.LocalDate fin) {
        Reserva reserva = obtenerPorId(id);
        
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha de salida no puede ser anterior a la de entrada.");
        }

        reserva.setFechaEntrada(inicio);
        reserva.setFechaSalida(fin);
        return reservaRepository.save(reserva);
    }

    public Reserva actualizarEstadoReserva(Integer reservaId, String nuevoEstado) {
        Reserva reserva = obtenerPorId(reservaId);
        reserva.setEstadoReserva(nuevoEstado);
        return reservaRepository.save(reserva);
    }

    public void cancelarReserva(Integer id) {
        actualizarEstadoReserva(id, "Cancelada");
        liberarHabitacionDeReserva(id);
    }

    public void liberarHabitacionDeReserva(Integer reservaId) {
        Reserva reserva = obtenerPorId(reservaId);
        String numHab = reserva.getHabitacion().getNumero();
        habitacionService.actualizarEstado(numHab, "Disponible");
    }

    public Reserva crearNuevaReserva(BookingRequest request) {
        Huesped cliente = huespedService.obtenerHuesped(request.getDocumentID());
        Habitacion cuarto = habitacionService.buscarPorNumero(request.getRoomNumber());

        if (!"Disponible".equalsIgnoreCase(cuarto.getEstado())) {
            throw new RuntimeException("La habitación no está disponible.");
        }

        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setHuesped(cliente);
        nuevaReserva.setHabitacion(cuarto);
        nuevaReserva.setFechaEntrada(request.getEnterDate());
        nuevaReserva.setFechaSalida(request.getExitDate());
        nuevaReserva.setEstadoReserva(request.getBookingState() != null ? request.getBookingState() : "Confirmada");

        Reserva guardada = reservaRepository.save(nuevaReserva);
        habitacionService.actualizarEstado(request.getRoomNumber(), "Ocupada");

        return guardada;
    }
    //NOTE: Funcion de filtrar reserva por estado 
    public List<Reserva> obtenerReservasPorEstado(String estado) {
    if (estado == null || estado.trim().isEmpty()) {
        throw new RuntimeException("El estado de búsqueda no puede estar vacío.");
    }
    return reservaRepository.findByEstadoReserva(estado);
}

}