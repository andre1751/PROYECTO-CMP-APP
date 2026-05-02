package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Reserva;

@Repository
public interface ReservaRepo extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByHuespedId(Integer huespedId);
    List<Reserva> findByEstadoReserva(String estadoReserva);
}