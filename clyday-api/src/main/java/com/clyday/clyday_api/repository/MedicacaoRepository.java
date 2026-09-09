package com.clyday.clyday_api.repository;

import com.clyday.clyday_api.entity.Medicacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicacaoRepository extends JpaRepository<Medicacao, Long> {

    List<Medicacao> findByPetId(Long petId);
}