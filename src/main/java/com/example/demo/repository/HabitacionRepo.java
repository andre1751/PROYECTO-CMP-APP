package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Habitacion;

@Repository
public interface HabitacionRepo extends JpaRepository<Habitacion, Integer> {
    Optional<Habitacion> findByNumero(String numero);
}