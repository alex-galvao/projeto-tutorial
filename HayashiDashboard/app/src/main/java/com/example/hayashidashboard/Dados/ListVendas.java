package com.example.hayashidashboard.Dados;

public class ListVendas {

    public String natureza;
    public String emissao;
    public String valor;

    public ListVendas(String natureza, String emissao, String valor) {
        this.natureza = natureza;
        this.emissao = emissao;
        this.valor = valor;
    }

    public String getNatureza() {
        return natureza;
    }

    public String getEmissao() {
        return emissao;
    }

    public String getValor() {
        return valor;
    }
}
