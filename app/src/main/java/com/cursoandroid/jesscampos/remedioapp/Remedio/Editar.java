package com.cursoandroid.jesscampos.remedioapp.Remedio;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.CriaBancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.Remedio;
import com.cursoandroid.jesscampos.remedioapp.MenuRemedio;
import com.cursoandroid.jesscampos.remedioapp.R;

import java.util.Calendar;

/**
 * Created by Jessica on 03/09/2017.
 */
public class Editar extends AppCompatActivity {
    String codigo;
    BancoDados crud;
    EditText nome;
    EditText freqHora;
    EditText medico;
    Cursor cursor;
    RadioGroup rbGrupoCaixa;

    private EditText hora;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);


        nome = (EditText)findViewById(R.id.etNomeEditar);
        hora = (EditText)findViewById((R.id.etHoraEditar));
        freqHora = (EditText)findViewById(R.id.etFreqHoraEditar);
        medico = (EditText)findViewById(R.id.etMedicoEditar);
        rbGrupoCaixa = (RadioGroup) findViewById(R.id.rbuttonGroupEditar);

        crud = new BancoDados(getBaseContext());
        codigo = this.getIntent().getStringExtra("codigo");
        cursor = crud.carregaDadoById(Integer.parseInt(codigo));

        String caixaBanco = cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_CAIXA));
        switch(caixaBanco) {
            case "A":
                rbGrupoCaixa.check(R.id.rbuttonAEditar);
                break;
            case "B":
                rbGrupoCaixa.check(R.id.rbuttonBEditar);
                break;
            case "C":
                rbGrupoCaixa.check(R.id.rbuttonCEditar);
                break;
        }

        nome.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_NOME)));
        hora.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_HORA)));
        freqHora.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_FREQHORA)));
        medico.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_MEDICO)));


        Button btEditarRemedio = (Button)findViewById(R.id.btEditarRemedio);
        btEditarRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String caixa = ((RadioButton) findViewById(rbGrupoCaixa.getCheckedRadioButtonId())).getText().toString();

                Remedio remedio = new Remedio();
                remedio.setNome(nome.getText().toString());
                remedio.setCaixa(caixa);
                remedio.setHora(hora.getText().toString());
                remedio.setFreqHora(Integer.parseInt(freqHora.getText().toString()));
                remedio.setMedico(medico.getText().toString());
                remedio.setFreqDia("");
                remedio.setFuncao("");

                crud.alteraRegistro(Integer.parseInt(codigo), remedio);
                Intent intent = new Intent(Editar.this, Listar.class);
                startActivity(intent);
                finish();
            }
        });

        hora = (EditText)findViewById((R.id.etHoraEditar));
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        Editar.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hour,minute, DateFormat.is24HourFormat(Editar.this));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker datePicker, int hourOfDay, int minute) {

                String date = hourOfDay + ":" + minute;
                hora.setText(date);
            }
        };
    }
}
