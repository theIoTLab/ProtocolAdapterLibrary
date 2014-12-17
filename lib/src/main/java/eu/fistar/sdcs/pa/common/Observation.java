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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This Class describes observations.<br>
 * An observation is used to encapsulate one or more measurements coming from a sensor
 * and carrying some meta data together with the measurements.
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
public class Observation implements Parcelable {

    private String propertyName;        // Property name (same as propertyName in SensorDescription)
    private String measurementUnit;     // Unit of measure of the property
    private List<String> values = new ArrayList<String>();        // Values observed for the property
    private long phenomenonTime = 0;    // Timestamp associated with the measurement
    private long duration = 0;          // Duration of the measurement

    // The static field CREATOR required by Parcelable
    public static final Creator<Observation> CREATOR
            = new Creator<Observation>() {

        /**
         * The method to recreate the object from a Parcel using the private constructor
         *
         * @param in
         *      The Parcel used to recreate the object
         *
         * @return
         *      The resulting object
         */
        public Observation createFromParcel(Parcel in) {
            return new Observation(in);
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
        public Observation[] newArray(int size) {
            return new Observation[size];
        }
    };

    /**
     * The method to recreate the object from a Parcel
     *
     * @param in
     *      The input Parcel
     */
    public void readFromParcel(Parcel in) {
        propertyName = in.readString();
        measurementUnit = in.readString();
        try {
            in.readStringList(values);
        } catch (NullPointerException e) {
            values = new ArrayList<String>();
        }
        phenomenonTime = in.readLong();
        duration = in.readLong();
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
        out.writeString(propertyName != null ? propertyName : "");
        out.writeString(measurementUnit != null ? measurementUnit : "");
        out.writeStringList(values != null ? values : new ArrayList<String>());
        out.writeLong(phenomenonTime);
        out.writeLong(duration);
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
    public Observation(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Default constructor
     */
    public Observation() {}

    /**
     * Constructor used to create a new object from a derived object of the same type
     *
     * @param derivObj
     */
    public Observation(Observation derivObj) {
        propertyName = derivObj.getPropertyName();
        measurementUnit = derivObj.getMeasurementUnit();
        values = (derivObj.getValues() != null ? derivObj.getValues() : new ArrayList<String>());
        phenomenonTime = derivObj.getPhenomenonTime();
        duration = derivObj.getDuration();
    }

    public Observation(SensorDescription mSensor, String[] mValues) {
        propertyName = mSensor.getPropertyName();
        measurementUnit = mSensor.getMeasurementUnit();
        values = (mValues != null ? Arrays.asList(mValues) : new ArrayList<String>());
        phenomenonTime = System.currentTimeMillis();
        duration = 0;
    }

    /**
     * Returns the property name (same as propertyName in SensorDescription)
     *
     * @return
     *      The property name
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Returns the values observed for the property
     *
     * @return
     *      The values observed for the property
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * Returns the timestamp associated with the measurement
     *
     * @return
     *      The timestamp associated with the measurement
     */
    public long getPhenomenonTime() {
        return phenomenonTime;
    }

    /**
     * Returns the duration of the measurement
     *
     * @return
     *      The duration of the measurement
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Returns the unit of measure of the property
     *
     * @return
     *      The unit of measure of the property
     */
    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setProperty(String mPropertyName) {
        propertyName = mPropertyName;
    }

    public void setDuration(long mDuration) {
        duration = mDuration;
    }

    public void setMeasurementUnit(String mMeasurementUnit) {
        measurementUnit = mMeasurementUnit;
    }

    public void setValues(String[] mValues) {
        values = Arrays.asList(mValues);
    }

    public void setPhenomenonTime(long mPhenomenonTime) {
        phenomenonTime = mPhenomenonTime;
    }

    /**
     * Returns a read-friendly String representing the object
     *
     * @return
     *      The String representing the object
     */
    public String toString() {
        String valuesStr = "\n";
        for (int i = 0; i < values.size(); i++) {
            valuesStr += "Value " + Integer.toString(i+1) + " = " + values.get(i) + "\n";
        }

        return "Property Name: "+propertyName+"\nMeasurement Unit: "+measurementUnit+"\nTime: "+
                phenomenonTime+"\nDuration: "+duration+"\nValues: "+valuesStr+"\n";
    }

}
