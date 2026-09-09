
package com.clyday.clyday_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AtividadeDTO {

    @NotBlank(message = "Selecione o tipo de cuidado")
    private String tipo;

    @NotBlank(message = "Descreva o cuidado realizado")
    private String descricao;

    private Integer pontos;

    private LocalDateTime dataHora;

    @NotNull(message = "Pet não informado")
    private Long petId;

    public AtividadeDTO() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }
}

