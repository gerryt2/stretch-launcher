# StretchSense Bluetooth LE Android Communication Library

StretchSense is a global supplier of soft sensors. These soft sensors are perfect for measuring the complex movements of people and soft objects in our environments. 

## About
### Background
The StretchSense Android API has been developed to demonstrate how to establish a connection between a one or more StretchSense Fabric Evaluation circuits and a BLE enabled Android device. The platform was developed using android Studio 2.1.2 in Java with a gradle defaultConfig minSdkVersion 19 and a targetSdkVersion 22.
First download and unzip the StretchSense Library from https://github.com/Stretchsense-Limited.

## Getting Started
You can use the "Empty Project" to start your application. All the set up are already integrated in an Empty Activity provided by the Android Studio. You can now use the functions provided by the StretchSense Android Library.
### Quick Start using the StretchSense Android Library

The steps required to use this library and connect the StretchSense fabric sensor to your Android Device are:

*	Initialization: Turn on the Bluetooth, initialize the library and the scanner
*	Connection: Connect to the device
*	Streaming:  Start reading data

#### Initialization
First, on your mainActivity.java, you need to declare your object from the StretchSense Class. All communication with the sensor will go through this object. 

        *   private StretchSenseLibraryManager mBleManager;
    
    In the onCreate function, declare the object

        *   mBleManager = new StretchSenseLibraryManager(this);

Using this object, you have to start the Bluetooth on the onResume function:
 
        *   if (mBleManager.isTheBleEnable(this)) {
                mBleManager.initialiseTheManager(this);
            }

#### Discovery
Your manager and peripheral list are now set up. You can start scanning for a peripheral using the function scanning. It will scan during 20 sec. Yoy can change the duration in the file Constants.java 

        *   mBleManager.startScanningForAPeriod();



#### Connection

If the parameter AUTOCONNECT in the Constants.java is true, everything is automatic. If not, you will need to connect the sensor using one of the function connect using a String or a device.

Once a peripheral has been found, the connect function will create a link between your application and the device. If the parameter AUTOCONNECT in the Constants.java is true, everything is automatic.

If the constant is a false, the library allow you to connect to the peripheral using his address or the whole information of the peripheral, as a device. The functions are respectively: connectWithAddress() and connectWithDevice().

This example use the first function. You can connect any peripheral available from the list, just change the index (here 0 refers to the first peripheral detected). 

	myPeripheralAvailable = listPeripheralAvailable[0]         
	stretchsenseObject.connectToPeripheralWithUUID(myPeripheralAvailable)

If you want to connect all the peripherals available, you can use a for loop:

    for (StretchSensePeripheralAvailable myPeripheral: mBleManager.listStretchSensePeripheralsAvailable) {
        String addressOfTheSensor = myPeripheral.device.getAddress();
        mBleManager.connectWithAddress(addressOfTheSensor);
    }

#### Streaming
You are now connected and can start reading data values from the peripheral.

    for (StretchSensePeripheralConnected myPeripheral: mBleManager.listStretchSensePeripheralsConnected) {
        Double valueOfTheSensor = myPeripheral.value;
    }

## Compatible Devices

### Bluetooth
This library has been developed exclusively for Bluetooth 4.0 (BLE), also known as Bluetooth Low Energy. Compatible devices and sensors are listed below. 

### StretchSense
Only the StretchSense Fabric Evaluation circuit is compatible with the support library.
http://stretchsense.com/evaluation-kits/fabric-stretch-sensor-evaluation-kit/

### Android
BLE was first introduce in 2012 and requires a minimum operating system version of Android 4.3

The following device use Bluetooth 4.0:
*	Google Nexus 4 and later
*	LG G2 and later
*   OnePlus One and later
*   Samsung Galaxy S4 and later
*   Sony Xperia Z and later

## License
The 'StretchSense Bluetooth LE Android Communication Library' is available under the MIT License attached within the root directory of this project.

## Support

If you've found an error in this sample, please file an issue on GitHub or contact support@stretchsense.com