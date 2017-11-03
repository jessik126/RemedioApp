package com.cursoandroid.jesscampos.remedioapp.Bluetooth;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cursoandroid.jesscampos.remedioapp.BancoDados.BancoDados;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.Remedio;
import com.cursoandroid.jesscampos.remedioapp.BancoDados.RemedioHistorico;
import com.cursoandroid.jesscampos.remedioapp.MenuPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by noob3 on 9/8/2017.
 */


public class GerenciadorBluetooth extends Application {
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter btAdapter;
    private BluetoothDevice device;
    private BluetoothSocket btSocket;
    private static final String TAG = "Menu Principal";
    private Context context;

    @Override
    public void onTerminate() {
        super.onTerminate();
        try {
            btSocket.close();
        } catch (IOException e) {
        }
    }

    public boolean abrirSocket(String address) {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
            btSocket.connect();
            return true;
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Falha ao conectar o dispositivo", Toast.LENGTH_LONG).show();
            try{
                btSocket.close();
            } catch (IOException ex) {

            }
            return false;
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    public boolean sincronizaAlarme(Context context) {
        this.context = context;
        try{
            OutputStream mmOutStream = btSocket.getOutputStream();
            InputStream mmInStream = btSocket.getInputStream();
            enviaAlarme(mmOutStream);
            enviaSinalHistorico(mmOutStream);
            recebeHistorico(mmInStream);
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    private void enviaAlarme(OutputStream mmOutStream) {
        boolean caixas[] = {false,false,false};

        //banco
        BancoDados crud = new BancoDados(getBaseContext());
        List<Remedio> listaRemedio = crud.listarRemedios();

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
        Toast.makeText(context, "Envio terminado", Toast.LENGTH_LONG).show();
    }

    private boolean enviaAlarmePorCaixa(Remedio remedio, OutputStream mmOutStream){
        String envia = "";
        String[] horaMin = remedio.getHora().split(":");
        envia = "{\"id\":" + remedio.getId() +
                ",\"cx\":\"" + remedio.getCaixa() +
                "\",\"h\":" + horaMin[0] +
                ",\"m\":" + horaMin[1] +
                ",\"fd\":\"" + remedio.getFreqDia() +
                "\",\"fh\":" + remedio.getFreqHora() + "}";

        byte[] msgBuffer = envia.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            Log.e(TAG, "Envio completo - caixa " + remedio.getCaixa());
            Toast.makeText(context, "Envio completo - caixa " + remedio.getCaixa(), Toast.LENGTH_LONG).show();
            return true;
        } catch (IOException e) {
            //if you cannot write, close the application
            Toast.makeText(context, "Connection Failure", Toast.LENGTH_LONG).show();
            return false;
        }
    }


//    BufferedReader streamReader = new BufferedReader(new InputStreamReader(mmInStream, "UTF-8"));
//    StringBuilder responseStrBuilder = new StringBuilder();
//
//    String inputStr;
//                while ((inputStr = streamReader.readLine()) != null)
//            responseStrBuilder.append(inputStr);
//
//    String readMessage = responseStrBuilder.toString();
//
//                if(readMessage.equals("#")) {
//        break;
//    }
//    processaHistorico(readMessage);

    public void recebeHistorico(InputStream mmInStream) {
        byte[] buffer = new byte[1];
        int bytes;
        String jsonRecebido = "";
        String readMessage = "";

        // Keep looping to listen for received messages
        while (!readMessage.equals("!")) {
            try {
                bytes = mmInStream.read(buffer);           //read bytes from input buffer
                readMessage = new String(buffer, 0, bytes);
                jsonRecebido = jsonRecebido + readMessage;

                if(readMessage.equals("}")) {
                    processaHistorico(jsonRecebido);
                    jsonRecebido = "";
                }
            } catch (IOException e) {
                break;
            }
        }

    }

    private void enviaSinalHistorico(OutputStream mmOutStream){
        try {
            mmOutStream.write("historico".getBytes());
            Toast.makeText(getBaseContext(), "Inicio recebimento Historico", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
        }
    }


    public void processaHistorico(String jsonStr) {
        String[] jsonArray = jsonStr.split("#");
        if (jsonArray.length > 0) {
            for(String json: jsonArray) {
                if(json.length() == 1)
                    continue;
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    RemedioHistorico historico = new RemedioHistorico(jsonObj.getInt("id"), jsonObj.getString("hr"), jsonObj.getString("dt"));

                    BancoDados crud = new BancoDados(getBaseContext());
                    crud.insereHistorico(historico);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }
        }

    }


}