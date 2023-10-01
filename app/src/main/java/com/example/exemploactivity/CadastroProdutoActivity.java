package com.example.exemploactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exemploactivity.modelo.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    private Button btSalvar;
    private EditText edCodigo;
    private EditText edDescricao;
    private EditText edValorUni;
    private TextView tvProdutoCadastrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        btSalvar = findViewById(R.id.btSalvar);
        edCodigo = findViewById(R.id.edCodigo);
        edDescricao = findViewById(R.id.edDescricao);
        edValorUni = findViewById(R.id.edValorUni);
        tvProdutoCadastrados = findViewById(R.id.tvProdutoCadastrados);

        atualizarListaProdutos();

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarProduto();
            }
        });
    }



    private void salvarProduto() {
        if(edCodigo.getText().toString().isEmpty()){
            edCodigo.setError("Informe a Codigo!");
            return;
        }
        if(edDescricao.getText().toString().isEmpty()){
            edDescricao.setError("Informe o Descrição!");
            return;
        }
        if(edValorUni.getText().toString().isEmpty()){
            edValorUni.setError("Informe o valor unitário do Produto!");
            return;
        }

        Produto produto = new Produto();
        produto.setCodigo(Integer.parseInt(edCodigo.getText().toString()));
        produto.setDescricao(edDescricao.getText().toString());
        produto.setVlUnitario(Double.parseDouble(edValorUni.getText().toString()));


        Controller controller = Controller.getInstance();
        if (controller != null) {
            controller.salvarProduto(produto);

            // Atualizar a interface do usuário com o novo produto
            atualizarListaProdutos();

            Toast.makeText(CadastroProdutoActivity.this, "Produto Cadastrado com Sucesso!!", Toast.LENGTH_SHORT).show();

            // Limpar os campos após salvar
            edCodigo.setText("");
            edDescricao.setText("");
            edValorUni.setText("");
        } else {
            Toast.makeText(CadastroProdutoActivity.this, "Erro ao salvar produto. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarListaProdutos(){
        String texto = "";
        for (Produto produto : Controller.getInstance().retonrarProduto()) {
            texto += "Codigo: "+produto.getCodigo()+" - \n Descrição: "+produto.getDescricao()+"\n"+
            "Valor Unitário: "+produto.getVlUnitario()+"\n" +
            "-----------------------------------------\n";
        }
        tvProdutoCadastrados.setText(texto);
    }
}