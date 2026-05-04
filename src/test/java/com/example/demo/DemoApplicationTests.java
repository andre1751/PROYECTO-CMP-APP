
package com.example.demo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.BookingDto;
import com.example.demo.entity.Reserva;
import com.example.demo.services.ReservaService;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ReservaService reservaService;

    @Test
    void verificarCreacionDeReserva() {
        // 1. Datos de prueba
        String dniHuesped = "1234567890";
        String numHabitacion = "101";

        BookingDto datosReserva = new BookingDto.Builder()
                .documentID(dniHuesped)
                .roomNumber(numHabitacion)
                .build();

        Reserva tempReserva = new Reserva();

        // IMPORTANTE: Añadir las fechas que faltaban
        tempReserva.setFechaEntrada(LocalDate.now());
        tempReserva.setFechaSalida(LocalDate.now().plusDays(3)); // Reserva de 3 días
        tempReserva.setEstadoReserva("Activa");

        // 2. Ejecutar la lógica del servicio
        Reserva resultado = reservaService.crearNuevaReserva(datosReserva);

        // 3. Verificaciones (Assertions)
        assertNotNull(resultado);
        assertEquals(dniHuesped, resultado.getId());

        // Verificamos que las fechas se procesaron correctamente en el resultado
        assertNotNull(resultado.getFechaEntrada());
        assertEquals("Ocupada", resultado.getEstadoReserva());

        System.out.println("¡Prueba exitosa con fechas incluidas!");

        // Ejemplo de como crear un BookingDto:
        BookingDto ejemplBookingDto = new BookingDto.Builder()
                .documentID("100101021") // valores de ejemplo, pones los de verdad
                .bookingState("la wea esa")
                .enterDate(null)
                .exitDate(null)
                .guestName("juanin")
                .build(); // esto es lo más importante,
        // que después de poner los datos, pongas el .build()
    }
}