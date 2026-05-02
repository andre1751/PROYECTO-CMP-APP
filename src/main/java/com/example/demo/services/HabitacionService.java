package com.example.demo.services;

import com.example.demo.entity.Habitacion;
import com.example.demo.repository.HabitacionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


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