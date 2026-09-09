

package com.clyday.clyday_api.service;

import com.clyday.clyday_api.dto.AtividadeDTO;
import com.clyday.clyday_api.entity.Atividade;
import com.clyday.clyday_api.entity.Pet;
import com.clyday.clyday_api.repository.AtividadeRepository;
import com.clyday.clyday_api.repository.PetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private PetRepository petRepository;

    public Atividade salvar(AtividadeDTO dto) {

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() ->
                        new RuntimeException("Pet não encontrado")
                );

        Atividade atividade = new Atividade();

        atividade.setTipo(dto.getTipo());
        atividade.setDescricao(dto.getDescricao());
        atividade.setPontos(calcularPontos(dto.getTipo()));

        if (dto.getDataHora() != null) {
            atividade.setDataHora(dto.getDataHora());
        } else {
            atividade.setDataHora(LocalDateTime.now());
        }

        atividade.setPet(pet);

        return atividadeRepository.save(atividade);
    }
    private Integer calcularPontos(String tipo) {

        if (tipo == null) {
            return 0;
        }

        return switch (tipo.toUpperCase().trim()) {

            case "ALIMENTACAO", "ALIMENTAÇÃO" -> 10;

            case "HIDRATACAO", "HIDRATAÇÃO" -> 10;

            case "MEDICACAO", "MEDICAÇÃO" -> 20;

            case "ATIVIDADE", "ATIVIDADE FISICA", "ATIVIDADE FÍSICA", "PASSEIO" -> 15;

            case "CONSULTA" -> 30;

            case "VACINA", "VACINACAO", "VACINAÇÃO" -> 30;

            case "HIGIENE", "BANHO" -> 15;

            case "NECESSIDADES" -> 5;

            default -> 0;
        };
    }

    public List<Atividade> listarPorPet(Long petId) {

        return atividadeRepository.findByPetId(petId);
    }

    public Atividade buscarPorId(Long id) {

        return atividadeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Atividade não encontrada")
                );
    }

    public void deletar(Long id) {

        if (!atividadeRepository.existsById(id)) {
            throw new RuntimeException("Atividade não encontrada");
        }

        atividadeRepository.deleteById(id);
    }

    // =========================================================
    // GAMIFICAÇÃO — SEQUÊNCIA DE CUIDADOS ("foguinho")
    // =========================================================
    //
    // Fluxo funcional (não-CRUD) que dá vida à proposta do produto:
    // a cada dia em que o tutor registra pelo menos um cuidado para o
    // pet, a sequência avança; um dia sem nenhum registro (além de
    // "hoje") quebra a sequência. O cálculo é feito dinamicamente a
    // partir do histórico de atividades — não depende de nenhum
    // contador armazenado, então nunca fica dessincronizado.

    /**
     * Calcula a sequência (streak) atual de dias consecutivos com pelo
     * menos um cuidado registrado para o pet informado.
     */
    public int calcularStreakAtual(Long petId) {

        TreeSet<LocalDate> dias = atividadeRepository.findByPetId(petId).stream()
                .filter(a -> a.getDataHora() != null)
                .map(a -> a.getDataHora().toLocalDate())
                .collect(Collectors.toCollection(TreeSet::new));

        if (dias.isEmpty()) {
            return 0;
        }

        LocalDate hoje = LocalDate.now();

        // A sequência só "quebra de vez" se nem hoje nem ontem tiverem
        // registro — assim o tutor não perde o streak só por ainda não
        // ter registrado nada hoje.
        LocalDate cursor;
        if (dias.contains(hoje)) {
            cursor = hoje;
        } else if (dias.contains(hoje.minusDays(1))) {
            cursor = hoje.minusDays(1);
        } else {
            return 0;
        }

        int streak = 0;
        while (dias.contains(cursor)) {
            streak++;
            cursor = cursor.minusDays(1);
        }

        return streak;
    }

    /** Calcula a sequência de cuidados de vários pets de uma vez (ex.: listagem). */
    public Map<Long, Integer> calcularStreakParaPets(List<Pet> pets) {

        Map<Long, Integer> streaks = new LinkedHashMap<>();

        for (Pet pet : pets) {
            streaks.put(pet.getId(), calcularStreakAtual(pet.getId()));
        }

        return streaks;
    }
}

