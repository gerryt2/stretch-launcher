package com.StretchSense.DemoMultiple;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.StretchSense.DemoMultiple.ble.StretchSensePeripheralAvailable;
import com.StretchSense.DemoMultiple.ble.StretchSensePeripheralConnected;
import com.StretchSense.DemoMultiple.ble.StretchSenseLibraryManager;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements StretchSenseLibraryManager.StretchSenseLibraryManagerListener {

    // Data StretchSense
    private StretchSenseLibraryManager stretchSenseBleManager;

    TableItem[] tableItemsAvailable = new TableItem[]{
            new TableItem("Available 1" , 0, 0),
            new TableItem("Available 2" , 0, 0),
            new TableItem("Available 3" , 0, 0)
    };

    TableItem[] tableItemsConnected = new TableItem[]{
            new TableItem("Connected 1" , 0, 0),
            new TableItem("Connected 2" , 0, 0),
            new TableItem("Connected 3" , 0, 0)
    };

    // Table Available
    ListView listView;
    MyMenuListAdapter adapterAvailable;
    // Table Connected
    ListView listViewConnected;
    MyMenuListAdapter adapterConnected;
    // Bar & Text Sampling Time
    private SeekBar seekbar;
    private TextView textViewSamplingTime;
    int progressValue;
    int realValuesamplingTime;
    //
    boolean isUpdatingUI = false;
    // Enumeration Status of the BLE
    private static final int STATUS_BLE_ENABLED = 0;
    private static final int STATUS_BLUETOOTH_NOT_AVAILABLE = 1;
    private static final int STATUS_BLE_NOT_AVAILABLE = 2;
    private static final int STATUS_BLUETOOTH_DISABLED = 3;

    ////////////////
    // FUNCTIONS
    ////////////////

    // FUNCTION CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up the BleManager
        stretchSenseBleManager = new StretchSenseLibraryManager(this);
        //set the table
        listView = (ListView) this.findViewById(R.id.listView);
        adapterAvailable = new MyMenuListAdapter(MainActivity.this, R.layout.my_menu_list_item, tableItemsAvailable);
        adapterAvailable.notifyDataSetChanged();
        listView.setAdapter(adapterAvailable);
        // set the method OnClick to connect
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                parent.getItemAtPosition(position);
                TableItem obj = (TableItem)parent.getItemAtPosition(position);
                String address = obj.getMenuText();

                for (StretchSensePeripheralAvailable myPeripheral: stretchSenseBleManager.listStretchSensePeripheralsAvailable) {
                    String addressOnTheList = myPeripheral.device.getAddress();
                    if (addressOnTheList.equals(address)) {
                        stretchSenseBleManager.connectWithDevice(myPeripheral.device);
                    }
                }
            }
        });
        //set the table connected
        listViewConnected = (ListView) this.findViewById(R.id.listView2);
        adapterConnected = new MyMenuListAdapter(MainActivity.this, R.layout.my_menu_list_item, tableItemsConnected);
        adapterConnected.notifyDataSetChanged();
        listViewConnected.setAdapter(adapterConnected);
        // set the switch autoconnect
        Switch switchAutoconnect = (Switch)findViewById(R.id.switch1);
        assert switchAutoconnect != null;
        switchAutoconnect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                stretchSenseBleManager.AUTOCONNECT = isChecked;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        if (stretchSenseBleManager.getStatusOfTheBle(this) == STATUS_BLE_ENABLED) {
            stretchSenseBleManager.initialiseTheManager(this);
            stretchSenseBleManager.startScanningForAPeriod();

        }
            // set the slider
            functionSeekbar();
    }

    // FUNCTION

    public void changeTheTextOf(final TextView element, final String text){

        // Do the action on the UI thread to change the text of the UI element
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                element.setText(text);
            }
        });

    }


    // FUNCTIONS UI
    public void updateTable(){

        // update table available
        if (!isUpdatingUI) {
            isUpdatingUI = true;
            tableItemsAvailable = new TableItem[]{};
            tableItemsConnected = new TableItem[]{};

            // update table available
            for (StretchSensePeripheralAvailable myPeripheral : stretchSenseBleManager.listStretchSensePeripheralsAvailable) {
                TableItem newPeripheral = new TableItem(myPeripheral.device.getAddress(), myPeripheral.rssi, myPeripheral.uniqueNumber);
                tableItemsAvailable = addElement(tableItemsAvailable, newPeripheral);
            }
            // update table connected
            for (StretchSensePeripheralConnected myPeripheral : stretchSenseBleManager.listStretchSensePeripheralsConnected) {

                TableItem newPeripheral = new TableItem(myPeripheral.id, myPeripheral.value.intValue(), myPeripheral.uniqueNumber);
                tableItemsConnected = addElement(tableItemsConnected, newPeripheral);

            }
            // notify the tables for the change
            listView = (ListView) this.findViewById(R.id.listView);
            listViewConnected = (ListView) this.findViewById(R.id.listView2);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //set the table available
                    adapterAvailable = new MyMenuListAdapter(MainActivity.this, R.layout.my_menu_list_item, tableItemsAvailable);
                    adapterAvailable.notifyDataSetChanged();
                    listView.setAdapter(adapterAvailable);

                    //set the table connected
                    adapterConnected = new MyMenuListAdapter(MainActivity.this, R.layout.my_menu_list_item, tableItemsConnected);
                    adapterConnected.notifyDataSetChanged();
                    listViewConnected.setAdapter(adapterConnected);

                }
            });
            isUpdatingUI = false;
        }
    }

    public TableItem[] addElement(TableItem[] a, TableItem e) {
        a  = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

    public void functionSeekbar(){

        seekbar = (SeekBar)findViewById(R.id.samplingTimeBar);
        textViewSamplingTime = (TextView) findViewById(R.id.valueSamplingTime);
        realValuesamplingTime = (seekbar.getProgress() + 1)*40;
        changeTheTextOf(textViewSamplingTime,"Sampling Time: " + realValuesamplingTime +"ms");

        seekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        realValuesamplingTime = (progress + 1)*40;
                        changeTheTextOf(textViewSamplingTime, "Sampling Time: " + realValuesamplingTime + "ms");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        changeTheTextOf(textViewSamplingTime, "Sampling Time: " + realValuesamplingTime + "ms");
                        stretchSenseBleManager.VALUE_SAMPLING_TIME = progressValue;
                        stretchSenseBleManager.writeCharacteristicSamplingTime(progressValue);
                    }
                }
        );
    }

    // FUNCTION LISTENER BLE
    @Override
    public void onPeripheralDiscovered(String deviceNameToScanFor, BluetoothDevice device, int rssi, byte[] scanRecord) {

        updateTable();

    }

    @Override
    public void onConnected() {

        updateTable();

    }

    @Override
    public void onConnecting() {

        Toast.makeText(this, "Is Connecting",Toast.LENGTH_LONG).show();
        updateTable();

    }

    @Override
    public void onDisconnected() {

        updateTable();
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {

        updateTable();

    }

    @Override
    public void onCharacteristicDiscovered(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

        updateTable();

    }

    @Override
    public void onDataAvailable(BluetoothGattCharacteristic characteristic) {
        updateTable();

    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

        updateTable();

    }

    @Override
    public void onReadRemoteRssi(int rssi) {
    }

    // ACTIONS
    public void onClickScan(View view) {

        stretchSenseBleManager.startScanningForAPeriod();

    }

}
