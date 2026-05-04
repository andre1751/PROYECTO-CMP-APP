package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.BookingDto;
import com.example.demo.Entity.Habitacion;
import com.example.demo.Entity.Huesped;
import com.example.demo.services.HabitacionService;
import com.example.demo.services.HuespedService;
import com.example.demo.services.ReservaService;

@RestController
@RequestMapping("/booking")
public class HotelBookController {

    private final ReservaService reservas;
    private final HabitacionService habitacionService;
    private final HuespedService huespedService;

    public HotelBookController(ReservaService reservas, HabitacionService habitacionService,
            HuespedService huespedService) {
        this.reservas = reservas;
        this.habitacionService = habitacionService;
        this.huespedService = huespedService;
    }

    @GetMapping("/reservas")
    public ResponseEntity<?> reservas() {
        try {
            List<BookingDto.BookingSummary> result = reservas.obtenerResumenReservas();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener las reservas", "message", e.getMessage()));
        }
    }

    @GetMapping("/buscarReserva/{id}")
    public ResponseEntity<?> buscarReserva(@PathVariable Integer id) {
        try {
            BookingDto.BookingSummary result = reservas.obtenerPorId(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Reserva no encontrada", "message", e.getMessage()));
        }
    }

    @PostMapping("/nueva_reserva")
    public ResponseEntity<?> nuevaReserva(@RequestBody BookingDto.BookingRequest nuevaReserva) {
        try {
            BookingDto.BookingSummary result = reservas.crearNuevaReserva(nuevaReserva);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Datos inválidos para la reserva", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al crear la reserva", "message", e.getMessage()));
        }
    }

    @PutMapping("/actualizarReserva/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable Integer id,
            @RequestBody BookingDto.BookingRequest nuevaReserva) {
        try {
            BookingDto.BookingSummary result = reservas.actualizarReserva(id, nuevaReserva);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Datos inválidos para actualizar la reserva", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar la reserva", "message", e.getMessage()));
        }
    }

    @RequestMapping(value = "/cancelarReserva/{id}", method = { RequestMethod.GET, RequestMethod.DELETE })
    public ResponseEntity<?> cancelarReserva(@PathVariable Integer id) {
        try {
            reservas.cancelarReserva(id);
            return ResponseEntity.ok(Map.of("message", "Reserva cancelada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al cancelar la reserva", "message", e.getMessage()));
        }
    }

    @GetMapping("/habitaciones")
    public ResponseEntity<?> listadeHabitaciones() {
        try {
            List<Habitacion> result = habitacionService.obtenerTodas();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener las habitaciones", "message", e.getMessage()));
        }
    }

    @GetMapping("/huespedes")
    public ResponseEntity<?> listadeHuespedes() {
        try {
            List<Huesped> result = huespedService.obtenerTodos();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener los huéspedes", "message", e.getMessage()));
        }
    }
}