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

/**
 * This Class defines sensors.<br>
 * A sensor definition is used to represent a sensor of a device and its characteristics.
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
public class SensorDescription implements Parcelable {

    private String sensorName;          // The sensor name (i.e. pulsimeter)
    private String measurementUnit;     // The measurement unit of the property (i.e. bpm)
    private String propertyName;        // The name of the property (i.e. pulse)

    // The static field CREATOR required by Parcelable
    public static final Parcelable.Creator<SensorDescription> CREATOR
            = new Parcelable.Creator<SensorDescription>() {

        /**
         * The method to recreate the object from a Parcel using the private constructor
         *
         * @param in
         *      The Parcel used to recreate the object
         *
         * @return
         *      The resulting object
         */
        public SensorDescription createFromParcel(Parcel in) {
            return new SensorDescription(in);
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
        public SensorDescription[] newArray(int size) {
            return new SensorDescription[size];
        }
    };

    /**
     * The method to recreate the object from a Parcel
     *
     * @param in
     *      The input Parcel
     */
    public void readFromParcel(Parcel in) {
        sensorName = in.readString();
        measurementUnit = in.readString();
        propertyName = in.readString();
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
        out.writeString(sensorName != null ? sensorName : "");
        out.writeString(measurementUnit != null ? measurementUnit : "");
        out.writeString(propertyName != null ? propertyName : "");
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
    public SensorDescription(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Default constructor
     */
    public SensorDescription() {}

    /**
     * The Constructor normally used to pass al the parameters
     *
     * @param mSensorName The name of the sensor
     * @param mMeasurementUnit The measurement unit used by the sensor
     * @param mPropertyName The name of the property provided by the sensor
     */
    public SensorDescription(String mSensorName, String mMeasurementUnit, String mPropertyName) {
        sensorName = mSensorName;
        measurementUnit = mMeasurementUnit;
        propertyName = mPropertyName;
    }

    /**
     * Constructor used to create a new object from a derivated object of the same type
     *
     * @param derivObj
     */
    public SensorDescription(SensorDescription derivObj) {
        sensorName = derivObj.getSensorName();
        measurementUnit = derivObj.getMeasurementUnit();
        propertyName = derivObj.getPropertyName();
    }

    /**
     * Returns the sensor name
     *
     * @return
     *      The sensor name
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * Returns the unit of measure used by the sensor
     *
     * @return
     *      The unit of measure used by the sensor
     */
    public String getMeasurementUnit() {
        return measurementUnit;
    }

    /**
     * Returns the property name of the sensor
     *
     * @return
     *      The property name of the sensor
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Returns a read-friendly String representing the object
     *
     * @return
     *      The String representing the object
     */
    public String toString() {
        return "Sensor Name: "+sensorName+"\nProperty Name: "+propertyName+"\nMeasurement Unit: "+
                measurementUnit+"\n";
    }
}
