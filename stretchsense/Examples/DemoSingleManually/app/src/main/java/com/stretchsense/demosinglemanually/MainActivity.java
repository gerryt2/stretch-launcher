package com.stretchsense.demosinglemanually;

import com.stretchsense.demosinglemanually.ble.StretchSenseLibraryManager;
import com.stretchsense.demosinglemanually.ble.StretchSensePeripheralAvailable;
import com.stretchsense.demosinglemanually.ble.StretchSensePeripheralConnected;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements StretchSenseLibraryManager.StretchSenseLibraryManagerListener {

    // PARAMETERS

    // Manager Bluetooth
    private StretchSenseLibraryManager mBleManager;

    // parameter UI
    private TextView textValue;
    private TextView textFeedback;
    private TextView textAddress;
    private TextView textInformation;

    //FUNCTION CREATION
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the object of the Bluetooth manager
        mBleManager = new StretchSenseLibraryManager(this);
        // Element of the UI
        textFeedback = (TextView)findViewById(R.id.textViewFeedback);
        textAddress = (TextView)findViewById(R.id.textViewAddress);
        textValue = (TextView)findViewById(R.id.textViewValue);
    }

    @Override
    public void onResume() {
        super.onResume();

        // If the bluetooth is enable, initialize the manager and scan
        if (mBleManager.isTheBleEnable(this)) {
            mBleManager.initialiseTheManager(this);
        }
    }

    // FUNCTIONS APPLICATION
    public void changeTheTextOf(final TextView element, final String text){

        // Do the action on the UI thread to change the text of the UI element
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                element.setText(text);
            }
        });

    }

    // FUNCTION LISTENER STRETCHSENSE MANAGER
    @Override
    public void onReadRemoteRssi(int rssi) {

    }

    @Override
    public void onPeripheralDiscovered(String deviceNameToScanFor, BluetoothDevice device, int rssi, byte[] scanRecord) {

        // Loading feedback information
        changeTheTextOf(textFeedback, "Sensor discovered");

        // Expend the list of the sensors available and display the address of the first sensor
        for (StretchSensePeripheralAvailable myPeripheral: mBleManager.listStretchSensePeripheralsAvailable) {
            String addressOfTheSensor = myPeripheral.device.getAddress();
            changeTheTextOf(textAddress, "Address: " + addressOfTheSensor);
            return;
        }

    }

    @Override
    public void onConnected() {

        // Loading feedback information
        changeTheTextOf(textFeedback, "Sensor connected");

    }

    @Override
    public void onConnecting() {

        // Loading feedback information
        changeTheTextOf(textFeedback, "Sensor connecting");

    }

    @Override
    public void onDisconnected() {

        // Loading feedback information
        changeTheTextOf(textFeedback, "Sensor disconnected");
        // Reseting the labels
        changeTheTextOf(textValue, "Value of the sensor");
        changeTheTextOf(textAddress, "Address of the sensor");

    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {

        // Loading feedback information
        changeTheTextOf(textFeedback, "Service discovered");

    }

    @Override
    public void onCharacteristicDiscovered(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

        // Loading feedback information
        changeTheTextOf(textFeedback, "Characteristic discovered");

    }

    @Override
    public void onDataAvailable(BluetoothGattCharacteristic characteristic) {

        // Loading feedback information
        changeTheTextOf(textFeedback, "Data available");

    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

        // Loading feedback information
        changeTheTextOf(textFeedback, "Characteristic changed");

        // Expend the list of the sensors connect and display the address of the first sensor
        for (StretchSensePeripheralConnected myPeripheral: mBleManager.listStretchSensePeripheralsConnected) {
            // change the text of the textView after a peripheral is found
            changeTheTextOf(textValue, ("Value: " + myPeripheral.value + "pF"));
            return;
        }

    }


    // Action

    public void onClickStartScanning(View view){

        mBleManager.startScanningForAPeriod();

    }

    public void onClickConnect(View view){
        for (StretchSensePeripheralAvailable myPeripheral: mBleManager.listStretchSensePeripheralsAvailable) {
            String addressOfTheSensor = myPeripheral.device.getAddress();
            mBleManager.connectWithAddress(addressOfTheSensor);
            return;
        }
    }
}
