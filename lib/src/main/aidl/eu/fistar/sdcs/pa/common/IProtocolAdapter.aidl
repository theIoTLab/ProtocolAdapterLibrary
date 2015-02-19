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

import android.os.IBinder;
import java.util.List;
import java.util.Map;
import eu.fistar.sdcs.pa.common.DeviceDescription;
import eu.fistar.sdcs.pa.common.Capabilities;

/**
 * Interface implemented by classes that implements a Protocol Adapter.<br>
 * This interface is used to define some methods used by Applications to communicate with the PA.
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
interface IProtocolAdapter {

    /**
     * Returns a list of all the devices connected at the moment with the Device Adapter.
     * 
     * @return A List containing the DeviceDescription of all the connected devices
     */
    List<DeviceDescription> getConnectedDevices();
    
    /**
     * Returns a map containing the Device ID of all the devices paired with the smartphone that can
     * be handled by at least one DA as the key, and a list of DA IDs of DA that can handle that
     * device as the value.
     * 
     * @return A map containing the Device ID of all the paired devices as the key, and a list of DA
     * IDs that can handle that device as the value (Map<String, List<String>>).
     */
    Map getDADevices();
    
    /**
     * Returns a list of devices that can be detected with a scanning.
     *
     * @return A list containing the Device ID of all the discovered devices
     */
    List<String> detectDevices();
    
    /**
     * Set the specific configuration of a device managed by the Device Adapter passing a data 
     * structure with key-value pairs containing all possible configuration parameters and 
     * their values, together with the device ID. This should be done before starting the Device 
     * Adapter, otherwise standard configuration will be used. Depending on capabilities, this 
     * could also be invoked when the DA is already running.
     *
     * @param config The configuration for the device in the form of a key/value set (String/String)
     * @param devID The device ID (the MAC Address)
     */
    void setDeviceConfig(in Map config, String devId);
    
    /**
     * Start the Device Adapter operations. This will cause the PA to bind the DA's service and start the DA.
     * Since the binding of the DAs is an asynchronous process, once it's completed, the {@link IProtocolAdapterListener}'s
     * onDAConnected will be called to notify the event to the application.
     *
     * @param daId The Device Adapter ID
     */
    void startDA(String daId);
    
    /**
     * Stop the Device Adapter operations. This will cause the PA to stop the DA and unbind the related service.
     *
     * @param daId The Device Adapter ID
     */
    void stopDA(String daId);
    
    /**
     * Return a Map with all the available DAs in the system. The keys of the Map are the DAs' ID and the values 
     * are the related Capabilities object.
     *
     * @return A Map with DA identifiers as key and Capabilities object as value (String/Capabilities)
     */
    Map getAvailableDAs();
    
    /**
     * Return the object describing the capabilities of the specified DA.
     *
     * @param daId ID of the DA
     * @return An instance of the Capabilities object containing all the capabilities of the device
     */
    Capabilities getDACapabilities(String daId);
    
    /**
     * Connect to the device whose MAC Address is passed as an argument.
     * 
     * @param devId The Device ID
     */
    void connectDev(String devId);

    /**
     * Force connection to the device whose devID is passed as an argument using the specified
     * Device Adapter.
     *
     * @param devId The Device ID
     * @param daId The ID of the Device Adapter
     */
    void forceConnectDev(String devId, String daId);
    
    /**
     * Disconnect from the device whose MAC Address is passed as an argument.
     * 
     * @param devId The Device ID
     */
    void disconnectDev(String devId);
    
    /**
     * Receive a binder from the Application representing its interface.
     *
     * @param application The IBinder of the application
     */
    void registerPAListener(IBinder application);
    
    /**
     * Add a device to the Device Adapter whitelist, passing its device ID as an argument. 
     * Please note that this insertion will persist, even through Device Adapter reboots, until
     * the device it's removed from the list. Every device adapter should check the format
     * of the address passed as an argument and, if it does not support that kind of 
     * address, it can safely ignore that address.
     *
     * @param devId The Device ID
     */
    void addDeviceToWhitelist(String devId);
    
    /**
     * Remove from the whitelist the device whose device ID is passed as an argument.
     * If the device is not in the list, the request can be ignored.
     * 
     * @param devId The Device ID
     */
    void removeDeviceFromWhitelist(String devId);
    
    /**
     * Retrieve all the devices in the whitelist of the DA. If there's no devices, an 
     * empty list is returned.
     *
     * @return A list containing all the device id in the white list
     */
    List<String> getWhitelist();
    
    /**
     * Set a list of devices in the whitelist all together, passing their device IDs as an argument. 
     * Please note that this insertion will persist, even through Device Adapter reboots, until
     * the devices are removed from the list. Every device adapter should check the format
     * of the address passed as an argument one by one and, if it does not support that kind of 
     * address, it can safely ignore that address.
     *
     * @param whiteList A list containing all the device id to insert in the white list
     */
    void setWhitelist(in List<String> whiteList);
    
    /**
     * Add a device to the Device Adapter blacklist, passing its device ID as an argument. 
     * Please note that this insertion will persist, even through Device Adapter reboots, until
     * the device it's removed from the list. Every device adapter should check the format
     * of the address passed as an argument and, if it does not support that kind of 
     * address, it can safely ignore that address.
     *
     * @param devId The Device ID
     */
    void addDeviceToBlackList(String devId);
    
    /**
     * Remove from the blacklist the device whose device ID is passed as an argument.
     * If the device is not in the list, the request can be ignored.
     * 
     * @param devId The Device ID
     */
    void removeDeviceFromBlacklist(String devId);
    
    /**
     * Retrieve all the devices in the blacklist of the DA. If there's no devices, an 
     * empty list is returned.
     * 
     * @return A list containing all the device id in the black list
     */
    List<String> getBlacklist();
    
    /**
     * Set a list of devices in the blacklist all together, passing their device IDs as an argument. 
     * Please note that this insertion will persist, even through Device Adapter reboots, until
     * the devices are removed from the list. Every device adapter should check the format
     * of the address passed as an argument one by one and, if it does not support that kind of 
     * address, it can safely ignore that address.
     * 
     * @param blackList A list containing all the device id to insert in the black list
     */
    void setBlackList(in List<String> blackList);
    
    /**
     * Return all the commands supported by the Device Adapter for its devices.
     *
     * @param daId ID of the DA
     * @return A list of commands supported by the Device Adapter
     */
    List<String> getCommandList(String daId);

    /**
     * Execute a command supported by the device. You can also specify a parameter, if the command
     * allows or requires it.
     * 
     * @param command The command to execute on the device
     * @param parameter The optional parameter to pass to the device together with the command
     * @param devId The Device ID
     * 
     * @throws IllegalArgumentException if the command is not supported by the Device Adapter
     */
    void execCommand(String command, String parameter, String devId);
    
}

