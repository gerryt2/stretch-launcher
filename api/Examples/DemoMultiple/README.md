# Example 3 - Multiple Circuit Connection  

StretchSense is a global supplier of soft sensors. These soft sensors are perfect for measuring the complex movements of people and soft objects in our environments. 

## About
### Background
The StretchSense Android API has been developed to demonstrate how to establish a connection between a one or more StretchSense Fabric Evaluation circuits and a BLE enabled Android device. The platform was developed using android Studio 2.1.2 in Java with a gradle defaultConfig minSdkVersion 19 and a targetSdkVersion 22.
This project and additional examples are available on StretchSense's Github Page https://github.com/Stretchsense-Limited.

### Usage
The Multiple Circuit Connection application demonstrates how, up to 10, simultaneous connections can be established by using the StretchSense (Bluetooth LE) Android Library. 

This application displays the list of sensors available and connected in two seperate tables. 
Once a sensor is available (advertising), it's address is displayed on the first table. You can change the type of connection (automatic or by clicking on the sensor row) with the toogle on the top right edge. 

The background color of the cell is linked to it state, if it’s colored, it’s connected but if it’s white it’s not. 

Once a sensor is connected, it appears in the second table with it address, value and unique number with it own color.

Using the slider on the bottom, you can change the sampling time of the sensor between two values.

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