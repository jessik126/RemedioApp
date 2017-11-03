package com.cursoandroid.jesscampos.remedioapp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.Remedio;
import com.cursoandroid.jesscampos.remedioapp.Bluetooth.Conectar;
import com.cursoandroid.jesscampos.remedioapp.Bluetooth.GerenciadorBluetooth;
import com.cursoandroid.jesscampos.remedioapp.Remedio.Inserir;
import com.cursoandroid.jesscampos.remedioapp.Remedio.Listar;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jessica on 22/07/2017.
 */
public class MenuPrincipal extends AppCompatActivity {
    String address;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        Button btInserir = (Button) findViewById(R.id.btInserir);
        Button btListar = (Button) findViewById(R.id.btListar);
        Button btParear = (Button) findViewById(R.id.btParear);
        final Button btSincronizar = (Button) findViewById(R.id.btSincronizar);
        final Button btDesconectar = (Button) findViewById(R.id.btDesconectar);

        //evento inserir medicamentos
        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela remedio_inserir
                Intent abreTela = new Intent(MenuPrincipal.this, Inserir.class);
                MenuPrincipal.this.startActivity(abreTela);
            }
        });

        //evento listar medicamentos
        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela remedio_listar
                Intent abreTela = new Intent(MenuPrincipal.this, Listar.class);
                MenuPrincipal.this.startActivity(abreTela);
            }
        });



        //evento conectar bluetooth
        btParear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela bluetooth_conectar
                Intent abreTela = new Intent(MenuPrincipal.this, Conectar.class);
                MenuPrincipal.this.startActivity(abreTela);
            }
        });

        try {
            if (this.getIntent().getExtras().getBoolean("conectado")) {
                btSincronizar.setEnabled(true);
                btSincronizar.setBackgroundColor(0xFF4052b5);
                Toast.makeText(getBaseContext(), "Conectado", Toast.LENGTH_LONG).show();
            } else {
                btSincronizar.setEnabled(false);
                btSincronizar.setBackgroundColor(0x334052b5);
            }
        }
        catch (Exception e){
            btSincronizar.setEnabled(false);
            btSincronizar.setBackgroundColor(0x334052b5);
        }

        address = this.getIntent().getStringExtra("enderecoDispositivo");

        //evento sincronizar bluetooth
        btSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criaSocket();
            }
        });

        //evento desconectar bluetooth
        btDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSincronizar.setEnabled(false);
                btSincronizar.setBackgroundColor(0x334052b5);
                btDesconectar.setEnabled(false);
                btDesconectar.setBackgroundColor(0x334052b5);
            }
        });
    }

    void criaSocket(){
        GerenciadorBluetooth gerenciador = (GerenciadorBluetooth) getApplication();
        Button btSincronizar = (Button) findViewById(R.id.btSincronizar);
        Button btDesconectar = (Button) findViewById(R.id.btDesconectar);

        Context context = MenuPrincipal.this;
        if(gerenciador.sincronizaAlarme(context)) {
            Toast.makeText(getBaseContext(), "Dados enviados com sucesso", Toast.LENGTH_LONG).show();
            btDesconectar.setEnabled(true);
            btDesconectar.setBackgroundColor(0xFF4052b5);
        } else {
            btSincronizar.setEnabled(false);
            btSincronizar.setBackgroundColor(0x334052b5);
            Toast.makeText(getBaseContext(), "Falha ao enviar ou receber dados", Toast.LENGTH_LONG).show();
        }
    }
}
