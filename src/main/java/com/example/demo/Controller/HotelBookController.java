package com.example.demo.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookingDto;
import com.example.demo.entity.Habitacion;
import com.example.demo.entity.Reserva;
import com.example.demo.services.HabitacionService;
import com.example.demo.services.ReservaService;

@RestController
@RequestMapping("/booking")
public class HotelBookController {

    ReservaService reservas = ReservaService.getInstance();
    HabitacionService habitacion = HabitacionService.getInstance();

    @GetMapping("/reservas")
    public List<Reserva> reservas() {
        return reservas.obtenerTodas();
    }

    @GetMapping("/buscarReserva/{id}")
    public Reserva buscarReserva(@PathVariable Integer Id) {

        return reservas.obtenerPorId(Id);
    }

    @GetMapping("/habitaciones")
    public List<Habitacion> listadeHabitaciones() {
        return habitacion.obtenerTodas();
    }

    @GetMapping("/cancelarReserva/{id}")
    public void cancelarReserva(@PathVariable Integer Id) {
        reservas.cancelarReserva(Id);
    }

    @PostMapping("/actualizarReserva/{id}")
    public void actualizarReserva(@RequestBody BookingDto nuevaReserva) {
        reservas.cancelarReserva(nuevaReserva.getDocumentID()); 
        reservas.crearNuevaReserva(nuevaReserva);
    }

    @PostMapping("/nueva_reserva")
    public void nuevaReserva(@RequestBody BookingDto nuevaReserva) {
        reservas.crearNuevaReserva(nuevaReserva);
    }
}