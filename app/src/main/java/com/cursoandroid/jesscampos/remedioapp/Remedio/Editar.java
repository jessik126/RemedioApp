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
import android.widget.TimePicker;
import android.widget.Toast;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.CriaBancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.Remedio;
import com.cursoandroid.jesscampos.remedioapp.MenuPrincipal;
import com.cursoandroid.jesscampos.remedioapp.R;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.CircularToggle;

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
    MultiSelectToggleGroup gpDiasDaSemana;

    private EditText hora;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remedio_editar);


        nome = (EditText) findViewById(R.id.etNomeEditar);
        hora = (EditText) findViewById((R.id.etHoraEditar));
        freqHora = (EditText) findViewById(R.id.etFreqHoraEditar);
        medico = (EditText) findViewById(R.id.etMedicoEditar);
        rbGrupoCaixa = (RadioGroup) findViewById(R.id.rbuttonGroupEditar);
        gpDiasDaSemana = (MultiSelectToggleGroup) findViewById(R.id.gpDiasDaSemana);

        crud = new BancoDados(getBaseContext());
        codigo = this.getIntent().getStringExtra("codigo");
        cursor = crud.carregaRemedioPorId(Integer.parseInt(codigo));

        String caixaBanco = cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_CAIXA));

        if (caixaBanco == null) {
            caixaBanco = " ";
        }

        switch (caixaBanco) {
            case "A":
                rbGrupoCaixa.check(R.id.rbuttonAEditar);
                break;
            case "B":
                rbGrupoCaixa.check(R.id.rbuttonBEditar);
                break;
            case "C":
                rbGrupoCaixa.check(R.id.rbuttonCEditar);
                break;
            default:
                rbGrupoCaixa.clearCheck();
                break;
        }


        nome.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_NOME)));
        hora.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_HORA)));
        freqHora.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_FREQHORA)));
        medico.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_MEDICO)));
        preencheDiasDaSemana(cursor.getString(cursor.getColumnIndexOrThrow(CriaBancoDados.KEY_FREQDIA)));

        Button btEditarRemedio = (Button)findViewById(R.id.btEditarRemedio);
        btEditarRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String caixa = ((RadioButton) findViewById(rbGrupoCaixa.getCheckedRadioButtonId())).getText().toString();

                String diasSelecionados = "";
                for(int index=0; index<((MultiSelectToggleGroup)gpDiasDaSemana).getChildCount(); ++index) {
                    CircularToggle child = (CircularToggle) ((MultiSelectToggleGroup)gpDiasDaSemana).getChildAt(index);
                    diasSelecionados += child.isChecked() ? '1' : '0';
                }

                if(diasSelecionados.equals("0000000")) {
                    Toast.makeText(getBaseContext(), "Por favor, selecione ao menos um dia da semana.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(nome.getText().toString().isEmpty() || caixa.isEmpty() || hora.getText().toString().isEmpty() ||
                        freqHora.getText().toString().isEmpty() || medico.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_LONG).show();
                    return;
                }

                int freqHoraValor = Integer.parseInt(freqHora.getText().toString());

                if(freqHoraValor < 0 || freqHoraValor > 24) {
                    Toast.makeText(getBaseContext(), "A frequência do alarme deve ser menor do que 24.", Toast.LENGTH_LONG).show();
                    return;
                }

                Remedio remedio = new Remedio();
                remedio.setNome(nome.getText().toString());
                remedio.setCaixa(caixa);
                remedio.setHora(hora.getText().toString());
                remedio.setFreqHora(freqHoraValor);
                remedio.setMedico(medico.getText().toString());
                remedio.setFreqDia(diasSelecionados);
                remedio.setFuncao("");

                crud.alteraRemedio(Integer.parseInt(codigo), remedio);
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

                String date = String.format("%02d:%02d", hourOfDay, minute); //hourOfDay + ":" + minute;
                hora.setText(date);
            }
        };
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,MenuPrincipal.class);
        startActivity(intent);
    }

    private void preencheDiasDaSemana(String diasSelecionados) {
        if(diasSelecionados.isEmpty() || (diasSelecionados.length() != ((MultiSelectToggleGroup)gpDiasDaSemana).getChildCount()))
            return;

        gpDiasDaSemana = (MultiSelectToggleGroup) findViewById(R.id.gpDiasDaSemana);

        for(int index=0; index < diasSelecionados.length(); ++index) {
            CircularToggle child = (CircularToggle) ((MultiSelectToggleGroup)gpDiasDaSemana).getChildAt(index);
            if(diasSelecionados.charAt(index) == '1')
                child.setChecked(true);
            else
                child.setChecked(false);

        }

    }
}
