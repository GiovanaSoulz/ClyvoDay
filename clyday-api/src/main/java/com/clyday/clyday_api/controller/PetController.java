package com.clyday.clyday_api.controller;

import com.clyday.clyday_api.dto.PetDTO;
import com.clyday.clyday_api.entity.Pet;
import com.clyday.clyday_api.entity.Tutor;
import com.clyday.clyday_api.repository.PetRepository;
import com.clyday.clyday_api.service.AtividadeService;
import com.clyday.clyday_api.service.PetService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PetController {

    private final PetRepository petRepository;
    private final PetService petService;
    private final AtividadeService atividadeService;

    public PetController(
            PetRepository petRepository,
            PetService petService,
            AtividadeService atividadeService
    ) {
        this.petRepository = petRepository;
        this.petService = petService;
        this.atividadeService = atividadeService;
    }

    // =========================
    // LISTAR PETS
    // =========================

    @GetMapping("/pets")
    public String listarPets(Model model) {

        List<Pet> pets = petRepository.findAll();

        model.addAttribute("pets", pets);

        // sequência de cuidados (streak) de cada pet, usada nos cards da listagem
        model.addAttribute(
                "streaks",
                atividadeService.calcularStreakParaPets(pets)
        );

        return "pets";
    }

    // =========================
    // DETALHES DO PET
    // =========================

    @GetMapping("/pets/{id}")
    public String detalhes(@PathVariable Long id, Model model) {

        Pet pet = petService.buscarPorId(id);

        model.addAttribute("pet", pet);
        model.addAttribute(
                "streak",
                atividadeService.calcularStreakAtual(id)
        );

        return "pet-detalhes";
    }

    // =========================
    // TELA DE CADASTRO
    // =========================

    @GetMapping("/pets/novo")
    public String novoPet(Model model) {

        List<Tutor> tutores = petService.listarTutores();

        model.addAttribute("petDTO", new PetDTO());
        model.addAttribute("tutores", tutores);

        return "pet-form";
    }

    // =========================
    // CADASTRAR PET
    // =========================

    @PostMapping("/pets")
    public String cadastrarPet(
            @Valid @ModelAttribute("petDTO") PetDTO dto,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("tutores", petService.listarTutores());
            return "pet-form";
        }

        petService.salvar(dto);

        return "redirect:/pets";
    }

    // =========================
    // TELA DE EDIÇÃO
    // =========================

    @GetMapping("/pets/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {

        Pet pet = petService.buscarPorId(id);

        PetDTO dto = new PetDTO();
        dto.setNome(pet.getNome());
        dto.setEspecie(pet.getEspecie());
        dto.setTutorId(pet.getTutor() != null ? pet.getTutor().getId() : null);

        model.addAttribute("petId", id);
        model.addAttribute("petDTO", dto);
        model.addAttribute("tutores", petService.listarTutores());

        return "pet-editar";
    }

    // =========================
    // SALVAR EDIÇÃO
    // =========================

    @PostMapping("/pets/{id}/editar")
    public String editarSalvar(
            @PathVariable Long id,
            @Valid @ModelAttribute("petDTO") PetDTO dto,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("petId", id);
            model.addAttribute("tutores", petService.listarTutores());
            return "pet-editar";
        }

        petService.atualizar(id, dto);

        return "redirect:/pets/" + id;
    }

    // =========================
    // EXCLUIR PET
    // =========================

    @PostMapping("/pets/{id}/excluir")
    public String excluir(@PathVariable Long id) {

        petService.deletar(id);

        return "redirect:/pets";
    }
}
