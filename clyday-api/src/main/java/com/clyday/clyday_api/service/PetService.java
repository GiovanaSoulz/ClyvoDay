package com.clyday.clyday_api.service;

import java.util.List;
import com.clyday.clyday_api.dto.PetDTO;
import com.clyday.clyday_api.entity.Pet;
import com.clyday.clyday_api.entity.Tutor;
import com.clyday.clyday_api.repository.PetRepository;
import com.clyday.clyday_api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public Pet salvar(PetDTO dto) {

        Tutor tutor = tutorRepository.findById(dto.getTutorId())
                .orElseThrow(() ->
                        new RuntimeException("Tutor não encontrado")
                );

        Pet pet = new Pet();

        pet.setNome(dto.getNome());
        pet.setEspecie(dto.getEspecie());
        pet.setTutor(tutor);

        return petRepository.save(pet);
    }
    public List<Tutor> listarTutores() {
        return tutorRepository.findAll();
    }
    public Page<Pet> listar(Pageable pageable) {

        return petRepository.findAll(pageable);
    }

    public Pet buscarPorId(Long id) {

        return petRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pet não encontrado")
                );
    }

    public Pet atualizar(Long id, PetDTO dto) {

        Pet pet = buscarPorId(id);

        Tutor tutor = tutorRepository.findById(dto.getTutorId())
                .orElseThrow(() ->
                        new RuntimeException("Tutor não encontrado")
                );

        pet.setNome(dto.getNome());
        pet.setEspecie(dto.getEspecie());
        pet.setTutor(tutor);

        return petRepository.save(pet);
    }

    public void deletar(Long id) {

        Pet pet = buscarPorId(id);

        petRepository.delete(pet);
    }
}