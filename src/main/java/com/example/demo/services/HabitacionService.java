package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Habitacion;
import com.example.demo.repository.HabitacionRepo;


@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepo habitacionRepository;

    public List<Habitacion> obtenerTodas() {
        return habitacionRepository.findAll();
    }
    public Habitacion buscarPorNumero(String numero) {
        return habitacionRepository.findByNumero(numero)
                .orElseThrow(() -> new RuntimeException("La habitación " + numero + " no existe."));
    }

    public Habitacion actualizarEstado(String numero, String nuevoEstado) {
        Habitacion hab = buscarPorNumero(numero);
        hab.setEstado(nuevoEstado);
        return habitacionRepository.save(hab);
    }
}