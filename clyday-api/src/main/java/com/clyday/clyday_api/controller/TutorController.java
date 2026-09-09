package com.clyday.clyday_api.controller;



import com.clyday.clyday_api.dto.TutorDTO;
import com.clyday.clyday_api.entity.Tutor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clyday.clyday_api.service.TutorService;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService service;

    @PostMapping
    public ResponseEntity<Tutor> salvar(
            @Valid @RequestBody TutorDTO dto
    ) {
        return ResponseEntity.ok(service.salvar(dto));
    }



    @GetMapping
    public ResponseEntity<Page<Tutor>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutor> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @GetMapping("/buscar")
    public ResponseEntity<Page<Tutor>> buscarPorNome(
            @RequestParam String nome,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                service.buscarPorNome(nome, pageable)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
