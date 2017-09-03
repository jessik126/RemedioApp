package com.cursoandroid.jesscampos.remedioapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.CriaBancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.Remedio;

/**
 * Created by Jessica on 22/07/2017.
 */
public class Inserir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserir);

        Button botao = (Button)findViewById(R.id.btInserir);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BancoDados crud = new BancoDados(getBaseContext());
                EditText nome = (EditText)findViewById(R.id.etNome);
                EditText caixa = (EditText)findViewById(R.id.etNomeCaixa);
                EditText hora = (EditText)findViewById((R.id.etHora));
                EditText min = (EditText)findViewById(R.id.etMin);
                EditText medico = (EditText)findViewById(R.id.etMedico);

                Remedio remedio = new Remedio();

                remedio.setNome(nome.getText().toString());
                remedio.setCaixa(caixa.getText().toString());
                remedio.setHora(Integer.parseInt(hora.getText().toString()));
                remedio.setMin(Integer.parseInt(min.getText().toString()));
                remedio.setMedico(medico.getText().toString());

                String resultado = crud.addRemedio(remedio);

                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
            }
        });
    }
}
