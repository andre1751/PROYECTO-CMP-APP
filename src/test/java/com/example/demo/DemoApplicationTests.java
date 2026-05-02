
package com.example.demo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Dto.BookingDto;
import com.example.demo.Entity.Reserva;
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
    
    Reserva tempReserva = new Reserva();
    
    // IMPORTANTE: Añadir las fechas que faltaban
    tempReserva.setFechaEntrada(LocalDate.now()); 
    tempReserva.setFechaSalida(LocalDate.now().plusDays(3)); // Reserva de 3 días
    tempReserva.setEstadoReserva("Activa");

    // 2. Ejecutar la lógica del servicio
    BookingDto.BookingResume resultado = reservaService.crearNuevaReserva(dniHuesped, numHabitacion, tempReserva);

    // 3. Verificaciones (Assertions)
    assertNotNull(resultado);
    assertEquals(dniHuesped, resultado.DocumentID());
    
    // Verificamos que las fechas se procesaron correctamente en el resultado
    assertNotNull(resultado.enterDate()); 
    assertEquals("Ocupada", resultado.bookingState()); 
    
    System.out.println("¡Prueba exitosa con fechas incluidas!");
}
}