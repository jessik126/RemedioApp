package com.cursoandroid.jesscampos.remedioapp.Remedio;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.CriaBancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.Remedio;
import com.cursoandroid.jesscampos.remedioapp.MenuRemedio;
import com.cursoandroid.jesscampos.remedioapp.R;

/**
 * Created by Jessica on 03/09/2017.
 */
public class Editar extends AppCompatActivity {
    String codigo;
    BancoDados crud;
    EditText nome;
    EditText caixa;
    EditText hora;
    EditText min;
    EditText medico;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserir);

        codigo = this.getIntent().getStringExtra("codigo");
        Button btEditarRemedio = (Button)findViewById(R.id.btEditarRemedio);

        crud = new BancoDados(getBaseContext());

        nome = (EditText)findViewById(R.id.etNome);
        caixa = (EditText)findViewById(R.id.etNomeCaixa);
        hora = (EditText)findViewById((R.id.etHora));
        min = (EditText)findViewById(R.id.etMin);
        medico = (EditText)findViewById(R.id.etMedico);


        cursor = crud.carregaDadoById(Integer.parseInt(codigo));
        nome.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_NOME)));
        caixa.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_CAIXA)));
        hora.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_HORA)));
        min.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_MIN)));
        medico.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_MEDICO)));

        btEditarRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Remedio remedio = new Remedio();

                remedio.setNome(nome.getText().toString());
                remedio.setCaixa(caixa.getText().toString());
                remedio.setHora(Integer.parseInt(hora.getText().toString()));
                remedio.setMin(Integer.parseInt(min.getText().toString()));
                remedio.setMedico(medico.getText().toString());

                crud.alteraRegistro(Integer.parseInt(codigo), remedio);
                Intent intent = new Intent(Editar.this, MenuRemedio.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
