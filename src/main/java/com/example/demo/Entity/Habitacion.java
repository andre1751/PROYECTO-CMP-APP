package com.example.Entity;

@Entity
@Table(name = "habitaciones")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Integer porque es SERIAL en SQL

    @Column(length = 10, unique = true, nullable = false)
    private String numero;

    @Column(length = 20)
    private String estado = "Disponible"; // Valor por defecto
}
