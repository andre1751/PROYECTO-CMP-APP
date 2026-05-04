package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Huesped;
import com.example.demo.repository.HuespedRepo;

import lombok.Data;

@Service
@Data
public class HuespedService {

    @Autowired
    private HuespedRepo huespedRepository;

    public Huesped obtenerHuesped(Integer documentoId) {
        return huespedRepository.findByDocumentoId(documentoId)
                .orElseThrow(() -> new RuntimeException("Huésped con documento " + documentoId + " no encontrado."));
    }

    public Huesped registrar(Huesped huesped) {
        return huespedRepository.save(huesped);
    }
}