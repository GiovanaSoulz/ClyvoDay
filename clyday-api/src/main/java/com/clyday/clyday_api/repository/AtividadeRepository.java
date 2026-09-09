package com.clyday.clyday_api.repository;

import com.clyday.clyday_api.entity.Atividade;
import com.clyday.clyday_api.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    List<Atividade> findByPetId(Long petId);

    List<Atividade> findByPetOrderByDataHoraDesc(Pet pet);

    List<Atividade> findByDataHoraBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    @Query("""
        SELECT a
        FROM Atividade a
        WHERE a.dataHora >= :inicio
        AND a.dataHora < :fim
        ORDER BY a.dataHora DESC
    """)
    List<Atividade> rankingSemanal(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}