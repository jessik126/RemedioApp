package com.cursoandroid.jesscampos.remedioapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jessica on 22/07/2017.
 */
public class Listar extends AppCompatActivity {
    Utils utils = new Utils(Listar.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar);

        final ListView listaRemedios = (ListView) findViewById(R.id.lvRemedios);

        //exibir
        final ArrayList<String> remedios = preencherDados();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, remedios);
        listaRemedios.setAdapter(arrayAdapter);

        //ao clicar no item
        listaRemedios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                utils.alert("Remédio: " +remedios.get(position).toString());
                //Toast.makeText(getApplicationContext(), "Remédio: " +remedios.get(position).toString(), Toast.LENGTH_LONG).show();

                //abrr tela Historico
                Intent abreTela = new Intent(Listar.this, Historico.class);
                Listar.this.startActivity(abreTela);
            }
        });

    }

    private ArrayList<String> preencherDados() {
        ArrayList<String> dados = new ArrayList<String>();
        dados.add("A");
        dados.add("B");
        dados.add("C");
        return dados;
    }
}
