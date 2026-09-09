package com.clyday.clyday_api.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String erro;
    private int status;
    private LocalDateTime data;

    public ErrorResponse(String erro, int status) {

        this.erro = erro;
        this.status = status;
        this.data = LocalDateTime.now();
    }

    public String getErro() {
        return erro;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getData() {
        return data;
    }
}
