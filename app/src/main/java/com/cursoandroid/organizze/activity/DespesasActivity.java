package com.cursoandroid.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.helper.DateCustom;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class DespesasActivity extends AppCompatActivity {
    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoValor = findViewById(R.id.editValor);
        campoData = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);

        campoData.setText(DateCustom.dataAtual());


    }
}
