package com.cursoandroid.jesscampos.remedioapp.Remedio;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.CriaBancoDados;
import com.cursoandroid.jesscampos.remedioapp.MenuPrincipal;
import com.cursoandroid.jesscampos.remedioapp.MenuRemedio;
import com.cursoandroid.jesscampos.remedioapp.R;

/**
 * Created by Jessica on 22/07/2017.
 */
public class Listar extends AppCompatActivity {
    ListView lista;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remedio_listar);

        //banco
        BancoDados crud = new BancoDados(getBaseContext());
        cursor = crud.carregaRemedios();

        String[] nomeCampos = new String[] {CriaBancoDados.KEY_CAIXA, CriaBancoDados.KEY_NOME};
        int[] idViews = new int[] {R.id.idListaCaixa, R.id.idListaNome};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(Listar.this, R.layout.util_lista2, cursor, nomeCampos, idViews, 0);
        lista = (ListView)findViewById(R.id.lvRemedios);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_ID));
                Intent abreTela = new Intent(Listar.this, MenuRemedio.class);
                abreTela.putExtra("codigo", codigo);

                Listar.this.startActivity(abreTela);
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,MenuPrincipal.class);
        startActivity(intent);
    }
}
