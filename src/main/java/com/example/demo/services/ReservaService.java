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

//TODO: hacer Singleton
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

    // ... (tus otros métodos se mantienen igual)

    // 1. READ (Individual) - Crucial para ver detalles
    public BookingDto.BookingSummary obtenerPorId(Integer id) {
        return reservaRepository.findById(id)
                .map(mapper::mapear)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
    }

    public BookingDto.BookingSummary actualizarFechas(Integer id, java.time.LocalDate inicio, java.time.LocalDate fin) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la reserva para actualizar fechas."));
        if (fin.isBefore(inicio)) {
            throw new RuntimeException("La fecha de salida no puede ser anterior a la de entrada.");
        }

        reserva.setFechaEntrada(inicio);
        reserva.setFechaSalida(fin);
        return mapper.mapear(reservaRepository.save(reserva));
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
        String numHab = reserva.getHabitacion().getNumero(); //
        habitacionService.actualizarEstado(numHab, "Disponible"); //
    }



    public BookingDto.BookingSummary crearNuevaReserva(String cedula, String numHabitacion, Reserva datosReserva) {
        Huesped cliente = huespedService.obtenerORequerir(cedula);
        Habitacion cuarto = habitacionService.buscarPorNumeroHabitacion(numHabitacion);
        
        // Validación de disponibilidad
        if (!"Disponible".equalsIgnoreCase(cuarto.getEstado())) {
            throw new RuntimeException("La habitación no está disponible para reserva.");
        }
        
        datosReserva.setHuesped(cliente);
        datosReserva.setHabitacion(cuarto);

        Reserva guardada = reservaRepository.save(datosReserva);
        
        habitacionService.actualizarEstado(numHabitacion, "Ocupada");
        
        return mapper.mapear(guardada);
    }

    //TODO: implementar este metodo
    public BookingDto crearNuevaReserva(BookingDto dto) {
        return null;
    }
}