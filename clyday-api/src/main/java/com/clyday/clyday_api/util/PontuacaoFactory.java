package com.clyday.clyday_api.util;


public class PontuacaoFactory {

    public static PontuacaoStrategy getStrategy(String tipo) {

        switch (tipo.toUpperCase()) {

            case "ALIMENTACAO":
                return new AlimentacaoStrategy();

            case "AGUA":
                return new AguaStrategy();

            case "MEDICACAO":
                return new MedicacaoStrategy();

            default:
                throw new RuntimeException("Tipo inválido");
        }
    }
}
