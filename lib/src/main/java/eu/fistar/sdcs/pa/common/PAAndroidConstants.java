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

/**
 * This class contains some constants useful for the Protocol Adapter
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
public class PAAndroidConstants {

    // The version of the library
    public static final String PA_LIB_VERSION = "3.3.1";

    // Intent related constants
    public static final String PA_PACKAGE = "eu.fistar.sdcs.pa";
    public static final String PA_ACTION = "eu.fistar.sdcs.pa.PAManagerService";

    // Log related constants
    public static final String PA_LOGTAG = "PA >>>";
    public static final String DA_LOGTAG = "DA >>>";
    public static final String ERRORTAG = "ERROR >>>";
    public static final String WARNINGTAG = "Warning >>>";

    public static class SDCS {
        public static final String ACTION = "eu.fistar.sdcs";
        public static final String PACKAGE = "eu.fistar.sdcs";
    }
    
    public static class SDCS_MESSAGES {
        public static final short MSG_TYPE_DEV_REGISTRATION = 7;
        public static final short MSG_TYPE_DEV_PROPERTIES_REGISTRATION = 8;
        public static final short MSG_TYPE_DATA_PUSH = 11;
        public static final short MSG_TYPE_DEV_DEREGISTRATION = 14;

        public static final String MSG_CODE_OK_PREFIX = "20";

        public static final String EXTRA_NAME_ISSUER = "Issuer";
        public static final String EXTRA_NAME_REQID = "requestId";
        public static final String EXTRA_NAME_STATUS = "status";
        public static final String EXTRA_NAME_CONTENT = "content";
        public static final String EXTRA_NAME_CONTENT_TYPE = "content-type";
        public static final String EXTRA_NAME_METHOD = "method";
        public static final String EXTRA_NAME_PATH = "path";
        public static final String EXTRA_NAME_REPLY_ACTION = "replyAction";

        public static final String JSON_NAME_CONTAINER = "container";
        public static final String JSON_NAME_ID = "id";
        public static final String JSON_NAME_SEARCH_STRING = "searchString";
        public static final String JSON_NAME_SEARCH_STRINGS = "searchStrings";
        public static final String JSON_NAME_APP_ID = "appId";
        public static final String JSON_NAME_APPLICATION = "application";
    }

    public static class DA_DISCOVERY {
        public static final String REQUEST_ACTION = "eu.fistar.sdcs.pa.discoveryrequest";

        public static final String REPLY_ACTION = "eu.fistar.sdcs.pa.discoveryreply";
        public static final String BUNDLE_REPACT = "replyAction";

        public static final String BUNDLE_DAID = "daId";
        public static final String BUNDLE_DACAP = "daCapabilities";

        public static final long TIMEOUT = 600;
    }

    public static class LOG_LEVEL {
        public static final int VERBOSE = 0;
        public static final int INFO = 1;
        public static final int WARNING = 2;
        public static final int ERROR = 3;
    }

}
