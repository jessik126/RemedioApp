package com.cursoandroid.jesscampos.remedioapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.CriaBancoDados;

import java.util.ArrayList;

/**
 * Created by Jessica on 22/07/2017.
 */
public class Listar extends AppCompatActivity {
    Utils utils = new Utils(Listar.this);
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar);

        //banco
        BancoDados crud = new BancoDados(getBaseContext());
        Cursor cursor = crud.carregaDados();

        String[] nomeCampos = new String[] {CriaBancoDados.KEY_CAIXA, CriaBancoDados.KEY_NOME};
        int[] idViews = new int[] {R.id.idListaCaixa, R.id.idListaNome};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.lista_itens,cursor,nomeCampos,idViews, 0);
        lista = (ListView)findViewById(R.id.lvRemedios);
        lista.setAdapter(adaptador);
    }

}
