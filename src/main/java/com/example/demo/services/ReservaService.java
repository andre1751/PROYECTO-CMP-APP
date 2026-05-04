package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.BookingDto;
import com.example.demo.Entity.Habitacion;
import com.example.demo.Entity.Huesped;
import com.example.demo.Entity.Reserva;
import com.example.demo.repository.ReservaRepo;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepo reservaRepository;

    @Autowired
    private HuespedService huespedService;

    @Autowired
    private HabitacionService habitacionService;

    private final BookingDto mapper = new BookingDto();

    public List<BookingDto.BookingSummary> obtenerResumenReservas() {
        return reservaRepository.findAll()
                .stream()
                .map(mapper::mapear)
                .collect(Collectors.toList());
    }

    public BookingDto.BookingSummary obtenerPorId(Integer id) {
        return reservaRepository.findById(id)
                .map(mapper::mapear)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
    }

    public BookingDto.BookingSummary crearNuevaReserva(BookingDto.BookingRequest request) {
        if (request.getDocumentID() == null) {
            throw new RuntimeException("El documento de identidad es obligatorio.");
        }
        if (request.getRoomNumber() == null || request.getRoomNumber().isBlank()) {
            throw new RuntimeException("El número de habitación es obligatorio.");
        }
        if (request.getEnterDate() == null || request.getExitDate() == null) {
            throw new RuntimeException("Las fechas de entrada y salida son obligatorias.");
        }
        if (request.getExitDate().isBefore(request.getEnterDate())) {
            throw new RuntimeException("La fecha de salida no puede ser anterior a la de entrada.");
        }

        Huesped cliente = huespedService.obtenerORequerir(request.getDocumentID(), request.getGuestName());
        Habitacion cuarto = habitacionService.buscarPorNumeroHabitacion(request.getRoomNumber());

        if (!"Disponible".equalsIgnoreCase(cuarto.getEstado())) {
            throw new RuntimeException("La habitación no está disponible para reserva.");
        }

        Reserva reserva = mapper.toReserva(request, cliente, cuarto);
        Reserva guardada = reservaRepository.save(reserva);
        habitacionService.actualizarEstado(request.getRoomNumber(), "Ocupada");
        return mapper.mapear(guardada);
    }

    public BookingDto.BookingSummary actualizarReserva(Integer id, BookingDto.BookingRequest request) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la reserva para actualizar."));

        if (request.getEnterDate() != null && request.getExitDate() != null) {
            if (request.getExitDate().isBefore(request.getEnterDate())) {
                throw new RuntimeException("La fecha de salida no puede ser anterior a la de entrada.");
            }
            reserva.setFechaEntrada(request.getEnterDate());
            reserva.setFechaSalida(request.getExitDate());
        }

        if (request.getBookingState() != null && !request.getBookingState().isBlank()) {
            reserva.setEstadoReserva(request.getBookingState());
            if ("Cancelada".equalsIgnoreCase(request.getBookingState())) {
                liberarHabitacionDeReserva(id);
            }
        }

        Reserva actualizada = reservaRepository.save(reserva);
        return mapper.mapear(actualizada);
    }

    public void cancelarReserva(Integer id) {
        actualizarEstadoReserva(id, "Cancelada");
        liberarHabitacionDeReserva(id);
    }

    public BookingDto.BookingSummary actualizarEstadoReserva(Integer reservaId, String nuevoEstado) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + reservaId));
        reserva.setEstadoReserva(nuevoEstado);
        Reserva actualizada = reservaRepository.save(reserva);
        return mapper.mapear(actualizada);
    }

    public void liberarHabitacionDeReserva(Integer reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("No se puede liberar: Reserva inexistente."));
        String numHab = reserva.getHabitacion().getNumero();
        habitacionService.actualizarEstado(numHab, "Disponible");
    }

    public BookingDto.BookingSummary crearNuevaReserva(String cedula, String numHabitacion, Reserva datosReserva) {
        Huesped cliente = huespedService.obtenerORequerir(Integer.valueOf(cedula), datosReserva.getHuesped().getNombre());
        Habitacion cuarto = habitacionService.buscarPorNumeroHabitacion(numHabitacion);

        if (!"Disponible".equalsIgnoreCase(cuarto.getEstado())) {
            throw new RuntimeException("La habitación no está disponible para reserva.");
        }

        datosReserva.setHuesped(cliente);
        datosReserva.setHabitacion(cuarto);

        Reserva guardada = reservaRepository.save(datosReserva);
        habitacionService.actualizarEstado(numHabitacion, "Ocupada");
        return mapper.mapear(guardada);
    }
}