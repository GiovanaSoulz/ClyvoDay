package com.clyday.clyday_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    // Protegido em SecurityConfig com hasRole("VETERINARIO"):
    // apenas o perfil profissional (veterinário) acessa /admin/**.
    @GetMapping("/teste")
    public String testeAdmin() {
        return "Acesso permitido apenas para o perfil VETERINARIO";
    }
}