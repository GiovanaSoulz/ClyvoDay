package com.clyday.clyday_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class SecurityConfig {

    // =========================================================
    // SENHAS
    // =========================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // =========================================================
    // USUÁRIOS
    // =========================================================

    @Bean
    public InMemoryUserDetailsManager users(PasswordEncoder encoder) {

        UserDetails tutor = User
                .withUsername("tutor")
                .password(encoder.encode("1234"))
                .roles("TUTOR")
                .build();

        UserDetails veterinario = User
                .withUsername("veterinario")
                .password(encoder.encode("1234"))
                .roles("VETERINARIO")
                .build();

        return new InMemoryUserDetailsManager(
                tutor,
                veterinario
        );
    }

    // =========================================================
    // SECURITY
    // =========================================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        CsrfTokenRequestAttributeHandler requestHandler =
                new CsrfTokenRequestAttributeHandler();

        http

                // =================================================
                // CSRF
                // =================================================

                .csrf(csrf -> csrf

                        .csrfTokenRepository(
                                CookieCsrfTokenRepository
                                        .withHttpOnlyFalse()
                        )

                        .csrfTokenRequestHandler(
                                requestHandler
                        )

                        // APIs REST que não utilizam formulário
                        // Thymeleaf podem ficar sem CSRF.
                        .ignoringRequestMatchers(
                                "/tutores/**",
                                "/atividades",
                                "/atividades/**",
                                "/h2-console/**"
                        )
                )

                // =================================================
                // AUTORIZAÇÃO
                // =================================================

                .authorizeHttpRequests(auth -> auth

                        // -------------------------------
                        // PÚBLICO
                        // -------------------------------

                        .requestMatchers("/login").permitAll()

                        .requestMatchers("/css/**").permitAll()

                        .requestMatchers("/js/**").permitAll()

                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // -------------------------------
                        // DASHBOARD
                        // -------------------------------

                        .requestMatchers("/dashboard")
                        .authenticated()

                        // -------------------------------
                        // ADMIN
                        // -------------------------------

                        .requestMatchers("/admin/**")
                        .hasRole("VETERINARIO")

                        // -------------------------------
                        // TUTORES
                        // -------------------------------

                        .requestMatchers("/tutores/**")
                        .hasRole("VETERINARIO")

                        // -------------------------------
                        // MEDICAÇÕES
                        // -------------------------------

                        // Página de cadastro
                        .requestMatchers("/medicacoes/novo")
                        .hasRole("VETERINARIO")

                        // Cadastro
                        .requestMatchers(HttpMethod.POST, "/medicacoes")
                        .hasRole("VETERINARIO")

                        // Exclusão
                        .requestMatchers(
                                "/medicacoes/*/deletar"
                        )
                        .hasRole("VETERINARIO")

                        // Visualização
                        .requestMatchers("/medicacoes/**")
                        .hasAnyRole(
                                "TUTOR",
                                "VETERINARIO"
                        )

                        // -------------------------------
                        // PETS
                        // -------------------------------

                        .requestMatchers("/pets/**")
                        .hasAnyRole(
                                "TUTOR",
                                "VETERINARIO"
                        )

                        // -------------------------------
                        // ATIVIDADES
                        // -------------------------------

                        .requestMatchers("/atividades/**")
                        .hasAnyRole(
                                "TUTOR",
                                "VETERINARIO"
                        )

                        // -------------------------------
                        // RANKING
                        // -------------------------------

                        .requestMatchers("/ranking/**")
                        .hasAnyRole(
                                "TUTOR",
                                "VETERINARIO"
                        )

                        // -------------------------------
                        // RESTO
                        // -------------------------------

                        .anyRequest()
                        .authenticated()
                )

                // =================================================
                // LOGIN
                // =================================================

                .formLogin(form -> form

                        .loginPage("/login")

                        .loginProcessingUrl("/login")

                        .defaultSuccessUrl(
                                "/dashboard",
                                true
                        )

                        .permitAll()
                )

                // =================================================
                // LOGOUT
                // =================================================

                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl("/login")

                        .permitAll()
                )

                // =================================================
                // H2 CONSOLE
                // =================================================

                .headers(headers -> headers.frameOptions(frame -> frame.disable()));
        return http.build();
    }
}