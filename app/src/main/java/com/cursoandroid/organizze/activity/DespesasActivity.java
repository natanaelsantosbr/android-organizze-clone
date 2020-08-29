package com.cursoandroid.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.config.ConfiguracaoFirebase;
import com.cursoandroid.organizze.helper.Base64Custom;
import com.cursoandroid.organizze.helper.DateCustom;
import com.cursoandroid.organizze.model.Movimentacao;
import com.cursoandroid.organizze.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class DespesasActivity extends AppCompatActivity {
    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double despesaTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoValor = findViewById(R.id.editValor);
        campoData = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);

        campoData.setText(DateCustom.dataAtual());

        recuperarDespesaTotal();
    }

    public void salvarDespesa(View view)
    {
        if(validarCamposDespesa())
        {
            movimentacao = new Movimentacao();

            String data = DateCustom.mesAnoDataEscolhida(campoData.getText().toString());
            Double valor = Double.parseDouble(campoValor.getText().toString());

            movimentacao.setValor(valor);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("d");

            Double despesaAtualizada = despesaTotal + valor;
            atualizarDespesa(despesaAtualizada );

            movimentacao.salvar(data);
        }
    }

    public  Boolean validarCamposDespesa()
    {
        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();

        if(!textoValor.isEmpty())
        {
            if(!textoData.isEmpty())
            {
                if(!textoCategoria.isEmpty())
                {
                    if(!textoDescricao.isEmpty())
                    {
                        return  true;
                    }
                    else
                    {
                        exibirMensagemDeAviso("Descrição não foi preenchido");
                        return false;
                    }
                }
                else
                {
                    exibirMensagemDeAviso("Categoria não foi preenchidoa");
                    return false;
                }

            }
            else
            {
                exibirMensagemDeAviso("Data não foi preenchida");
                return false;
            }

        }
        else
        {
            exibirMensagemDeAviso("Valor não foi preenchido");
            return false;
        }
    }

    public void recuperarDespesaTotal() {

        String emailUsuario = autenticacao.getCurrentUser().getEmail();

        String idUsuario = Base64Custom.codificarBase64(emailUsuario);


        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void exibirMensagemDeAviso(String mensagem)
    {
        Toast.makeText(DespesasActivity.this, mensagem, Toast.LENGTH_SHORT).show();
    }

    public void atualizarDespesa(Double despesaTotal) {

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("despesaTotal").setValue(despesaTotal);
    }
}
