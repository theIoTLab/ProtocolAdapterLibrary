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

package eu.fistar.sdcs.pa.common.da;

import android.os.IBinder;
import java.util.List;
import java.util.Map;
import eu.fistar.sdcs.pa.common.DeviceDescription;
import eu.fistar.sdcs.pa.common.Capabilities;

/**
 * Interface implemented by Device Adapters (DA).<br>
 * A DA provides a technological adapter and manage the use of base resources.
 *<br>
 * Example of Device Adapters are <br>
 * - HDP Device Adapter<br>
 * - SmartBT Device Adapter<br>
 * - Device Adapters used for proprietary devices
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
interface IDeviceAdapter {

    /**
     * Returns a list of all the devices connected at the moment with the Device Adapter.
     *
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    List<DeviceDescription> getConnectedDevices();

    /**
     * Returns a list of the Device IDs of all the devices paired with the smartphone and managed
     * by the specific Device Adapter.
     *
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    List<String> getPairedDevicesAddress();

    /**
     * Returns a list of devices that can be detected with a scanning.
     *
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    List<String> detectDevices();

    /**
     * Set the specific configuration of a device managed by the Device Adapter passing a data
     * structure with key-value pairs containing all possible configuration parameters and
     * their values, together with the device ID. This should be done before starting the Device
     * Adapter, otherwise standard configuration will be used. Depending on capabilities, this
     * could also be invoked when the DA is already runing.
     *
     * @param config
     *		The configuration for the device in the form of a key/value set (String/String)
     *
     * @param devID
     *		The device ID (eg. for BT devices is the MAC Address)
     *
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    void setDeviceConfig(in Map config, String devId);

    /**
     * Return the object describing the capabilities of the DA. The implementation for this
     * method is mandatory.
     *
     * @return An instance of the Capabilities object containing all the capabilities of the
     * device
     */
    Capabilities getDACapabilities();

    /**
     * Start the Device Adapter operations. The implementation for this method is mandatory.
     */
    void start();

    /**
     * Stop the Device Adapter operations. This will not close or disconnect the service. The
     * implementation for this method is mandatory.
     */
    void stop();

    /**
     * Connect to the device whose DevID is passed as an argument.
     *
     * @param devId The Device ID
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    void connectDev(String devId);

    /**
     * Force connection to the device whose devID is passed as an argument.
     *
     * @param devId The Device ID
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    void forceConnectDev(String devId);

    /**
     * Disconnect from the device whose DevID is passed as an argument.
     *
     * @param devId The Device ID
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    void disconnectDev(String devId);

    /**
     * Receive a binder from the Protocol Adapter representing its interface. The implementation
     * for this method is mandatory.
     *
     * @param pa The Protocol Adapter Binder
     */
    void registerDAListener(IBinder pa);

    /**
     * Add a device to the Device Adapter whitelist, passing its device ID as an argument.
     * Note that this insertion will persist, even through Device Adapter reboots, until
     * the device it's removed from the list. Every device adapter should check the format
     * of the address passed as an argument and, if it does not support that kind of
     * address, it can safely ignore that address.
     *
     * @param devId The Device ID
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    void addDeviceToWhitelist(String devId);

    /**
     * Remove from the whitelist the device whose device ID is passed as an argument.
     * If the device is not in the list, the request can be ignored.
     *
     * @param devId The Device ID
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    void removeDeviceFromWhitelist(String devId);

    /**
     * Retrieve all the devices in the whitelist of the DA. If there's no devices, an
     * empty list is returned.
     *
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    List<String> getWhitelist();

    /**
     * Set a list of devices in the whitelist all together, passing their device IDs as an
     * argument. Note that this insertion will persist, even through Device Adapter reboots,
     * until the devices are removed from the list. Every device adapter should check the format
     * of the address passed as an argument one by one and, if it does not support that kind of
     * address, it can safely ignore that address.
     *
     * @param devicesId The list containing the Device IDs
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    void setWhitelist(in List<String> devicesId);

    /**
     * Add a device to the Device Adapter blacklist, passing its device ID as an argument.
     * Note that this insertion will persist, even through Device Adapter reboots, until
     * the device it's removed from the list. Every device adapter should check the format
     * of the address passed as an argument and, if it does not support that kind of
     * address, it can safely ignore that address.
     *
     * @param devId The Device ID
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    void addDeviceToBlackList(String devId);

    /**
     * Remove from the blacklist the device whose device ID is passed as an argument.
     * If the device is not in the list, the request can be ignored.
     *
     * @param devId The Device ID
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    void removeDeviceFromBlacklist(String devId);

    /**
     * Retrieve all the devices in the blacklist of the DA. If there's no devices, an
     * empty list is returned.
     *
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    List<String> getBlacklist();

    /**
     * Set a list of devices in the blacklist all together, passing their device IDs as an
     * argument. Note that this insertion will persist, even through Device Adapter reboots,
     * until the devices are removed from the list. Every device adapter should check the format
     * of the address passed as an argument one by one and, if it does not support that kind of
     * address, it can safely ignore that address.
     *
     * @param devicesId The list containing the Device IDs
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    void setBlackList(in List<String> devicesId);

    /**
     * Return all the comand supported by the Device Adapter for its devices.
     *
     * @return A list of commands supported by the Device Adapter
     *
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     */
    List<String> getCommandList();

    /**
     * Execute a command supported by the device. You can also specify a parameter, if he command
     * requires or allows it.
     *
     * @param command The command to execute on the device
     * @param parameter The optional parameter to pass to the device together with the command
     * @param devId The Device ID
     *
     * @throws UnsupportedOperationException if the method is not supported and not implemented
     * by the DeviceAdapter
     * @throws IllegalArgumentException if the command is not supported by the Device Adapter
     */
    void execCommand(String command, String parameter, String devId);

}