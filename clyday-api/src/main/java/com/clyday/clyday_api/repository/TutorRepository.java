package com.clyday.clyday_api.repository;



import com.clyday.clyday_api.entity.Tutor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Page<Tutor> findByNomeContainingIgnoreCase(
            String nome,
            Pageable pageable
    );
}
