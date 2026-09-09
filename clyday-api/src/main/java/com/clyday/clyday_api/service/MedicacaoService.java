package com.clyday.clyday_api.service;

import com.clyday.clyday_api.entity.Medicacao;
import com.clyday.clyday_api.entity.Pet;
import com.clyday.clyday_api.repository.MedicacaoRepository;
import com.clyday.clyday_api.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicacaoService {

    private final MedicacaoRepository medicacaoRepository;
    private final PetRepository petRepository;

    public MedicacaoService(
            MedicacaoRepository medicacaoRepository,
            PetRepository petRepository
    ) {
        this.medicacaoRepository = medicacaoRepository;
        this.petRepository = petRepository;
    }

    public Medicacao salvar(
            String nome,
            String dosagem,
            String horario,
            Boolean obrigatoria,
            Long petId
    ) {

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() ->
                        new RuntimeException("Pet não encontrado")
                );

        Medicacao medicacao = new Medicacao();

        medicacao.setNome(nome);
        medicacao.setDosagem(dosagem);
        medicacao.setHorario(horario);
        medicacao.setObrigatoria(obrigatoria != null ? obrigatoria : Boolean.FALSE);
        medicacao.setPet(pet);

        return medicacaoRepository.save(medicacao);
    }

    public List<Medicacao> listar() {
        return medicacaoRepository.findAll();
    }

    public void deletar(Long id) {
        medicacaoRepository.deleteById(id);
    }
}