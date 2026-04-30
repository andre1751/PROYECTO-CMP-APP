package com.example.demo.Dto;
import java.util.Date;
public class BookingDto {
    public record BookingResume(
    String DocumentID,
    String guestName,
    String roomNumber,
    Date enterDate, 
    Date exitDate,
    @NotBlank(message = "El estado no puede estar vacío")
    @Pattern(regexp = "Activa|Cancelada|Finalizada", 
             message = "El estado debe ser: Activa, Cancelada o Finalizada")
    String bookingState,
   ) {}

  public BookingResume mapear(Reserva reserva) {
        return new BookingResume(
            reserva.getHuesped().getDocumentoId(),    // De tabla huespedes
            reserva.getHuesped().getNombre(),         // De tabla huespedes
            reserva.getHabitacion().getNumero(),      // De tabla habitaciones
            reserva.getFechaEntrada(),                // De tabla reservas[cite: 1]
            reserva.getFechaSalida(),                 // De tabla reservas[cite: 1]
            reserva.getEstadoReserva()                // Validado por el CHECK de SQL[cite: 1]
        );
    }

}
