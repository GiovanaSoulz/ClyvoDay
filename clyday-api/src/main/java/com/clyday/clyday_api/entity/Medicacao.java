package com.clyday.clyday_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "medicacoes")
public class Medicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String dosagem;

    private String horario;

    private Boolean obrigatoria = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    public Medicacao() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Boolean getObrigatoria() {
        return obrigatoria;
    }

    public void setObrigatoria(Boolean obrigatoria) {
        this.obrigatoria = obrigatoria;
    }
}