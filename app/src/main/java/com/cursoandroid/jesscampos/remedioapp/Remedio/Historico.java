package com.cursoandroid.jesscampos.remedioapp.Remedio;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.CriaBancoDados;
import com.cursoandroid.jesscampos.remedioapp.MenuPrincipal;
import com.cursoandroid.jesscampos.remedioapp.MenuRemedio;
import com.cursoandroid.jesscampos.remedioapp.R;

/**
 * Created by Jessica on 23/07/2017.
 */
public class Historico extends AppCompatActivity {
    ListView lista;
    Cursor cursor;
    Cursor cursorRemedio;
    String codigo;
    String nomeRemedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remedio_historico);

        //banco
        BancoDados crud = new BancoDados(getBaseContext());
        codigo = this.getIntent().getStringExtra("codigo");
        nomeRemedio = this.getIntent().getStringExtra("nomeRemedio");
        cursorRemedio = crud.carregaRemedioPorId(Integer.parseInt(codigo));
        cursor = crud.carregaHistoricosPorRemedio(Integer.parseInt(codigo));
        //cursor = crud.carregaDadosHistorico();

        TextView nomeTela = (TextView)findViewById(R.id.tvHistorico);
        nomeTela.setText(nomeTela.getText() + " " + nomeRemedio);

        TextView infoRemedio = (TextView)findViewById(R.id.tvInfoHistorico);
        infoRemedio.setText("Ingerir às " + cursorRemedio.getString(cursorRemedio.getColumnIndexOrThrow(CriaBancoDados.KEY_HORA)) +
        " a cada " + cursorRemedio.getString(cursorRemedio.getColumnIndexOrThrow(CriaBancoDados.KEY_FREQHORA)) + " hora(s)");


        String[] nomeCampos = new String[] {CriaBancoDados.KEY_DIA_HISTORICO, CriaBancoDados.KEY_HORA_HISTORICO};
        int[] idViews = new int[] {R.id.idListaCaixa, R.id.idListaNome};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(Historico.this, R.layout.util_lista2, cursor, nomeCampos, idViews, 0);
        lista = (ListView)findViewById(R.id.lvHistorico);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
