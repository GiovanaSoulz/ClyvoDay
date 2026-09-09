package com.clyday.clyday_api.service;

import com.clyday.clyday_api.entity.Atividade;
import com.clyday.clyday_api.entity.Pet;
import com.clyday.clyday_api.repository.AtividadeRepository;
import com.clyday.clyday_api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RankingService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    /**
     * Fluxo funcional (não-CRUD): soma os pontos ganhos por cada pet
     * apenas nos últimos 7 dias e ordena do maior para o menor,
     * gerando o ranking semanal exibido em /ranking.
     * <p>
     * Agrupa por id do pet (e não pelo objeto Pet em si) porque a
     * entidade não sobrescreve equals/hashCode, então comparar
     * instâncias vindas de consultas diferentes poderia agrupar errado.
     */
    public List<Map.Entry<Pet, Integer>> rankingSemanal() {

        LocalDateTime fim = LocalDateTime.now();
        LocalDateTime inicio = fim.toLocalDate().minusDays(6).atTime(LocalTime.MIN);

        List<Pet> pets = petRepository.findAll();

        Map<Long, Integer> pontosPorPetId = new LinkedHashMap<>();
        for (Pet pet : pets) {
            pontosPorPetId.put(pet.getId(), 0);
        }

        List<Atividade> atividadesDaSemana =
                atividadeRepository.findByDataHoraBetween(inicio, fim);

        for (Atividade atividade : atividadesDaSemana) {

            Pet pet = atividade.getPet();

            if (pet == null || !pontosPorPetId.containsKey(pet.getId())) {
                continue;
            }

            int pontosAtuais = pontosPorPetId.get(pet.getId());
            int pontosAtividade = atividade.getPontos() != null ? atividade.getPontos() : 0;

            pontosPorPetId.put(pet.getId(), pontosAtuais + pontosAtividade);
        }

        return pets.stream()
                .map(pet -> Map.entry(pet, pontosPorPetId.getOrDefault(pet.getId(), 0)))
                .sorted(Map.Entry.<Pet, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
    }
}
