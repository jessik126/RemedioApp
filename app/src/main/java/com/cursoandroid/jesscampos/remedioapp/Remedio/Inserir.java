package com.cursoandroid.jesscampos.remedioapp.Remedio;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import java.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.Remedio;
import com.cursoandroid.jesscampos.remedioapp.R;

/**
 * Created by Jessica on 22/07/2017.
 */
public class Inserir extends AppCompatActivity {
    private EditText hora;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    String caixa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserir);

        Button botao = (Button)findViewById(R.id.btInserir);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nome = (EditText)findViewById(R.id.etNome);

                //EditText hora = (EditText)findViewById((R.id.etHora));
                RadioGroup rbGrupoCaixa = (RadioGroup) findViewById(R.id.rbuttonGroup);
                String caixa = ((RadioButton) findViewById(rbGrupoCaixa.getCheckedRadioButtonId())).getText().toString();

                EditText freqHora = (EditText)findViewById(R.id.etFreqHora);
                EditText medico = (EditText)findViewById(R.id.etMedico);

                Remedio remedio = new Remedio();
                remedio.setNome(nome.getText().toString());
                remedio.setCaixa(caixa);
                remedio.setHora(hora.getText().toString());
                remedio.setFreqHora(Integer.parseInt(freqHora.getText().toString()));
                remedio.setMedico(medico.getText().toString());
                remedio.setFreqDia("");
                remedio.setFuncao("");

                BancoDados crud = new BancoDados(getBaseContext());
                String resultado = crud.addRemedio(remedio);
                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
            }
        });

        hora = (EditText)findViewById((R.id.etHora));
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        Inserir.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hour,minute, DateFormat.is24HourFormat(Inserir.this));
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
