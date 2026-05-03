package com.example.demo.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.BookingDto;
import com.example.demo.Dto.BookingDto.BookingRequest;
import com.example.demo.Entity.Habitacion;
import com.example.demo.Entity.Reserva;
import com.example.demo.services.HabitacionService;
import com.example.demo.services.ReservaService;


@RestController
@RequestMapping("/booking")
public class HotelBookController {
    
    ReservaService reservas = new ReservaService();
    HabitacionService habitacion = new HabitacionService();


    //TODO: todas las reservas
    @GetMapping("/reservas")
    public List<Reserva> reservas() {
        return reservas.obtenerResumenReservas();
    }

    //TODO: buscar reserva
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

    //TODO: Actualizar estado reserva
    @GetMapping("/actualizarReserva/{id}")
    public void actualizarReserva(@PathVariable Integer Id, BookingRequest nuevaReserva) {
        reservas.cancelarReserva(Id);
        reservas.crearNuevaReserva(nuevaReserva);
    }

    //TODO: crear nueva reserva
    @PostMapping("/nueva_reserva")
    public void nuevaReserva(@RequestBody BookingRequest nuevaReserva) {
        // nuevaReserva
        reservas.crearNuevaReserva(nuevaReserva);
    }
}