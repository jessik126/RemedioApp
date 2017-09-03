package com.cursoandroid.jesscampos.remedioapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jessica on 22/07/2017.
 */
public class TelaMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_menu);

        Button btInserir = (Button) findViewById(R.id.btInserir);
        Button btListar = (Button) findViewById(R.id.btListar);
        Button btParear = (Button) findViewById(R.id.btParear);

        //evento inserir
        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela inserir
                Intent abreTela = new Intent(TelaMenu.this, Inserir.class);
                TelaMenu.this.startActivity(abreTela);
            }
        });

        //evento listar
        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela listar
                Intent abreTela = new Intent(TelaMenu.this, Listar.class);
                TelaMenu.this.startActivity(abreTela);
            }
        });

        //evento parear
        btParear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela parear
                Intent abreTela = new Intent(TelaMenu.this, Parear.class);
                TelaMenu.this.startActivity(abreTela);
            }
        });
    }
}
