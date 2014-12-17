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
 * Object used to describe the capabilities of the device. The Device Adapter should create this
 * object when it starts and should provide it to the Protocol Adapter.
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
public class Capabilities implements Parcelable {

    private boolean mBlacklistSupport;
    private boolean mWhitelistSupport;
    private boolean mGuiConfigurationSupport;
    private int mDeviceConfigurationType;
    private boolean mCommandSupport;
    private boolean mDetectDeviceSupport;
    private boolean mPreviousPairingNeeded;
    private boolean mMonitorDisconnectionSupport;
    private String mFriendlyName;
    private String mActionName;
    private String mPackageName;
    private boolean mConnectionInitiator;
    private boolean mAvailableDevicesSupport;

    // Config related constants
    public static final int CONFIG_NOT_SUPPORTED = 0;
    public static final int CONFIG_RUNTIME_ONLY = 1;
    public static final int CONFIG_STARTUP_ONLY = 2;
    public static final int CONFIG_STARTUP_AND_RUNTIME = 3;

    public static final Parcelable.Creator<Capabilities> CREATOR
            = new Parcelable.Creator<Capabilities>() {

        /**
         * The method to recreate the object from a Parcel using the private constructor
         *
         * @param in
         *      The Parcel used to recreate the object
         *
         * @return
         *      The resulting object
         */
        public Capabilities createFromParcel(Parcel in) {
            return new Capabilities(in);
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
        public Capabilities[] newArray(int size) {
            return new Capabilities[size];
        }
    };

    /**
     * The method to recreate the object from a Parcel
     *
     * @param in
     *      The input Parcel
     */
    public void readFromParcel(Parcel in) {
        mBlacklistSupport = in.readByte() == 1;
        mWhitelistSupport = in.readByte() == 1;
        mGuiConfigurationSupport = in.readByte() == 1;
        mDeviceConfigurationType = in.readInt();
        mCommandSupport = in.readByte() == 1;
        mDetectDeviceSupport = in.readByte() == 1;
        mPreviousPairingNeeded = in.readByte() == 1;
        mMonitorDisconnectionSupport = in.readByte() == 1;
        mFriendlyName = in.readString();
        mActionName = in.readString();
        mPackageName = in.readString();
        mConnectionInitiator = in.readByte() == 1;
        mAvailableDevicesSupport = in.readByte() == 1;
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
        out.writeByte((byte) (mBlacklistSupport ? 1 : 0));
        out.writeByte((byte) (mWhitelistSupport ? 1 : 0));
        out.writeByte((byte) (mGuiConfigurationSupport ? 1 : 0));
        out.writeInt(mDeviceConfigurationType);
        out.writeByte((byte) (mCommandSupport ? 1 : 0));
        out.writeByte((byte) (mDetectDeviceSupport ? 1 : 0));
        out.writeByte((byte) (mPreviousPairingNeeded ? 1 : 0));
        out.writeByte((byte) (mMonitorDisconnectionSupport ? 1 : 0));
        out.writeString(mFriendlyName);
        out.writeString(mActionName);
        out.writeString(mPackageName);
        out.writeByte((byte) (mConnectionInitiator ? 1 : 0));
        out.writeByte((byte) (mAvailableDevicesSupport ? 1 : 0));
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
    public Capabilities(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Default constructor
     */
    public Capabilities() {}

    /**
     * The constructor that should be used when creating a Capabilities object. All capabilities
     * must be specified.
     *
     * @param blacklistSupport True if the Device Adapter supports blacklists
     * @param whitelistSupport True if the Device Adapter supports whitelists
     * @param guiConfigurationSupport True if the Device Adapter can be configured through a GUI
     * @param deviceConfigurationType Specify the kind of device configuration supported by Device Adapter
     * @param commandSupport True if Device Adapter supports commands
     * @param detectDeviceSupport True if the Device Adapter supports detection of the devices
     * @param previousPairingNeeded True if the Device Adapter needs the devices to be paired
     * @param monitorDisconnectionSupport True if the Device Adapter can notify PA when a device disconnects
     * @param friendlyName A human readable name for the Device Adapter
     * @param actionName The String that should be used to start the service
     * @param packageName The name of the package as it will be returned in the ComponentName
     * @param connectionInitiator True if the Device Adapter is connection initiator, false if it is connection target
     * @param availableDevicesSupport True if the Device Adapter supports the provision of the Available Devices
     */
    public Capabilities(
            boolean blacklistSupport,
            boolean whitelistSupport,
            boolean guiConfigurationSupport,
            int deviceConfigurationType,
            boolean commandSupport,
            boolean detectDeviceSupport,
            boolean previousPairingNeeded,
            boolean monitorDisconnectionSupport,
            String friendlyName,
            String actionName,
            String packageName,
            boolean connectionInitiator,
            boolean availableDevicesSupport) {

        mBlacklistSupport = blacklistSupport;
        mWhitelistSupport = whitelistSupport;
        mGuiConfigurationSupport = guiConfigurationSupport;
        mDeviceConfigurationType = deviceConfigurationType;
        mCommandSupport = commandSupport;
        mDetectDeviceSupport = detectDeviceSupport;
        mPreviousPairingNeeded = previousPairingNeeded;
        mMonitorDisconnectionSupport = monitorDisconnectionSupport;
        mFriendlyName = friendlyName;
        mActionName = actionName;
        mPackageName = packageName;
        mConnectionInitiator = connectionInitiator;
        mAvailableDevicesSupport = availableDevicesSupport;

    }

    /**
     * States whether Device Adapter supports blacklist or not. If true, the Device Adapter should provide
     * working implementation of the following methods: addDeviceToBlackList, removeDeviceFromBlacklist,
     * getBlacklist, setBlacklist.
     *
     * @return True if Device Adapter supports blacklist, false otherwise
     */
    public boolean hasBlacklist() {
        return mBlacklistSupport;
    }

    /**
     * States whether Device Adapter supports whitelist or not. If true, the Device Adapter should provide
     * working implementation of the following methods: addDeviceToWhiteList, removeDeviceFromWhitelist,
     * getWhitelist, setWhitelist.
     *
     * @return True if Device Adapter supports whitelist, false otherwise
     */
    public boolean hasWhitelist() {
        return mWhitelistSupport;
    }

    /**
     * States whether Device Adapter supports configuration through a GUI. If true, the Device Adapter
     * should provide working implementation of the following method: getDAConfigActivityName.
     *
     * @return True if the Device Adapter supports configuration through a GUI, false otherwise
     */
    public boolean isGuiConfigurable() {
        return mGuiConfigurationSupport;
    }

    /**
     * Retrieve the information about whether the configuration is supported by the Device Adapter
     * and, if so, what kind of configuration it supports. If supported, the Device Adapter
     * should provide working implementation of the following methods: setDeviceConfig.
     *
     * @return int a value between 0 and 3:
     * 0 = CONFIG_NOT_SUPPORTED
     * 1 = CONFIG_RUNTIME_ONLY, configuration can only be made at runtime
     * 2 = CONFIG_STARTUP_ONLY, configuration can only be made upon startup
     * 3 = CONFIG_STARTUP_AND_RUNTIME, configuration can be made both at runtime or upon startup
     */
    public int getDeviceConfigurationType() {
        return mDeviceConfigurationType;
    }

    /**
     * States whether the Device Adapter supports the sending of commands. If supported, the Device
     * Adapter should provide working implementation of the following methods: execCommand,
     * getCommandList
     *
     * @return True is DA supports commands, false otherwise
     */
    public boolean supportCommands() {
        return mCommandSupport;
    }

    /**
     * States whether the Device Adapter is the initiator of the communication with the devices (it
     * connects to the devices) or if it's the target (the devices automatically connect to it). If
     * true, the Device Adapter should provide working implementation of the following methods:
     * connectDev, forceConnectDev, disconnectDev, getConnectedDevices.
     *
     * @return True if the Device Adapter is the communication initiator, false if it's the target
     */
    public boolean isCommunicationInitiator() {
        return mConnectionInitiator;
    }

    /**
     * States whether the Device Adapter supports the detection of nearby devices. If supported, the
     * Device Adapter should provide working implementation of the following methods: detectDevices.
     *
     * @return True if the Device Adapter supports detection of nearby devices, false otherwise
     */
    public boolean canDetectDevice() {
        return mDetectDeviceSupport;
    }

    /**
     * States whether the Device Adapter needs the devices to be already paired in order to use them.
     *
     * @return True if the Device Adapter needs the devices to be already paired, false otherwise
     */
    public boolean needsPreviousPairing() {
        return mPreviousPairingNeeded;
    }

    /**
     * States whether the Device Adapter can monitor the disconnection of the devices. If supported,
     * the Device Adapter should call the following methods upon device disconnection: deviceDisconnected.
     *
     * @return True if the Device Adapter can monitor the disconnection of the devices, false otherwise
     */
    public boolean canMonitorDisconnection() {
        return mMonitorDisconnectionSupport;
    }

    /**
     * Retrieve the Friendly Name of the Device Adapter, one that is both human readable and self-explanatory.
     *
     * @return The Friendly Name of the Device Adapter
     */
    public String getFriendlyName() {
        return mFriendlyName;
    }

    /**
     * Retrieve the Action Name to use in order to bind the Device Adapter Service. Please note
     * that since Android 5.0 (Lollipop) implicit intents are not supported anymore to bind services,
     * so the action name must be an explicit one.
     *
     * @return The (explicit) Action Name to use in order to bind the Device Adapter Service
     */
    public String getActionName() {
        return mActionName;
    }

    /**
     * Retrieve the Package Name of the Device Adapter.
     *
     * @return The Package Name of the Device Adapter
     */
    public String getPackageName() {
        return mPackageName;
    }

    /**
     * States whether the Device Adapter has the ability of recognising if it can handle a device or not,
     * and consequently if it can provide the list of the Available Devices or not. If supported,
     * the Device Adapter should provide working implementation of the following methods:
     * getPairedDevicesAddress.
     *
     * @return True if Device Adapter can provide the list of Available Devices, false otherwise
     */
    public boolean canProvideAvailableDevice() {
        return mAvailableDevicesSupport;
    }

}
