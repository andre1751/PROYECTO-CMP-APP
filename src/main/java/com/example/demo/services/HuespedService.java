package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Huesped;
import com.example.demo.repository.HuespedRepo;

@Service
public class HuespedService {

    @Autowired
    private HuespedRepo huespedRepository;

    public Huesped obtenerORequerir(Integer documentoId, String nombre) {
        return huespedRepository.findByDocumentoId(documentoId)
                .orElseGet(() -> registrar(crearHuesped(documentoId, nombre)));
    }

    public Huesped registrar(Huesped huesped) {
        return huespedRepository.save(huesped);
    }

    public List<Huesped> obtenerTodos() {
        return huespedRepository.findAll();
    }

    private Huesped crearHuesped(Integer documentoId, String nombre) {
        Huesped huesped = new Huesped();
        huesped.setNombre(nombre);
        huesped.setDocumentoId(documentoId);
        return huesped;
    }
}