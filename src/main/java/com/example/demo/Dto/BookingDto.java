package com.example.demo.Dto;
import java.time.LocalDate;

import com.example.demo.Entity.Reserva;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
 
//TODO: eliminar estructura record, getters y setters de cada cosa
public class BookingDto {
    public record BookingSummary(
    String DocumentID,
    String guestName,
    String roomNumber,
    LocalDate enterDate, 
    LocalDate exitDate,
    @NotBlank(message = "El estado no puede estar vacío")
    @Pattern(regexp = "Activa|Cancelada|Finalizada", 
             message = "El estado debe ser: Activa, Cancelada o Finalizada")
    String bookingState
   ) {}


    public BookingSummary mapear(Reserva reserva) {
        return new BookingSummary(
            reserva.getHuesped().getDocumentoId(),    
            reserva.getHuesped().getNombre(),         
            reserva.getHabitacion().getNumero(),     
            reserva.getFechaEntrada(),                
            reserva.getFechaSalida(),                
            reserva.getEstadoReserva()                
        );
    }

}
