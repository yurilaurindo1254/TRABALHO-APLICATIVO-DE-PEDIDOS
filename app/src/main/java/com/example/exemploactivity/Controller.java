package com.example.exemploactivity;

import com.example.exemploactivity.modelo.Cliente;
import com.example.exemploactivity.modelo.Produto;
import com.example.exemploactivity.modelo.Pedido;

import java.util.ArrayList;

public class Controller {

    private static Controller instancia;
    private ArrayList<Cliente> listaClientes;
    private int ultimoCodigoPedido = 0;
    private ArrayList<Pedido> listaPedidos;
    private ArrayList<Produto> listaProdutos;

    public static Controller getInstance(){
        if(instancia == null) {
            return instancia = new Controller();
        }else {
            return instancia;
        }
    }

    private Controller(){
        listaClientes = new ArrayList<>();
        listaPedidos = new ArrayList<>();
        listaProdutos = new ArrayList<>();
    }

    public void salvarCliente (Cliente cliente){
        listaClientes.add(cliente);
    }

    public ArrayList<Cliente> retornarCliente() {
        return listaClientes;
    }
    public void salvarProduto(Produto produto){
        listaProdutos.add(produto);
    }

    public ArrayList<Produto> retonrarProduto() {
        return listaProdutos;
    }

    public int gerarNovoCodigoPedido() {
        ultimoCodigoPedido++;
        return ultimoCodigoPedido;
    }
    public void salvarPedido(Pedido pedido){ listaPedidos.add(pedido);}

    public Pedido buscarPedidoPorNumero(int numeroPedido) {
        for (Pedido pedido : listaPedidos) {
            if (pedido.getCodigo() == numeroPedido) {
                return pedido;
            }
        }
        return null; // Retorna null se o pedido não for encontrado
    }
    public ArrayList<Pedido> retornarPedido(){
        return listaPedidos;
    }
}
