package com.example.exemploactivity.modelo;

public class Pedido {

    private int quantidade;
    private Double vlUnitario;
    private Produto produto;

    private int codigo;
    private Cliente cliente;

    public Pedido() {
    }

    public Pedido (int quantidade, Double vlUnitario, Produto produto, Cliente cliente) {
        this.quantidade = quantidade;
        this.vlUnitario = vlUnitario;
        this.produto = produto;
        this.cliente = cliente;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getVlUnitario() {
        return vlUnitario;
    }

    public void setVlUnitario(Double vlUnitario) {
        this.vlUnitario = vlUnitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
