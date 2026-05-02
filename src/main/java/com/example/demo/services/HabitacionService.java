package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Habitacion;
import com.example.demo.repository.HabitacionRepo;

//TODO: hacer singleton
@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepo habitacionRepository;

    public List<Habitacion> obtenerTodas() {
        return habitacionRepository.findAll();
    }

    public Habitacion buscarPorNumeroHabitacion(String numHabitacion) {
        return habitacionRepository.findByNumero(numHabitacion)
                .orElseThrow(() -> new RuntimeException("La habitación " + numHabitacion + " no existe."));
    }

    public Habitacion actualizarEstado(String numero, String nuevoEstado) {
        Habitacion hab = buscarPorNumeroHabitacion(numero);
        hab.setEstado(nuevoEstado);
        return habitacionRepository.save(hab);
    }
}