package com.stretchsense.newstretchsenseproject;

import com.stretchsense.newstretchsenseproject.ble.StretchSenseLibraryManager;
import com.stretchsense.newstretchsenseproject.ble.StretchSensePeripheralAvailable;
import com.stretchsense.newstretchsenseproject.ble.StretchSensePeripheralConnected;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements StretchSenseLibraryManager.StretchSenseLibraryManagerListener {

    // PARAMETERS

    // Manager Bluetooth
    private StretchSenseLibraryManager mBleManager;

    //FUNCTION CREATION
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the object of the Bluetooth manager
        mBleManager = new StretchSenseLibraryManager(this);
        // Add your code here


    }

    @Override
    public void onResume() {
        super.onResume();

        // If the bluetooth is enable, initialize the manager and scan
        if (mBleManager.isTheBleEnable(this)) {
            mBleManager.initialiseTheManager(this);
        }
    }


    // FUNCTION LISTENER STRETCHSENSE MANAGER
    @Override
    public void onReadRemoteRssi(int rssi) {

    }

    @Override
    public void onPeripheralDiscovered(String deviceNameToScanFor, BluetoothDevice device, int rssi, byte[] scanRecord) {

    }

    @Override
    public void onConnected() {
    }

    @Override
    public void onConnecting() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {

    }

    @Override
    public void onCharacteristicDiscovered(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

    }

    @Override
    public void onDataAvailable(BluetoothGattCharacteristic characteristic) {

    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

    }

}
