package com.example.exemploactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exemploactivity.modelo.Cliente;
import com.example.exemploactivity.modelo.Pedido;
import com.example.exemploactivity.modelo.Produto;

import java.util.ArrayList;

public class CadastroPedidosActivity extends AppCompatActivity {

    private TextView tvListaPedidos;
    private TextView tvErroProdutos;
    private Spinner spClientes;
    private Spinner spProdutos;
    private EditText edQuantidade;
    private EditText edValorUni;
    private Button btSalvar;
    private RadioButton rbAPrazo;
    private RadioGroup rgFormas;
    private RadioButton rbAVista;
    private TextView tvValorParcelas;
    private EditText edNumeroParcelas;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Produto> listaProdutos;
    private int posicaoSelecionada = 0;

    private AutoCompleteTextView autoCompleteCodigoPedido;
    private int posicaoselecionadaproduto = 0;
    private Button btAdicionarProduto;
    private boolean calculoRealizado = false;
    private TextView tvProdutosAdicionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedidos);

        tvListaPedidos = findViewById(R.id.tvListaPedidos);
        tvErroProdutos = findViewById(R.id.tvErroProdutos);
        tvValorParcelas = findViewById(R.id.tvValorParcelas);
        edQuantidade = findViewById(R.id.edQuantidade);
        tvProdutosAdicionados = findViewById(R.id.tvProdutosAdicionados);
        edValorUni = findViewById(R.id.edValorUni);
        autoCompleteCodigoPedido = findViewById(R.id.autoCompleteCodigoPedido);
        btAdicionarProduto = findViewById(R.id.btAdicionarProduto);
        spClientes = findViewById(R.id.spClientes);
        rgFormas = findViewById(R.id.rgFormas);
        rbAPrazo = findViewById(R.id.rbAPrazo);
        edNumeroParcelas = findViewById(R.id.edNumeroParcelas);
        rbAVista = findViewById(R.id.rbAVista);
        spProdutos = findViewById(R.id.spProdutos);
        btSalvar = findViewById(R.id.btSalvar);
        btAdicionarProduto = findViewById(R.id.btAdicionarProduto);

        atualizarTextViewProdutos();
        carregaClientes();
        atualizaListaPedidos();
        salvarPedido();
        carregaProdutos();
        adicionarProdutoAoPedido();

        ArrayList<String> listaCodigosPedidos = new ArrayList<>();
        for (Pedido pedido : Controller.getInstance().retornarPedido()) {
            listaCodigosPedidos.add(String.valueOf(pedido.getCodigo()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                listaCodigosPedidos
        );

        autoCompleteCodigoPedido.setAdapter(adapter);

        autoCompleteCodigoPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigoPedido = parent.getItemAtPosition(position).toString();

                int numeroPedido = Integer.parseInt(codigoPedido);
                Pedido pedido = Controller.getInstance().buscarPedidoPorNumero(numeroPedido);

                // Faça o que quiser com o objeto 'pedido' (exiba detalhes, etc.)
            }
        });

        autoCompleteCodigoPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigoPedido = parent.getItemAtPosition(position).toString();

                int numeroPedido = Integer.parseInt(codigoPedido);
                Pedido pedido = Controller.getInstance().buscarPedidoPorNumero(numeroPedido);

                // Faça o que quiser com o objeto 'pedido' (exiba detalhes, etc.)
            }
        });


        btAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarProdutoAoPedido();
            }
        });

        rgFormas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rbAPrazo) {
                    edNumeroParcelas.setVisibility(View.VISIBLE);
                    calcularEExibirValorParcelas();
                } else if (checkedId == R.id.rbAVista) {
                    edNumeroParcelas.setVisibility(View.GONE);
                    tvValorParcelas.setText("");
                    calcularEExibirValorParcelas();
                }
            }
        });

        spClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int posicao, long l) {
                if(posicao > 0){
                    posicaoSelecionada = posicao;
                    tvErroProdutos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edNumeroParcelas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !calculoRealizado) {
                    calcularEExibirValorParcelas();
                    calculoRealizado = true;
                }
            }
        });



        spProdutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                if (posicao > 0) {
                    posicaoselecionadaproduto = posicao; // Usar a posição diretamente sem subtrair 1
                    tvErroProdutos.setVisibility(View.GONE);

                    // Obter o produto selecionado
                    Produto produtoSelecionado = listaProdutos.get(posicaoselecionadaproduto - 1);

                    // Exibir o valor unitário do produto no edValorUni
                    edValorUni.setText(String.valueOf(produtoSelecionado.getVlUnitario()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarPedido();
            }
        });


    }

    private void adicionarProdutoAoPedido() {
        if (posicaoselecionadaproduto <= 0) {
            Toast.makeText(this, "Selecione um produto primeiro.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade = Integer.parseInt(edQuantidade.getText().toString());
        if (quantidade <= 0) {
            Toast.makeText(this, "A quantidade deve ser maior que zero.", Toast.LENGTH_SHORT).show();
            return;
        }

        Produto produto = listaProdutos.get(posicaoselecionadaproduto - 1);

        // Criar um novo objeto Pedido com o produto e a quantidade e adicionar à lista de Pedidos
        Pedido pedido = new Pedido();
        pedido.setProduto(produto);
        pedido.setQuantidade(quantidade);
        Controller.getInstance().salvarPedido(pedido); // Salvar o pedido diretamente

        calcularEExibirValorParcelas();
        atualizarTextViewProdutos(); // Atualizar a lista de produtos na interface do usuário
        Toast.makeText(this, "Produto adicionado ao pedido.", Toast.LENGTH_SHORT).show();
    }


    private double calcularValorTotalPedido() {
        double valorTotal = 0;
        for (Pedido pedido : Controller.getInstance().retornarPedido()) {
            valorTotal += pedido.getProduto().getVlUnitario() * pedido.getQuantidade();
        }
        return valorTotal;
    }

    private void exibirValorTotal(double valorTotal) {
        String textoTotal = String.format("Valor Total: %.2f", valorTotal);
        tvValorParcelas.setText(textoTotal);
    }

    private void atualizarTextViewProdutos() {
        StringBuilder produtosText = new StringBuilder("Produtos Adicionados:\n");
        for (Pedido pedido : Controller.getInstance().retornarPedido()) {
            Produto produto = pedido.getProduto();
            int quantidade = pedido.getQuantidade();
            produtosText.append("- ").append(quantidade).append("x ").append(produto.getDescricao()).append("\n");
        }
        tvProdutosAdicionados.setText(produtosText.toString());
    }

    private void calcularEExibirValorParcelas() {
        double valorTotal = calcularValorTotalPedido();

        if (rbAPrazo.isChecked()) {
            // Lógica para calcular valor à prazo
            if (!edNumeroParcelas.getText().toString().isEmpty()) {
                int numeroParcelas = Integer.parseInt(edNumeroParcelas.getText().toString());
                double valorTotalComJuros = valorTotal + valorTotal * 0.05;
                double valorParcela = valorTotalComJuros / numeroParcelas;
                String textoParcelas = "Valor da Parcela (à prazo): " + valorParcela;
                tvValorParcelas.setText(textoParcelas);
            } else {
                tvValorParcelas.setText("Informe o número de parcelas.");
            }
        } else if (rbAVista.isChecked()) {
            double valorComDesconto = valorTotal - valorTotal * 0.05;
            String textoAVista = "Valor Total (à vista): " + valorComDesconto;
            tvValorParcelas.setText(textoAVista);
        } else {
            tvValorParcelas.setText("");
        }
    }

    private void salvarPedido() {
        String quantidadeStr = edQuantidade.getText().toString().trim();
        String valorUniStr = edValorUni.getText().toString().trim();

        if (quantidadeStr.isEmpty() || valorUniStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade = Integer.parseInt(quantidadeStr);
        double valorUni = Double.parseDouble(valorUniStr);

        if (quantidade <= 0) {
            edQuantidade.setError("A quantidade deve ser maior que zero!!");
            edQuantidade.requestFocus();
            return;
        }

        if (valorUni <= 0) {
            edValorUni.setError("Valor unitário deve ser maior que zero!!");
            edValorUni.requestFocus();
            return;
        }

        if (posicaoSelecionada <= 0 || posicaoselecionadaproduto <= 0) {
            tvErroProdutos.setVisibility(View.VISIBLE);
            return;
        }

        Produto produto = listaProdutos.get(posicaoselecionadaproduto - 1);
        Cliente cliente = listaClientes.get(posicaoSelecionada - 1);

        Pedido pedido = new Pedido();
        pedido.setQuantidade(quantidade);
        pedido.setVlUnitario(valorUni);
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        int novoCodigoPedido = Controller.getInstance().gerarNovoCodigoPedido();
        pedido.setCodigo(novoCodigoPedido);

        limparCampos();

        Controller.getInstance().salvarPedido(pedido);
        Toast.makeText(this, "Pedido salvo com sucesso!!", Toast.LENGTH_LONG).show();
        this.finish();
    }

    private void limparCampos() {
        edQuantidade.setText("");
        edValorUni.setText("");
        spClientes.setSelection(0);
        spProdutos.setSelection(0);
        rgFormas.clearCheck();
        edNumeroParcelas.setText("");
        tvValorParcelas.setText("");
    }

    private void carregaClientes() {
        listaClientes = Controller.getInstance().retornarCliente();
        String[]vetClientes = new String[listaClientes.size() + 1];
        vetClientes[0] = "Selecione o Cliente";
        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente cliente = listaClientes.get(i);
            vetClientes[i+1] = cliente.getNome()+" - "+cliente.getCpf();
        }
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                vetClientes);

        spClientes.setAdapter(adapter);

    }

    private void carregaProdutos() {
        listaProdutos = Controller.getInstance().retonrarProduto();
        String[] vetProduto = new String[listaProdutos.size() + 1];
        vetProduto[0] = "Selecione o Produto";
        for (int i = 0; i < listaProdutos.size(); i++) {
            Produto produto = listaProdutos.get(i);
            vetProduto[i + 1] = produto.getCodigo() + " - " + produto.getDescricao() + " - " + produto.getVlUnitario();
        }
        ArrayAdapter adapter = new ArrayAdapter(
                CadastroPedidosActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                vetProduto);

        spProdutos.setAdapter(adapter);
    }


    private void atualizaListaPedidos() {
        String texto = "";
        for (Pedido pedido : Controller.getInstance().retornarPedido()) {
            Cliente cliente = pedido.getCliente();

            // Verificar se o cliente não é nulo antes de acessar seus métodos
            if (cliente != null) {
                texto += "\nNome: " + cliente.getNome()+
                        "\nCódigo Pedido: " + pedido.getCodigo();
            }
        }
        tvListaPedidos.setText(texto);
    }
}
