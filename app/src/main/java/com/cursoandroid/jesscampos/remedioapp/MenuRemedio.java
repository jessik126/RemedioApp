package com.cursoandroid.jesscampos.remedioapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.CriaBancoDados;
import com.cursoandroid.jesscampos.remedioapp.Remedio.Editar;
import com.cursoandroid.jesscampos.remedioapp.Remedio.Historico;
import com.cursoandroid.jesscampos.remedioapp.Remedio.Listar;

/**
 * Created by Jessica on 03/09/2017.
 */
public class MenuRemedio extends AppCompatActivity {
    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_remedio);

        Button btEditarRemedio = (Button) findViewById(R.id.btEditarRemedio);
        Button btDesativarRemedio = (Button) findViewById(R.id.btDesativarRemedio);
        //Button btDeletarRemedio = (Button) findViewById(R.id.btDeletarRemedio);
        Button btHistoricoRemedio = (Button) findViewById(R.id.btHistoricoRemedio);

        codigo = this.getIntent().getStringExtra("codigo");
        BancoDados crud = new BancoDados(getBaseContext());
        Cursor cursor = crud.carregaRemedioPorId(Integer.parseInt(codigo));
        TextView nomeTela = (TextView)findViewById(R.id.tvMenuRemedio);
        final String nomeRemedio = cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_NOME));
        nomeTela.setText(nomeTela.getText() + "\n" + nomeRemedio);
        nomeTela.setGravity(Gravity.CENTER_HORIZONTAL);

        //evento remedio_editar
        btEditarRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela remedio_inserir
                Intent abreTela = new Intent(MenuRemedio.this, Editar.class);
                abreTela.putExtra("codigo", codigo);
                MenuRemedio.this.startActivity(abreTela);
            }
        });

        //evento desativar
        btDesativarRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BancoDados crud = new BancoDados(getBaseContext());
                crud.desativaRemedio(Integer.parseInt(codigo));

                //abrir tela remedio_inserir
                Intent abreTela = new Intent(MenuRemedio.this, Listar.class);
                MenuRemedio.this.startActivity(abreTela);
            }
        });

        //evento deletar
       /* btDeletarRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BancoDados crud = new BancoDados(getBaseContext());
                crud.deletaRegistro(Integer.parseInt(codigo));

                //abrir tela remedio_inserir
                Intent abreTela = new Intent(MenuRemedio.this, Listar.class);
                MenuRemedio.this.startActivity(abreTela);
            }
        });*/

        //evento remedio_historico
        btHistoricoRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela bluetooth_sincronizar
                Intent abreTela = new Intent(MenuRemedio.this, Historico.class);
                abreTela.putExtra("codigo", codigo);
                abreTela.putExtra("nomeRemedio", nomeRemedio);
                MenuRemedio.this.startActivity(abreTela);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,Listar.class);
        startActivity(intent);
    }
}
