package com.example.testerecycler;

public class ListProdutos {

    public String codProduto;
    public String nomeProduto;
    public String estoqueMinimo;
    public String estoqueAtual;
    public String estoqueIdeal;

    public ListProdutos(String codProduto, String nomeProduto, String estoqueMinimo, String estoqueAtual, String estoqueIdeal) {
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueAtual = estoqueAtual;
        this.estoqueIdeal = estoqueIdeal;
    }

    public String getCodProduto() {
        return codProduto;
    }



    public String getNomeProduto() {
        return nomeProduto;
    }



    public String getEstoqueMinimo() {
        return estoqueMinimo;
    }



    public String getEstoqueAtual() {
        return estoqueAtual;
    }



    public String getEstoqueIdeal() {
        return estoqueIdeal;
    }

}
