package com.cursoandroid.jesscampos.remedioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelaLogin extends AppCompatActivity {
    Utils utils = new Utils(TelaLogin.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        Button btLogin = (Button) findViewById(R.id.btLogin);
        Button btTelaCadastrar = (Button) findViewById(R.id.btTelaCadastrar);

        //evento tela_login
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView etEmail = (TextView)findViewById(R.id.etEmail);
                TextView etSenha = (TextView)findViewById(R.id.etSenha);
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();

                if(email.isEmpty() || senha.isEmpty()){
                    utils.alert("Nenhum campo pode estar vazio");
                }
                else if (email.equals("jessik126@gmail.com") && senha.equals("126"))
                {
                    utils.alert("TelaLogin realizado com sucesso");
                    //abrir tela TelaCadastrar
                    Intent abreTelaMenu = new Intent(TelaLogin.this, TelaMenu.class);
                    TelaLogin.this.startActivity(abreTelaMenu);
                }
                else
                {
                    utils.alert("Email ou senha incorretos");
                }
            }
        });

        //evento tela_cadastrar
        btTelaCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrir tela TelaCadastrar
                Intent abreTela = new Intent(TelaLogin.this, TelaCadastrar.class);
                TelaLogin.this.startActivity(abreTela);
            }
        });

    }
}
