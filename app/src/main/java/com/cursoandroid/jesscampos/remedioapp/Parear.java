package com.cursoandroid.jesscampos.remedioapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Jessica on 22/07/2017.
 */
public class Parear extends AppCompatActivity implements AdapterView.OnItemClickListener{
    Utils utils = new Utils(Parear.this);

    private static final String TAG  = "Parear";

    BluetoothAdapter mBluetoothAdapter;
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public ListaBluetooth mDeviceListAdapter;
    ListView lvNewDevices;


    private final BroadcastReceiver mBroadcastReceiverBthOnOff = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)){
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch (state){
                    case BluetoothAdapter.STATE_OFF:
                        //utils.alert("OFF");
                        Log.d(TAG, "OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        //utils.alert("mudando para OFF");
                        Log.d(TAG, "mudando para OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        //utils.alert("ON");
                        Log.d(TAG, "ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        //utils.alert("mudando para ON");
                        Log.d(TAG, "mudando para ON");
                        break;
                }
            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiverBthDiscoverability = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(mBluetoothAdapter.ACTION_SCAN_MODE_CHANGED )){
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, mBluetoothAdapter.ERROR);

                switch (state){
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        utils.alert("DICOVERABLE");
                        Log.d(TAG, "DICOVERABLE: Discoberta ativada");
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        utils.alert("CONNECTABLE");
                        Log.d(TAG, "CONNECTABLE: Discoberta desativada, pode receber conexões");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        utils.alert("NONE");
                        Log.d(TAG, "NONE: Discoberta desativada, não pode receber conexões");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        utils.alert("CONNECTING");
                        Log.d(TAG, "CONNECTING");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        utils.alert("CONNECTED");
                        Log.d(TAG, "CONNECTED");
                        break;
                }
            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiverBthDiscover = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND )){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(mDevice);



                Log.d(TAG, "onReceive: " + mDevice.getName() + ": " + mDevice.getAddress());
                mDeviceListAdapter = new ListaBluetooth(context, R.layout.lista_bluetooth, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);

            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiverBthParear = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED )){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG, "BONDED");
                }
                else if(mDevice.getBondState() == BluetoothDevice.BOND_BONDING){
                    Log.d(TAG, "BONDING");

                }
                else if(mDevice.getBondState() == BluetoothDevice.BOND_NONE){
                    Log.d(TAG, "NO BOND");

                }

            }
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiverBthOnOff);
        unregisterReceiver(mBroadcastReceiverBthDiscoverability);
        unregisterReceiver(mBroadcastReceiverBthDiscover);
        unregisterReceiver(mBroadcastReceiverBthParear);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parear);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();

        final Button btBluetoothOnOff = (Button) findViewById(R.id.btBluetoothOnOff);
        Button btBluetoothDiscover = (Button) findViewById(R.id.btBluetoothDiscover);

        IntentFilter bthParear = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiverBthParear, bthParear);
        lvNewDevices.setOnItemClickListener(Parear.this);

        if(this.mBluetoothAdapter.isEnabled()){
            btBluetoothOnOff.setText("ON");


        }else{
            btBluetoothOnOff.setText("OFF");
        }

        btBluetoothOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bthOnOff(btBluetoothOnOff);
            }
        });
        /*btBluetoothDiscoverability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothDiscoverability();
            }
        });*/
        btBluetoothDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothDiscover();
            }
        });
    }

    private void bthOnOff(Button bthBluetoothOnOff) {
        if(this.mBluetoothAdapter == null) {
            Log.d(TAG, "Dispositivo não tem bluetooth");
        }
        else if(!this.mBluetoothAdapter.isEnabled()){
            //para habilitar o adaptador de bluetooth
            Intent bthIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.startActivity(bthIntent);

            IntentFilter bthOnOffIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiverBthOnOff, bthOnOffIntent);
            bthBluetoothOnOff.setText("ON");

            Set pairedDevices = mBluetoothAdapter.getBondedDevices();
            if(pairedDevices.size() > 0) {
                Iterator nPairedDevices = pairedDevices.iterator();

                while(nPairedDevices.hasNext()) {
                    BluetoothDevice pairedDevice = (BluetoothDevice)nPairedDevices.next();
                    mBTDevices.add(pairedDevice);
                }
            }

        } else {
            //para desabilitar o adaptador de bluetooth
            mBluetoothAdapter.disable();

            IntentFilter bthOnOffIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiverBthOnOff, bthOnOffIntent);

            bthBluetoothOnOff.setText("OFF");
        }

    }


    /*private void bluetoothDiscoverability(){
        Intent bthIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        bthIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        this.startActivity(bthIntent);

        IntentFilter bthDiscoverabilityIntent = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiverBthDiscoverability, bthDiscoverabilityIntent);
    }*/

    private void bluetoothDiscover(){
        Log.d(TAG, "DISCOVER");
        if(mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "Cancel discovery");
        }
        mBluetoothAdapter.startDiscovery();

        checkBluetoothPermissions();

        IntentFilter bthIntentDiscover = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBroadcastReceiverBthDiscover, bthIntentDiscover);


    }

    private void checkBluetoothPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permision.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permision.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0){
                this.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},200);
            }
        } else{
            Log.d(TAG, "No need to check permission");

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mBluetoothAdapter.cancelDiscovery();
        Log.d(TAG, "OnClick device");

        Log.d(TAG, "OnClick name: " + mBTDevices.get(i).getName());
        Log.d(TAG, "OnClick address: " + mBTDevices.get(i).getAddress());

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.d(TAG, "Trying to pair " + mBTDevices.get(i).getName());
            mBTDevices.get(i).createBond();
        }
    }
}



