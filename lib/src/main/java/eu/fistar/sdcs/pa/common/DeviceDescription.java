/**
 * Copyright (C) 2014 Consorzio Roma Ricerche
 * All rights reserved
 *
 * This file is part of the Protocol Adapter software, available at
 * https://github.com/theIoTLab/ProtocolAdapter .
 *
 * The Protocol Adapter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://opensource.org/licenses/LGPL-3.0
 *
 * Contact Consorzio Roma Ricerche (protocoladapter@gmail.com)
 */

package eu.fistar.sdcs.pa.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents a definition of a device.<br>
 * A device definition is used to represent a device and all its properties, such as sensors.
 *<br>
 * Examples of Devices are<br>
 * - HDP Device<br>
 * - Smart BT Device<br>
 * - Proprietary Devices
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
public class DeviceDescription implements Parcelable {

    private String deviceID; // The unique device identifier
    private String serialNumber; // The device's serial number, empty if not automatically provided by the device
    private String modelName; // The model name
    private String manufacturerName; // The manufacturer name
    private List<SensorDescription> sensorList = new ArrayList<SensorDescription>(); // The list of the properties
    private String address; // The MAC Address of the device
    private boolean registered;

    public static final Creator<DeviceDescription> CREATOR
            = new Creator<DeviceDescription>() {

        /**
         * The method to recreate the object from a Parcel using the private constructor
         *
         * @param in
         *      The Parcel used to recreate the object
         *
         * @return
         *      The resulting object
         */
        public DeviceDescription createFromParcel(Parcel in) {
            return new DeviceDescription(in);
        }

        /**
         * The method to create an array of objects
         *
         * @param size
         *      The size of the array
         *
         * @return
         *      The resulting array
         */
        public DeviceDescription[] newArray(int size) {
            return new DeviceDescription[size];
        }
    };

    /**
     * The method to recreate the object from a Parcel
     *
     * @param in
     *      The input Parcel
     */
    public void readFromParcel(Parcel in) {
        deviceID = in.readString();
        serialNumber = in.readString();
        modelName = in.readString();
        manufacturerName = in.readString();
        try {
            in.readTypedList(sensorList, SensorDescription.CREATOR);
        } catch (NullPointerException e) {
            sensorList = new ArrayList<SensorDescription>();
        }
        address = in.readString();
        registered = in.readByte() == 1;
    }

    /**
     * The method to serialize the object as a Parcel
     *
     * @param out
     *      The resulting Parcel
     *
     * @param flags
     *      Some flags
     */
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(deviceID != null ? deviceID : "");
        out.writeString(serialNumber != null ? serialNumber : "");
        out.writeString(modelName != null ?  modelName : "");
        out.writeString(manufacturerName != null ? manufacturerName : "");
        out.writeTypedList(sensorList != null ? sensorList : new ArrayList<SensorDescription>());
        out.writeString(address != null ? address : "");
        out.writeByte((byte) (registered ? 1 : 0));
    }

    /**
     * The describe content method required by Parcelable
     *
     * @return
     *      The contents description
     */
    public int describeContents() {
        return 0;
    }

    /**
     * The Constructor required by Parcelable
     */
    public DeviceDescription(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Default constructor
     */
    public DeviceDescription() {}

    public DeviceDescription(String mAddress) {
        address = mAddress;
        registered = false;
    }

    /**
     * Constructor used to create a new object from a derivated object of the same type
     *
     * @param derivObj
     */
    public DeviceDescription(DeviceDescription derivObj) {
        deviceID = derivObj.getDeviceID();
        serialNumber = derivObj.getSerialNumber();
        modelName = derivObj.getModelName();
        manufacturerName = derivObj.getManufacturerName();
        sensorList = new ArrayList<SensorDescription>();
        if (derivObj.getSensorList() != null && !derivObj.getSensorList().isEmpty()) {
            for (SensorDescription tmpSens : derivObj.getSensorList()) {
                sensorList.add(new SensorDescription(tmpSens));
            }
        }
        address = derivObj.getAddress();
        registered = derivObj.isRegistered();
    }

    /**
     * Set the device attributes that are obtained in the HDP Attributes and the HDP Configuration
     * retrieval phase
     *
     * @param mDeviceID
     *      The unique device ID
     *
     * @param mSerialNumber
     *      The serial number of the device (not used right now)
     *
     * @param mModelName
     *      The model name
     *
     * @param mManufacturerName
     *      The manufacturer name
     *
     * @param mSensorList
     *      The list of the sensor associated with the device
     *
     * @param mAddress
     *      The physical address of the device
     */
    public DeviceDescription(String mDeviceID, String mSerialNumber, String mModelName, String mManufacturerName, List<SensorDescription> mSensorList, String mAddress) {
        deviceID = mDeviceID;
        serialNumber = mSerialNumber;
        modelName = mModelName;
        manufacturerName = mManufacturerName;
        sensorList = mSensorList;
        address = mAddress;
        registered = false;
    }

    /**
     * Returns the device unique identifier as provided by the device
     *
     * @return
     *      The unique identifier for that device
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * Returns the model number of the device
     *
     * @return
     *      The model number of the device
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Returns the model name of the device
     *
     * @return
     *      The model name of the device
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Returns the manufacturer name of the device
     *
     * @return
     *      The manufacturer name of the device
     */
    public String getManufacturerName() {
        return manufacturerName;
    }

    /**
     * Returns the list of sensors belonging to the device
     *
     * @return
     *      The list of sensors belonging to the device
     */
    public List<SensorDescription> getSensorList() {
        return sensorList;
    }

    /**
     * Returns the physical address of the device
     *
     * @return
     *      The physical address of the device
     */
    public String getAddress() {
        return address;
    }

    /**
     * Used by Device Adapters to mark a device as registered or unregistered
     *
     * @param mRegistered
     *      The boolean value used to mark the device as registered or unregistered
     */
    public void setRegistered(boolean mRegistered) {
        registered = mRegistered;
    }

    /**
     * Returns a boolean value indicating whether the device is registered in the Device Adapter or not
     *
     * @return
     *      The boolean value indicating whether the device is registered or not
     */
    public boolean isRegistered() {
        return registered;
    }

    /**
     * Returns a read-friendly String representing the object
     *
     * @return
     *      The String representing the object
     */
    public String toString() {
        String propStr = "\n";

        for (SensorDescription tmpSens: sensorList) {
            propStr += tmpSens.toString();
        }

        return "ID: "+deviceID+"\nModel Number: "+ serialNumber +"\nModel Name: "+modelName+
                "\nManufacturer: "+manufacturerName+"\nAddress: "+address+"\nProperties: "+propStr;
    }

}
