package com.example.demo.Entity;

@Entity
@Table(name = "huespedes")
public class Huesped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(name = "documento_id", length = 20, unique = true, nullable = false)
    private String documentoId;

    @Column(length = 100)
    private String email;
}
