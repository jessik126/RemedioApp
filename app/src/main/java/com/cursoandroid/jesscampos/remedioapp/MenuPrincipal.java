package com.cursoandroid.jesscampos.remedioapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cursoandroid.jesscampos.remedioapp.Remedio.Inserir;
import com.cursoandroid.jesscampos.remedioapp.Remedio.Listar;

/**
 * Created by Jessica on 22/07/2017.
 */
public class MenuPrincipal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        Button btInserir = (Button) findViewById(R.id.btInserir);
        Button btListar = (Button) findViewById(R.id.btListar);
        Button btParear = (Button) findViewById(R.id.btParear);

        //evento inserir
        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela inserir
                Intent abreTela = new Intent(MenuPrincipal.this, Inserir.class);
                MenuPrincipal.this.startActivity(abreTela);
            }
        });

        //evento listar
        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela listar
                Intent abreTela = new Intent(MenuPrincipal.this, Listar.class);
                MenuPrincipal.this.startActivity(abreTela);
            }
        });

        //evento bluetooth
        btParear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela bluetooth
                Intent abreTela = new Intent(MenuPrincipal.this, Bluetooth.class);
                MenuPrincipal.this.startActivity(abreTela);
            }
        });
    }
}
