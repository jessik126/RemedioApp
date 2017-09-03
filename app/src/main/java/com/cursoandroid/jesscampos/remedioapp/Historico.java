package com.cursoandroid.jesscampos.remedioapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;

/**
 * Created by Jessica on 23/07/2017.
 */
public class Historico extends AppCompatActivity {

    private XYPlot plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico);

        Button btEditar = (Button) findViewById(R.id.btEditar);

        //evento editar
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrir tela listar
                Intent abreTelaCadastro = new Intent(Historico.this, Inserir.class);
                Historico.this.startActivity(abreTelaCadastro);
            }
        });

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);

        // create a couple arrays of y-values to plot:
        final Number[] domainLabels = {1, 2, 3, 6, 7, 8, 9, 10, 13, 14};
        Number[] series1Numbers = {9,9.5,9,10,8,8.5,9,9,8,8};
        Number[] series2Numbers = {8,10,9,9,9,8.5,8,9.5,10,10.5};

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.GREEN,Color.GREEN,null, null);
        LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.RED,Color.RED,null, null);

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
        plot.addSeries(series2, series2Format);
    }

}
