package com.cursoandroid.jesscampos.remedioapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class Cadastrar extends AppCompatActivity {

    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar);

        Button btCadastrar = (Button) findViewById(R.id.btCadastrar);
        Button btCancelar = (Button) findViewById(R.id.btCancelar);

        //evento cadastrar
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView etNome = (TextView)findViewById(R.id.etNome);
                TextView etEmail = (TextView)findViewById(R.id.etEmail);
                TextView etSenha = (TextView)findViewById(R.id.etSenha);
                String nome = etNome.getText().toString();
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
            }
        });

        //evento cancelar
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //fechar essa tela e voltar pra tela de login
                finish();
            }
        });

        mDisplayDate = (EditText) findViewById(R.id.edDateNascimento);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Cadastrar.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "/" + month  + "/" + year;
                mDisplayDate.setText(date);
            }
        };


    }
}
