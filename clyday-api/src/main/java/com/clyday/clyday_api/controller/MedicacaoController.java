package com.clyday.clyday_api.controller;

import com.clyday.clyday_api.dto.MedicacaoDTO;
import com.clyday.clyday_api.repository.PetRepository;
import com.clyday.clyday_api.service.MedicacaoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MedicacaoController {

    private final MedicacaoService medicacaoService;
    private final PetRepository petRepository;

    public MedicacaoController(
            MedicacaoService medicacaoService,
            PetRepository petRepository
    ) {
        this.medicacaoService = medicacaoService;
        this.petRepository = petRepository;
    }

    // =========================
    // LISTAR MEDICAÇÕES
    // =========================

    @GetMapping("/medicacoes")
    public String listar(Model model) {

        model.addAttribute(
                "medicacoes",
                medicacaoService.listar()
        );

        return "medicacoes";
    }

    // =========================
    // NOVA MEDICAÇÃO
    // =========================

    @GetMapping("/medicacoes/novo")
    public String novaMedicacao(Model model) {

        model.addAttribute("medicacaoDTO", new MedicacaoDTO());
        model.addAttribute("pets", petRepository.findAll());

        return "medicacao-form";
    }

    // =========================
    // CADASTRAR MEDICAÇÃO
    // =========================

    @PostMapping("/medicacoes")
    public String cadastrar(
            @Valid @ModelAttribute("medicacaoDTO") MedicacaoDTO dto,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pets", petRepository.findAll());
            return "medicacao-form";
        }

        medicacaoService.salvar(
                dto.getNome(),
                dto.getDosagem(),
                dto.getHorario(),
                dto.getObrigatoria(),
                dto.getPetId()
        );

        return "redirect:/medicacoes";
    }

    // =========================
    // DELETAR MEDICAÇÃO
    // =========================

    @PostMapping("/medicacoes/{id}/deletar")
    public String deletar(@PathVariable Long id) {

        medicacaoService.deletar(id);

        return "redirect:/medicacoes";
    }
}
