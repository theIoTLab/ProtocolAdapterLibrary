#Protocol Adapter Library

FI-STAR Phase: Beta  
Document version: 1.0 (draft)  
Date: 05/03/2015

##What is the Protocol Adapter Library
The Protocol Adapter Library is a component of the Protocol Adapter (more about the Protocol Adapter [here](https://github.com/theIoTLab/ProtocolAdapterManager/blob/master/Protocol%20Adapter%20Guide.md)). This library contains all the objects and facilities (parcelable objects, AIDL interfaces, etc.) needed to develop applications that make use of the Protocol Adapter. Once included in your project, you won’t need to worry about low level details, but instead you can focus on implementing your logic, taking for granted the underlying infrastructure and functionalities provided by the Protocol Adapter. Please note that the library is not only for using in applications, but it is also used by us in the Protocol Adapter Manager and in every Device Adapter. We released this library in the form of an AAR package.

##Working with the Protocol Adapter Library
###How to include the library in a project
The first thing to do in order to work with the Protocol Adapter is to include the Protocol Adapter Library inside your project. Since Android Studio is now the official Android IDE and since we used it as our IDE when developing the entire project, we will cover here the AAR package inclusion on Android Studio. For other IDEs, you should be able to find abundant resources on-line.
Unluckily, to date (Mar. 2015) Android Studio does not offer a straightforward method to include an AAR package, but nevertheless the process is quite simple.

First of all you should copy the AAR file inside the `libs` directory of your app module. You can do this by simply copy-pasting the file from your file manager directly in the IDE.

Then you should edit the build.gradle file of your app module and add these lines. If the `dependencies` section already exists (this is the most common case) just add the `compile` line to the existing section.

    // This entry is added in order to use the AAR library
    repositories {
        flatDir { dirs 'libs' }
    }
    dependencies {
      // This is the entry that adds the dependency from the AAR library
      compile 'eu.fistar.sdcs.pa.common:protocol-adapter-lib:3.4.4@aar'
     }

The string passed as an argument of `compile` is made of 4 parts: the package name, the file name, the library version and the @aar suffix. To date (Mar. 2015) 3.4.4 is the latest version of the library, but you should take care of inserting the right version of the library here, the one that matches with the file you just copied in the project.
Finally, you should force a sync of the project with gradle files. You can do this by clicking the specific button.
If you want to use a directory other than `libs` just use the same name in the `build.gradle` file.

###Using the library in an application
Once the previous step is completed, you can start using the Protocol Adapter.

The first thing you should keep in mind is that the Protocol Adapter is a bound service using the IProtocolAdapter AIDL interface and a pool of Parcelable objects that can flow through it, so you should bind it before you can use it. Moreover, it expects you to provide an implementation of the IProtocolAdapterListener and pass it over to Protocol Adapter after successful binding in order to establish a bidirectional communication channel.

So, there are two main aspects that we cover here: implementation of the IProtocolAdapterListener interface and connection to Protocol Adapter.

####Implementation of the IProtocolAdapterListener interface
This interface is used by Protocol Adapter itself to notify events and deliver data to your application. There is not a single way to implement it, but we warmly suggest you to declare a private final field inside your class and to assing it the implementation in the form of an anonymous class implementing the IProtocolAdapterListener.Stub interface.

Example code follows:

     private final IProtocolAdapterListener.Stub paListener = new IProtocolAdapterListener.Stub() {
        @Override
        public void registerDevice(DeviceDescription deviceDescription, String daId) throws RemoteException {
            /*************************
            * Your logic goes here
            **************************/
            Log.d(LOGTAG,  "Device registered " + deviceDescription.getDeviceID());
        }

        @Override
        public void pushData(List<Observation> observations, DeviceDescription deviceDescription) throws RemoteException {
            /*************************
            * Your logic goes here
            **************************/
            Log.d(LOGTAG,  "Data pushed from device " + deviceDescription.getDeviceID());
        }

        @Override
        public void deregisterDevice(DeviceDescription deviceDescription) throws RemoteException {
            /*************************
            * Your logic goes here
            **************************/
            Log.d(LOGTAG,  "Device deregistered " + deviceDescription.getDeviceID());
        }

        @Override
        public void registerDeviceProperties(DeviceDescription deviceDescription) throws RemoteException {
            /*************************
            * Your logic goes here
            **************************/
            Log.d(LOGTAG, "Device properties registered: " + deviceDescription.getDeviceID());
        }

        @Override
        public void deviceDisconnected(DeviceDescription deviceDescription) throws RemoteException {
            /*************************
            * Your logic goes here
            **************************/
            Log.d(LOGTAG, "Device disconnected: " + deviceDescription.getDeviceID());
        }

        @Override
        public void log(int logLevel, String daId, String message) throws RemoteException {
            /*************************
            * Your logic goes here
            **************************/
            Log.d(LOGTAG, "LOG! Level: " + logLevel + "; DA: " + daId + "; Message: " + message + ";");
        }

        @Override
        public void onDaConnected(String daId) {
            /*************************
            * Your logic goes here
            **************************/
            Log.d(LOGTAG, "Device Adapter " + daId + " completed the initialization phase");
        }
    };

As you can see, this is a dummy implementation, but it includes all the required methods.
For a brief description of all involved methods see the next section.

####Binding to the Protocol Adapter
The first thing you should do in order to interact with the Protocol Adapter is to bind the service. It is a regular Android bound service using an AIDL interface. Since you may not be familiar with them, here you find included some sample code to perform the connection.

You should create an implementation of the ServiceConnection object, which contains the callbacks that will be invoked when the binding is successfully performed or when the bound is interrupted for some reason. Again, there’s not a single way to do this, but we warmly suggest you to declare a private final field inside your class and to assing it the implementation in the form of an anonymous class implementing the ServiceConnection interface.

Example code follows:

    private ServiceConnection servConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // Extract the IProtocolAdapter implementation from the binder
            pa = IProtocolAdapter.Stub.asInterface(iBinder);
            try {
                // Register the listener inside the Protocol Adapter
                pa.registerPAListener(paListener);
            } catch (RemoteException e) {
                Log.d(LOGTAG, "Error contacting Protocol Adapter");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(LOGTAG, "Protocol Adapter disconnected unexpectedly");
        }
    };

You may want to make `pa` a field of your class, so you can access its value from everywhere in your class.

Now that the ServiceConnection is implemented, you can bind the Protocol Adapter service. Here you can find a sample code using an explicit intent to bind the service (as required since Android 5.0 Lollipop):

    // Create the Intent to start the PA with
    Intent intent = new Intent().setComponent(new ComponentName(PAAndroidConstants.PA_PACKAGE, PAAndroidConstants.PA_ACTION));

    // Start the Protocol Adapter
    bindService(intent, servConn, Context.BIND_AUTO_CREATE);

Once the binding is done, Android will call the “onServiceConnected” method of your implementation of ServiceConnection.

##An inside look at the Protocol Adapter Library

In the following paragraphs you will find a brief analysis of some components of the library. For a complete technical reference, please refer to the library's JavaDoc that you can find [here](https://github.com/theIoTLab/ProtocolAdapterLibrary/releases/download/v3.4.4/protocol-adapter-lib-3.4.4-javadoc.zip).

###The IProtocolAdapter AIDL interface
This is the AIDL interface implemented by the Protocol Adapter Manager and includes all the methods used by Applications to communicate with the PA.
These are the most significant methods:

* `List<DeviceDescription> getConnectedDevices()` - Returns a list of all the devices connected at the moment with the Device Adapter.
* `Map<String, List<String>> getDADevices()` - Returns a map containing the Device ID of all the devices paired with the smartphone that can be handled by at least one DA as the key, and a list of DA IDs of DA that can handle that device as the value.
* `void setDeviceConfig(Map config, String devId)` - Set the specific configuration of a device managed by the Device Adapter passing a data structure with key-value pairs containing all possible configuration parameters and  their values, together with the device ID. This should be done before starting the Device  Adapter, otherwise standard configuration will be used. Depending on capabilities, this  could also be invoked when the DA is already running.
* `void startDA(String daId)` - Start the Device Adapter operations. This will cause the PA to bind the DA's service and start the DA.
* `void stopDA(String daId)` - Stop the Device Adapter operations. This will cause the PA to stop the DA and unbind the related service.
* `Map<String, Capabilities> getAvailableDAs()` - Return a Map with all the available DAs in the system. The keys of the Map are the DAs' ID and the values are the related Capabilities object.
* `Capabilities getDACapabilities(String daId)` - Return the object describing the capabilities of the specified DA.
* `void connectDev(String devId)` - Connect to the device whose MAC Address is passed as an argument.
* `void forceConnectDev(String devId, String daId)` - Force connection to the device whose devID is passed as an argument using the specified Device Adapter.
* `void disconnectDev(String devId)` - Disconnect from the device whose MAC Address is passed as an argument.
* `void registerPAListener(IBinder application)` - Called by the Application to register the binder representing its interface inside the Protocol Adapter.
* `void addDeviceToWhitelist(String devId)` - Add a device to the Device Adapter whitelist, passing its device ID as an argument. Please note that this insertion will persist, even through Device Adapter reboots, until the device it's removed from the list. Every device adapter takes care of checking the format of the address passed as an argument and, if it does not support that kind of address, it will safely ignore that address.
* `void removeDeviceFromWhitelist(String devId)` - Remove from the whitelist the device whose device ID is passed as an argument. If the device is not in the list, the request will be ignored.
* `List<String> getWhitelist()` - Retrieve all the devices in the whitelist of the DA. If there's no devices, an empty list is returned.
* `void setWhitelist(List<String> whiteList)` - Set a list of devices in the whitelist all together, passing their device IDs as an argument. Please note that this insertion will persist, even through Device Adapter reboots, until the devices are removed from the list. Every device adapter will take care of checking the format of the address passed as an argument one by one and, if it does not support that kind of address, it will safely ignore that address.
* `void addDeviceToBlackList(String devId)` - Add a device to the Device Adapter blacklist, passing its device ID as an argument. Please note that this insertion will persist, even through Device Adapter reboots, until the device it's removed from the list. Every device adapter will take care of checking the format of the address passed as an argument and, if it does not support that kind of address, it will safely ignore that address.
* `void removeDeviceFromBlacklist(String devId)` - Remove from the blacklist the device whose device ID is passed as an argument. If the device is not in the list, the request can be ignored.
* `List<String> getBlacklist()` - Retrieve all the devices in the blacklist of the DA. If there's no devices, an empty list is returned.
* `void setBlackList(List<String> blackList)` - Set a list of devices in the blacklist all together, passing their device IDs as an argument. Please note that this insertion will persist, even through Device Adapter reboots, until the devices are removed from the list. Every device adapter will take care of checking the format of the address passed as an argument one by one and, if it does not support that kind of address, it will safely ignore that address.
* `List<String> getCommandList(String daId)` - Return all the commands supported by the Device Adapter for its devices.
* `void execCommand(String command, String parameter, String devId)` - Execute a command supported by the device. You can also specify a parameter, if the command allows or requires it.

Remember that methods of the IProtocolAdapter interface are not guaranteed to return immediately when they are called and may block. So, if you are calling them from inside an Activity and you are concerned about “Application Not Responding” errors (you should really be), you better call them from a thread other than the UI one.

###The IProtocolAdapterListener AIDL Interface
This is the AIDL interface implemented by an Application and includes all the methods used by the Protocol Adapter Manager to communicate with Applications.
These are the most significant methods:

* `void registerDevice(DeviceDescription devDesc, String daId)` - Called by Protocol Adapter to register a new device.
* `void pushData(List<Observation> observations, DeviceDescription devDesc)` - Called by Protocol Adapter to push new measurements data coming from the device.
* `void deregisterDevice(DeviceDescription devDesc)` - Called by Protocol Adapter to deregister a device with the protocol adapter when it is not available anymore.
* `void registerDeviceProperties(DeviceDescription devDesc)` - Called by Protocol Adapter to register a new property for a device. This is not used at the moment, since all the job is done with registerDevice.
* `void deviceDisconnected(DeviceDescription devDesc)` - Called by Protocol Adapter when a device disconnects.
* `onDaConnected(String daId)` - Called by Protocol Adapter when a Device Adapter completes the binding process with the PA.
* `void log(int logLevel, String daId, String message)` - Called by Protocol Adapter to forward to the Application a log message received from one of the Device Adapters or generated locally.

Remember that methods of the IProtocolAdapterListener interface are not guaranteed to return immediately when they are called and may block. So, if you are calling them from inside an Activity and you are concerned about “Application Not Responding” errors (you should really be), you better call them from a thread other than the UI one.

###The Parcelable Objects
The library includes a set of objects used to communicate data and represent devices, capabilities and events. Because these objects must flow through AIDL interfaces, they all implements the Parcelable interface, as required by Android. Parcel is the Android proprietary lightweight serialization standard and objects implementing the Parcelable interface are required to also implements a number of methods used to perform the serialization of an object into a Parcel and the deseralization of a Parcel into an object. Follows a brief description of all these objects.

####The DeviceDescription object
The DeviceDescription object represents a definition of a device. A device definition is used to represent a device and all its properties, such as sensors.
Here are the getter methods to retrieve the properties of the DeviceDescription:

* `public String getDeviceID()` - Returns the device unique identifier as provided by the device.
* `public String getSerialNumber()` - Returns the model number of the device.
* `public String getModelName()` - Returns the model name of the device.
* `public String getManufacturerName()` - Returns the manufacturer name of the device.
* `public List<SensorDescription> getSensorList()` - Returns the list of sensors belonging to the device.
* `public String getAddress()` - Returns the physical address of the device.

####The SensorDescription object
The SensorDescription object defines a sensor. A sensor definition is used to represent a sensor of a device and its characteristics.
Here are the getter methods to retrieve the properties of the SensorDescription:

* `public String getSensorName()` - Returns the name of the sensor (i.e. Pulsimeter).
* `public String getMeasurementUnit()` - Returns the unit of measure used by the sensor (i.e. Bpm).
* `public String getPropertyName()` - Returns the property name of the sensor (i.e. Heart Rate).

####The Observation object
The Observation object describes observations. An observation is used to encapsulate one or more measurements coming from a sensor and carrying some meta data together with the measurements.
Here are the getter methods to retrieve the properties of the Observation:

* `public String getPropertyName()` - Returns the property name (same as propertyName in SensorDescription).
* `public String getMeasurementUnit()` - Returns the unit of measure of the property (same as measurementUnit in SensorDescription).
* `public List<String> getValues()` - Returns the values observed for the property.
* `public long getPhenomenonTime()` - Returns the timestamp associated with the measurement.
* `public long getDuration()` - Returns the duration of the measurement.

####The Capabilities object
The Capabilities object is used to describe the capabilities of the device. The Device Adapter creates this object when it starts (usually defining it as a constant) and provides it to the Protocol Adapter.
Here are the public methods used to access the Capabilities of the Device Adapter:

* `public boolean hasBlacklist()` - States whether Device Adapter supports blacklist or not. If true, the Device Adapter should provide working implementation of the following methods: `addDeviceToBlackList()`, `removeDeviceFromBlacklist()`, `getBlacklist()`, `setBlacklist()`.
* `public boolean hasWhitelist()` - States whether Device Adapter supports whitelist or not. If true, the Device Adapter should provide working implementation of the following methods: `addDeviceToWhiteList()`, `removeDeviceFromWhitelist()`, `getWhitelist()`, `setWhitelist()`.
* `public boolean isGuiConfigurable()` - States whether Device Adapter supports configuration through a GUI. If true, the activity name can be retrieved using the `getConfigActivityName()` method.
* `public int getDeviceConfigurationType()` - Retrieve the information about whether the configuration is supported by the Device Adapter and, if so, what kind of configuration it supports. If supported, the Device Adapter should provide working implementation of the following method: `setDeviceConfig()`.
Acceptables values are between 0 and 3:
    * `0 = CONFIG_NOT_SUPPORTED` configuration is not supported
    * `1 = CONFIG_RUNTIME_ONLY`, configuration can only be made at runtime
    * `2 = CONFIG_STARTUP_ONLY`, configuration can only be made upon startup
    * `3 = CONFIG_STARTUP_AND_RUNTIME`, configuration can be made both at runtime or upon startup
* `public boolean supportCommands()` - States whether the Device Adapter supports the sending of commands. If supported, the Device Adapter should provide working implementation of the following methods: `execCommand()`, `getCommandList()`.
* `public boolean isCommunicationInitiator()` - States whether the Device Adapter is the initiator of the communication with the devices (it connects to the devices) or if it's the target (the devices automatically connect to it). If true, the Device Adapter should provide working implementation of the following methods: `connectDev()`, `forceConnectDev()`, `disconnectDev()`, `getConnectedDevices()`.
* `public boolean canDetectDevice()` - States whether the Device Adapter supports the detection of nearby devices. If supported, the Device Adapter should provide working implementation of the following methods: `detectDevices()`.
* `public boolean needsPreviousPairing()` - States whether the Device Adapter needs the devices to be already paired in order to use them.
* `public boolean canMonitorDisconnection()` - States whether the Device Adapter can monitor the disconnection of the devices. If supported, the Device Adapter should call the following methods upon device disconnection: `deviceDisconnected()`.
* `public String getFriendlyName()` - Retrieve the Friendly Name of the Device Adapter, one that is both human readable and self-explanatory.
* `public String getActionName()` - Retrieve the Action Name to use in order to bind the Device Adapter Service. Please note that since Android 5.0 (Lollipop) implicit intents are not supported anymore to bind services, so the action name must be an explicit one.
* `public String getPackageName()` - Retrieve the Package Name of the Device Adapter.
* `public String getDaId()` - Retrieve the ID of the Device Adapter.
* `public ComponentName getConfigActivityName()` - Retrieve the reference to the activity that can be used to configure the Device Adapter. Such reference is provided as a ComponentName object, so it can be used directly by the recipient.
* `public boolean canProvideAvailableDevice()` - States whether the Device Adapter has the ability to recognise if it can handle a device or not, and consequently if it can provide the list of the Available Devices or not. If supported, the Device Adapter should provide working implementation of the following methods: `getPairedDevicesAddress()`.

## Authors, Contact and Contributions
As the licence reads, this is free software released by Consorzio Roma Ricerche. The authors (Marcello Morena and Alexandru Serbanati) will continuously add support for even more medical devices, but external contributions are welcome. Please have a look at the TODO file to know what we are working on and contact us (protocoladapter[at]gmail[dot]com) if you plan on contributing.

## Acknowledgement
This work was carried out with the support of the FI-STAR project (“Future Internet Social and Technological Alignment Research”), an integrated project funded by the European Commission through the 7th ICT - Framework Programme (318389).
