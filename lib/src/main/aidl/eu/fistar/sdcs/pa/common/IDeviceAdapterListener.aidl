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

import eu.fistar.sdcs.pa.common.DeviceDescription;
import eu.fistar.sdcs.pa.common.Observation;

/**
 * Interface implemented by classes that implements a Protocol Adapter.<br>
 * This interface is used to define some methods used by Device Adapters to communicate with the PA.
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
interface IDeviceAdapterListener {

    /**
     * Register a new device with the protocol adapter
     *
     * @param dev_desc
     *      The device to register
     */
    void registerDevice(in DeviceDescription devDesc);

    /**
     * Push new measurements data coming from the device
     *
     * @param observations
     *      The data to push
     *
     * @param dev_desc
     *      The device who supplies the data
     */
    void pushData(in List<Observation> observations, in DeviceDescription devDesc);


    /**
     * Deregister a device with the protocol adapter when it is not available anymore
     *
     * @param dev_desc
     *      The device to deregister
     */
    void deregisterDevice(in DeviceDescription devDesc);

    /**
     * Register a new property for a device
     * Not used at the moment, since all the job is done with register_Device
     *
     * @param dev_desc
     *      The device that has the property to register
     */
    void registerDeviceProperties(in DeviceDescription devDesc);

    /**
     * Called by Device Adapters when a device disconnects
     *
     * @param dev_desc
     *      The device that has just disconnected
     */
    void deviceDisconnected(in DeviceDescription devDesc);

    /**
     * Called by Device Adapter to generate a log message in order to notify Protocol Adapter of
     * some behaviour.
     *
     * @param logLevel The log level of the message
     * @param daId The ID of the Device Adapter generating the message
     * @param message The content of the message
     */
    void log(in int logLevel, in String daId, in String message);

}
