package com.clyday.clyday_api.config;

import com.clyday.clyday_api.entity.Tutor;
import com.clyday.clyday_api.repository.TutorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner iniciarDados(TutorRepository tutorRepository) {

        return args -> {

            if (tutorRepository.count() == 0) {

                Tutor tutor = new Tutor();

                tutor.setNome("Tutor ClyvoDay");
                tutor.setEmail("tutor@clyvoday.com");

                tutorRepository.save(tutor);
            }
        };
    }
}