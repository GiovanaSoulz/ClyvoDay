package com.clyday.clyday_api.service;


import com.clyday.clyday_api.dto.TutorDTO;
import com.clyday.clyday_api.entity.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.clyday.clyday_api.repository.TutorRepository;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    public Tutor salvar(TutorDTO dto) {

        Tutor tutor = new Tutor();

        tutor.setNome(dto.getNome());
        tutor.setEmail(dto.getEmail());

        return repository.save(tutor);
    }
    @Cacheable("tutores")
    public Page<Tutor> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Tutor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
    }
    public Page<Tutor> buscarPorNome(
            String nome,
            Pageable pageable
    ) {

        return repository
                .findByNomeContainingIgnoreCase(
                        nome,
                        pageable
                );
    }
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
