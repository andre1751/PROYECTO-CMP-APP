package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Huesped;

@Repository
public interface HuespedRepo extends JpaRepository<Huesped, Integer> {
    // Útil para buscar huéspedes por su documento de identidad
    Optional<Huesped> findByDocumentoId(String documentoId);
}