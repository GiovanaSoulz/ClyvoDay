
package com.clyday.clyday_api.controller;

import com.clyday.clyday_api.dto.AtividadeDTO;
import com.clyday.clyday_api.entity.Atividade;
import com.clyday.clyday_api.service.AtividadeService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService service;

    @PostMapping
    public ResponseEntity<Atividade> cadastrar(
            @Valid @RequestBody AtividadeDTO dto
    ) {

        Atividade atividade = service.salvar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(atividade);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Atividade>> listarPorPet(
            @PathVariable Long petId
    ) {

        return ResponseEntity.ok(
                service.listarPorPet(petId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atividade> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}

