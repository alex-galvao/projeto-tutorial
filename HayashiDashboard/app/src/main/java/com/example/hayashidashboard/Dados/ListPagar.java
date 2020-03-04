package com.example.hayashidashboard.Dados;

public class ListPagar {

    public String pedido;
    public String emissao;
    public String valor;

    public ListPagar(String pedido, String emissao, String valor) {
        this.pedido = pedido;
        this.emissao = emissao;
        this.valor = valor;
    }

    public String getPedido() {
        return pedido;
    }

    public String getEmissao() {
        return emissao;
    }

    public String getValor() {
        return valor;
    }
}
