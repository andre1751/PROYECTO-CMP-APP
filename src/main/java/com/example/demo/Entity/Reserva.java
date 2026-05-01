package com.example.demo.Entity;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_huesped", nullable = false) 
    private Huesped huesped;

    @ManyToOne
    @JoinColumn(name = "id_habitacion", nullable = false) 
    private Habitacion habitacion;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Column(name = "estado_reserva", length = 20)
    private String estadoReserva = "Activa"; 
    
}
