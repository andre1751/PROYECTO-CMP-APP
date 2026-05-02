package com.example.demo.dto;
import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import com.example.demo.entity.Reserva;
 
public class BookingDto {
    public record BookingResume(
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

  public BookingResume mapear(Reserva reserva) {
        return new BookingResume(
            reserva.getHuesped().getDocumentoId(),    
            reserva.getHuesped().getNombre(),         
            reserva.getHabitacion().getNumero(),     
            reserva.getFechaEntrada(),                
            reserva.getFechaSalida(),                
            reserva.getEstadoReserva()                
        );
    }

}
