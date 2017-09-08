package com.cursoandroid.jesscampos.remedioapp.Remedio;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.cursoandroid.jesscampos.remedioapp.MenuPrincipal;
import com.cursoandroid.jesscampos.remedioapp.R;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.CircularToggle;

/**
 * Created by Jessica on 22/07/2017.
 */
public class Inserir extends AppCompatActivity {
    private AlertDialog alerta;
    private AlertDialog.Builder builder;
    private EditText hora;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    String caixa;
    MultiSelectToggleGroup gpDiasDaSemana;
    Remedio remedio = new Remedio();
    Remedio remedioAntigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remedio_inserir);

        criaModal();

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
                gpDiasDaSemana = (MultiSelectToggleGroup) findViewById(R.id.gpDiasDaSemana);

                String diasSelecionados = "";
                for(int index = 0; index<((MultiSelectToggleGroup)gpDiasDaSemana).getChildCount(); ++index) {
                    CircularToggle child = (CircularToggle) ((MultiSelectToggleGroup)gpDiasDaSemana).getChildAt(index);
                    diasSelecionados += child.isChecked() ? '1' : '0';
                }

                BancoDados crud = new BancoDados(getBaseContext());

                remedioAntigo = crud.carregaDadosPorCaixa(caixa);

                remedio.setNome(nome.getText().toString());
                remedio.setCaixa(caixa);
                remedio.setHora(hora.getText().toString());
                remedio.setFreqHora(Integer.parseInt(freqHora.getText().toString()));
                remedio.setMedico(medico.getText().toString());
                remedio.setFreqDia(diasSelecionados);
                remedio.setFuncao("");

                if(remedioAntigo != null) {
                    alerta.show();
                }
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

    private void criaModal() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Já existe um remédio nessa caixa, deseja substituí-lo?");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                BancoDados crud = new BancoDados(getBaseContext());
                crud.desativaRegistro(remedioAntigo.getId());
                crud.addRemedio(remedio);
                Intent intent = new Intent(Inserir.this, MenuPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        alerta = builder.create();
    }
}
