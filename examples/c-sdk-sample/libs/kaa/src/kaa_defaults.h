/*
 * Copyright 2014-2015 CyberVision, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

# ifndef KAA_DEFAULTS_H_
# define KAA_DEFAULTS_H_

/**
 *
 * DO NOT EDIT THIS FILE. IT IS AUTO-GENERATED.
 *
 */

# include <stdint.h>
# include "kaa_common.h"

# ifdef __cplusplus
extern "C" {
# endif

# define BUILD_VERSION                   "0.7.0-SNAPSHOT"
# define BUILD_COMMIT_HASH               ""

# define APPLICATION_TOKEN               "48754853396563159149"

# define CONFIG_SCHEMA_VERSION           2
# define PROFILE_SCHEMA_VERSION          2
# define SYSTEM_NF_SCHEMA_VERSION        1
# define USER_NF_SCHEMA_VERSION          1
# define LOG_SCHEMA_VERSION              2

# define KAA_SYNC_TIMEOUT                60000L



typedef struct {
    const char *    name;
    uint16_t         version;
} kaa_events_schema_version_t;

# define KAA_EVENT_SCHEMA_VERSIONS_SIZE    1

static const kaa_events_schema_version_t KAA_EVENT_SCHEMA_VERSIONS[KAA_EVENT_SCHEMA_VERSIONS_SIZE] = {
    {
          /* .name = */   "Device Event Class Family"
        , /* .version = */1
    }};



/**
 * @brief Uses to represent transport-specific connection data to establish
 * connection to Bootstrap servers.
 *
 * @see kaa_transport_protocol_id_t
 * @see kaa_access_point_t
 */
typedef struct {
    kaa_transport_protocol_id_t    protocol_id;
    kaa_access_point_t             access_point;
} kaa_bootstrap_server_connection_data_t;

# define KAA_BOOTSTRAP_ACCESS_POINT_COUNT    2

static const kaa_bootstrap_server_connection_data_t KAA_BOOTSTRAP_ACCESS_POINTS[KAA_BOOTSTRAP_ACCESS_POINT_COUNT] =
{
    {
        { 0xfb9a3cf0, 1 },
        {
            0x95f7e40f,
            316,
            (char []) {
            0 , 0 , 1 , 38 , 48 , -126 , 1 , 34 , 48 , 13 , 6 , 9 , 42 , -122 , 72 , -122 , -9 , 13 , 1 , 1 , 1 , 5 , 0 , 3 , -126 , 1 , 15 , 0 , 48 , -126 , 1 , 10 , 2 , -126 , 1 , 1 , 0 , -109 , -30 , 108 , -119 , -12 , 3 , -91 , -40 , -128 , -97 , -35 , 73 , 16 , -32 , 24 , -15 , -125 , 46 , 48 , -71 , -102 , 81 , -76 , 60 , 126 , 77 , 100 , 98 , 88 , 99 , 30 , -15 , 65 , 74 , 66 , 59 , -120 , -112 , -55 , -63 , -16 , 124 , 102 , 55 , 66 , -71 , 92 , 12 , 83 , 11 , 111 , -46 , -30 , 116 , 12 , 127 , 58 , 35 , -75 , 43 , 64 , -106 , 119 , 65 , 27 , -5 , -73 , 28 , 49 , -25 , 27 , 79 , -22 , -114 , -14 , 90 , -106 , 91 , -4 , -38 , 2 , 34 , -64 , -101 , -108 , -24 , -121 , -17 , -14 , -124 , -113 , 40 , 95 , 112 , 7 , -47 , -61 , -92 , 121 , -115 , -11 , 65 , -27 , -91 , 89 , 104 , 8 , 43 , -20 , -53 , -101 , -73 , 43 , 71 , 28 , 26 , 15 , 66 , -75 , 123 , -19 , -123 , -120 , -18 , -31 , 76 , -9 , -112 , -30 , -96 , 85 , 20 , -113 , -125 , 98 , 81 , 96 , -56 , -16 , 82 , 127 , 59 , 7 , 109 , -103 , -104 , -41 , 106 , -10 , -66 , -48 , 105 , 31 , 73 , -96 , 15 , 42 , 60 , -42 , 121 , 30 , 107 , 49 , -77 , -92 , -127 , 4 , -70 , -68 , -46 , 61 , -84 , -55 , -33 , 63 , -55 , -51 , 1 , -6 , 24 , 78 , 24 , -36 , 54 , 26 , 54 , -29 , 54 , 69 , 63 , 119 , 94 , -74 , 86 , -105 , -119 , -91 , 85 , -56 , 115 , -99 , -15 , 58 , -30 , -111 , 51 , 103 , 2 , -62 , -39 , -43 , 10 , 75 , 82 , -3 , 102 , 30 , 86 , -101 , -116 , 43 , 87 , 84 , -80 , 110 , -96 , -9 , 96 , 69 , 67 , 44 , -63 , 46 , -48 , -111 , 25 , 69 , -30 , 80 , 120 , 33 , 54 , 74 , -115 , -10 , -68 , 126 , 9 , 70 , 37 , -56 , -30 , 26 , -50 , -16 , -121 , 2 , 3 , 1 , 0 , 1 , 0 , 0 , 0 , 10 , 49 , 48 , 46 , 50 , 46 , 49 , 46 , 49 , 57 , 48 , 0 , 0 , 38 , -95             }
        }
    },
    {
        { 0x56c8ff92, 1 },
        {
            0x95f7e40f,
            316,
            (char []) {
            0 , 0 , 1 , 38 , 48 , -126 , 1 , 34 , 48 , 13 , 6 , 9 , 42 , -122 , 72 , -122 , -9 , 13 , 1 , 1 , 1 , 5 , 0 , 3 , -126 , 1 , 15 , 0 , 48 , -126 , 1 , 10 , 2 , -126 , 1 , 1 , 0 , -109 , -30 , 108 , -119 , -12 , 3 , -91 , -40 , -128 , -97 , -35 , 73 , 16 , -32 , 24 , -15 , -125 , 46 , 48 , -71 , -102 , 81 , -76 , 60 , 126 , 77 , 100 , 98 , 88 , 99 , 30 , -15 , 65 , 74 , 66 , 59 , -120 , -112 , -55 , -63 , -16 , 124 , 102 , 55 , 66 , -71 , 92 , 12 , 83 , 11 , 111 , -46 , -30 , 116 , 12 , 127 , 58 , 35 , -75 , 43 , 64 , -106 , 119 , 65 , 27 , -5 , -73 , 28 , 49 , -25 , 27 , 79 , -22 , -114 , -14 , 90 , -106 , 91 , -4 , -38 , 2 , 34 , -64 , -101 , -108 , -24 , -121 , -17 , -14 , -124 , -113 , 40 , 95 , 112 , 7 , -47 , -61 , -92 , 121 , -115 , -11 , 65 , -27 , -91 , 89 , 104 , 8 , 43 , -20 , -53 , -101 , -73 , 43 , 71 , 28 , 26 , 15 , 66 , -75 , 123 , -19 , -123 , -120 , -18 , -31 , 76 , -9 , -112 , -30 , -96 , 85 , 20 , -113 , -125 , 98 , 81 , 96 , -56 , -16 , 82 , 127 , 59 , 7 , 109 , -103 , -104 , -41 , 106 , -10 , -66 , -48 , 105 , 31 , 73 , -96 , 15 , 42 , 60 , -42 , 121 , 30 , 107 , 49 , -77 , -92 , -127 , 4 , -70 , -68 , -46 , 61 , -84 , -55 , -33 , 63 , -55 , -51 , 1 , -6 , 24 , 78 , 24 , -36 , 54 , 26 , 54 , -29 , 54 , 69 , 63 , 119 , 94 , -74 , 86 , -105 , -119 , -91 , 85 , -56 , 115 , -99 , -15 , 58 , -30 , -111 , 51 , 103 , 2 , -62 , -39 , -43 , 10 , 75 , 82 , -3 , 102 , 30 , 86 , -101 , -116 , 43 , 87 , 84 , -80 , 110 , -96 , -9 , 96 , 69 , 67 , 44 , -63 , 46 , -48 , -111 , 25 , 69 , -30 , 80 , 120 , 33 , 54 , 74 , -115 , -10 , -68 , 126 , 9 , 70 , 37 , -56 , -30 , 26 , -50 , -16 , -121 , 2 , 3 , 1 , 0 , 1 , 0 , 0 , 0 , 10 , 49 , 48 , 46 , 50 , 46 , 49 , 46 , 49 , 57 , 48 , 0 , 0 , 38 , -96             }
        }
    }};



# define KAA_CONFIGURATION_DATA_LENGTH    18

static const char KAA_CONFIGURATION_DATA[KAA_CONFIGURATION_DATA_LENGTH] =
{
0 , 0 , -78 , -87 , 125 , -11 , -15 , 94 , 67 , -10 , -122 , -87 , -72 , -51 , 127 , -28 , 23 , -97 };

# ifdef __cplusplus
} // extern "C"
# endif

# endif /* KAA_DEFAULTS_H_ */

