package com.cursoandroid.jesscampos.remedioapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.Remedio;
import com.cursoandroid.jesscampos.remedioapp.Bluetooth.Conectar;
import com.cursoandroid.jesscampos.remedioapp.Bluetooth.Sincronizar;
import com.cursoandroid.jesscampos.remedioapp.Remedio.Inserir;
import com.cursoandroid.jesscampos.remedioapp.Remedio.Listar;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jessica on 22/07/2017.
 */
public class MenuPrincipal extends AppCompatActivity {
    String address;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        Button btInserir = (Button) findViewById(R.id.btInserir);
        Button btListar = (Button) findViewById(R.id.btListar);
        Button btParear = (Button) findViewById(R.id.btParear);
        Button btSincronizar = (Button) findViewById(R.id.btSincronizar);

        //evento inserir medicamentos
        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela remedio_inserir
                Intent abreTela = new Intent(MenuPrincipal.this, Inserir.class);
                MenuPrincipal.this.startActivity(abreTela);
            }
        });

        //evento listar medicamentos
        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela remedio_listar
                Intent abreTela = new Intent(MenuPrincipal.this, Listar.class);
                MenuPrincipal.this.startActivity(abreTela);
            }
        });



        //evento conectar bluetooth
        btParear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir tela bluetooth_conectar
                Intent abreTela = new Intent(MenuPrincipal.this, Conectar.class);
                MenuPrincipal.this.startActivity(abreTela);
            }
        });

        try {
            if (this.getIntent().getExtras().getBoolean("conectado")) {
                btSincronizar.setEnabled(true);
                btSincronizar.setBackgroundColor(0xFF4052b5);
                Toast.makeText(getBaseContext(), "Conectado", Toast.LENGTH_LONG).show();
            } else {
                btSincronizar.setEnabled(false);
                btSincronizar.setBackgroundColor(0x334052b5);
            }
        }
        catch (Exception e){
            btSincronizar.setEnabled(false);
            btSincronizar.setBackgroundColor(0x334052b5);
        }

        address = this.getIntent().getStringExtra("enderecoDispositivo");

        //evento sincronizar bluetooth
        btSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrir tela bluetooth_sincronizar
                /*Intent abreTela = new Intent(MenuPrincipal.this, Sincronizar.class);
                abreTela.putExtra("enderecoDispositivo", address);
                MenuPrincipal.this.startActivity(abreTela);*/
                criaSocket();
            }
        });
    }

    void criaSocket(){
        BluetoothAdapter btAdapter;
        BluetoothDevice device;
        BluetoothSocket btSocket = null;
        try {
            //create device and set the MAC address
            btAdapter = BluetoothAdapter.getDefaultAdapter();
            device = btAdapter.getRemoteDevice(address);
            btSocket = createBluetoothSocket(device);
            boolean a = btSocket.isConnected();
            btSocket.connect();
            OutputStream mmOutStream = btSocket.getOutputStream();
            enviaAlarme(mmOutStream);

            btSocket.close();
        }
        catch(IOException e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
        finally {
            try {
                btSocket.close();
            } catch (IOException e1) {
            }
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    public void enviaAlarme(OutputStream mmOutStream) {
        boolean caixas[] = {false,false,false};

        //banco
        BancoDados crud = new BancoDados(getBaseContext());
        List<Remedio> listaRemedio = crud.obterRemediosAtivos();

        for (Remedio remedio : listaRemedio ) {
            int numCaixa = (int) remedio.getCaixa().toCharArray()[0] - 65;
            caixas[numCaixa] = enviaAlarmePorCaixa(remedio, mmOutStream);
        }
        for (int i=0; i<caixas.length; i++){
            if(!caixas[i]){
                char nomeCaixa = (char)(i+65);
                Remedio remedio = new Remedio();
                remedio.setCaixa(Character.toString(nomeCaixa));
                caixas[i] = enviaAlarmePorCaixa(remedio, mmOutStream);
            }
        }
    }

    boolean enviaAlarmePorCaixa(Remedio remedio, OutputStream mmOutStream){
        String envia = "";
        String[] horaMin = remedio.getHora().split(":");
        envia = "{\"cx\":\"" + remedio.getCaixa() +
                "\",\"h\":" + horaMin[0] +
                ",\"m\":" + horaMin[1] +
                ",\"fd\":\"" + remedio.getFreqDia() +
                "\",\"fh\":" + remedio.getFreqHora() + "}";

        byte[] msgBuffer = envia.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            return true;
        } catch (IOException e) {
            //if you cannot write, close the application
            Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
