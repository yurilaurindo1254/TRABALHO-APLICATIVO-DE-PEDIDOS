package com.example.exemploactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exemploactivity.modelo.Cliente;
import com.google.android.material.snackbar.Snackbar;

public class CadastroClienteActivity extends AppCompatActivity {

    private Button btSalvar;
    private EditText edNomeCliente;
    private EditText edCpfCliente;
    private TextView tvAlunosCadastrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        btSalvar = findViewById(R.id.btSalvar);
        edNomeCliente = findViewById(R.id.edNomeCliente);
        tvAlunosCadastrados = findViewById(R.id.tvClientesCadastrados);
        edCpfCliente = findViewById(R.id.edCpfCliente);

        atualizarListaClientes();

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarCliente();
            }
        });
    }

    private void salvarCliente() {
        if(edNomeCliente.getText().toString().isEmpty()){
            edNomeCliente.setError("Informe o Nome do Cliente!");
            return;
        }
        if(edCpfCliente.getText().toString().isEmpty()){
            edCpfCliente.setError("Informe o CPF do Cliente!");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(edNomeCliente.getText().toString());
        cliente.setCpf(edCpfCliente.getText().toString());

        Controller controller = Controller.getInstance();
        if (controller != null) {
            controller.salvarCliente(cliente);

            // Atualizar a interface do usuário com o novo produto
            atualizarListaClientes();

            Toast.makeText(CadastroClienteActivity.this, "Produto Cadastrado com Sucesso!!", Toast.LENGTH_SHORT).show();

            // Limpar os campos após salvar
            edCpfCliente.setText("");
            edNomeCliente.setText("");
        } else {
            Toast.makeText(CadastroClienteActivity.this, "Erro ao salvar produto. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarListaClientes(){
        String texto = "";
        for (Cliente cliente : Controller.getInstance().retornarCliente()) {
            texto += "Nome: "+cliente.getNome()+" - "+cliente.getCpf()+"\n";
        }
        tvAlunosCadastrados.setText(texto);
    }
}