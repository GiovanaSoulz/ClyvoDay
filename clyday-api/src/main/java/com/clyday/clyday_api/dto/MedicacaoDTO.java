package com.clyday.clyday_api.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicacaoDTO {

    @NotBlank(message = "Informe o nome da medicação")
    private String nome;

    @NotBlank(message = "Informe a dosagem")
    private String dosagem;

    @NotBlank(message = "Informe o horário")
    private String horario;

    private Boolean obrigatoria = Boolean.FALSE;

    @NotNull(message = "Selecione o pet")
    private Long petId;

    // GETTERS

    public String getNome() {
        return nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    public String getHorario() {
        return horario;
    }

    public Boolean getObrigatoria() {
        return obrigatoria;
    }

    public Long getPetId() {
        return petId;
    }

    // SETTERS

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setObrigatoria(Boolean obrigatoria) {
        this.obrigatoria = obrigatoria;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }
}
