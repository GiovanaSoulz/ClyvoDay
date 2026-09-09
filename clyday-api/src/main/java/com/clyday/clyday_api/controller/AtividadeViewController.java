package com.clyday.clyday_api.controller;

import com.clyday.clyday_api.dto.AtividadeDTO;
import com.clyday.clyday_api.entity.Pet;
import com.clyday.clyday_api.service.AtividadeService;
import com.clyday.clyday_api.service.PetService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AtividadeViewController {

    private final AtividadeService atividadeService;
    private final PetService petService;

    public AtividadeViewController(
            AtividadeService atividadeService,
            PetService petService
    ) {
        this.atividadeService = atividadeService;
        this.petService = petService;
    }

    @GetMapping("/atividades/nova")
    public String novaAtividade(
            @RequestParam Long petId,
            Model model
    ) {

        Pet pet = petService.buscarPorId(petId);

        AtividadeDTO dto = new AtividadeDTO();
        dto.setPetId(petId);

        model.addAttribute("atividadeDTO", dto);
        model.addAttribute("pet", pet);

        return "atividade-form";
    }

    @PostMapping("/atividades/cadastrar")
    public String cadastrar(
            @Valid @ModelAttribute("atividadeDTO") AtividadeDTO dto,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {

            Pet pet = petService.buscarPorId(dto.getPetId());
            model.addAttribute("pet", pet);

            return "atividade-form";
        }

        atividadeService.salvar(dto);

        return "redirect:/atividades/pet/" + dto.getPetId() + "/view";
    }

    @GetMapping("/atividades/pet/{petId}/view")
    public String listar(
            @PathVariable Long petId,
            Model model
    ) {

        Pet pet = petService.buscarPorId(petId);

        model.addAttribute(
                "pet",
                pet
        );

        model.addAttribute(
                "atividades",
                atividadeService.listarPorPet(petId)
        );

        return "atividades";
    }
}