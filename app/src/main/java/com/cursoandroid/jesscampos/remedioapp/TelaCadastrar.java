package com.cursoandroid.jesscampos.remedioapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelaCadastrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastrar);

        Button btCadastrar = (Button) findViewById(R.id.btCadastrar);
        Button btCancelar = (Button) findViewById(R.id.btCancelar);

        //evento tela_cadastrar
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView etNome = (TextView)findViewById(R.id.etNome);
                TextView etEmail = (TextView)findViewById(R.id.etEmail);
                TextView etSenha = (TextView)findViewById(R.id.etSenha);
                String nome = etNome.getText().toString();
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
            }
        });

        //evento cancelar
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //fechar essa tela e voltar pra tela de tela_login
                finish();
            }
        });

    }
}
