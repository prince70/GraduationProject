package com.niwj.graduationproject.control;

import java.util.UUID;

/**
 * Created by prince70 on 2017/9/6.
 */

public class Constants {

    public static final String LOG_TAG = "Medical";

    public static final String DEVICE_STATUS_ONLINE = "OnLine";
    public static final String DEVICE_STATUS_OFFLINE = "OffLine";

    // 身份证阅读器uuid
    public static final UUID UUID_ID_CARD_READER = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // 0主蓝牙设备
    public static final int BLE_SEARCH_TYPE_MAIN = 0;
    // 1身份证读卡器
    public static final int BLE_SEARCH_TYPE_IDCARD = 1;

    // 刷新各个设备状态
    public static final int MESSAGE_UPDATE_FLAG = 1001;
    // 刷新体温数据
    public static final int MESSAGE_UPDATE_TIWEN = 1002;
    // 刷新血脂数据
    public static final int MESSAGE_UPDATE_XUEZHI = 1003;
    // 刷新尿检数据
    public static final int MESSAGE_UPDATE_NIAOJIAN = 1004;
    // 刷新血糖数据
    public static final int MESSAGE_UPDATE_XUETANG = 1005;
    // 刷新体成分数据
    public static final int MESSAGE_UPDATE_TICHENGFEN = 1006;
    //刷新心电波形
    public static final int MESSAGE_UPDATE_XINDIANBOXING = 1010;
    //刷新血氧波形
    public static final int MESSAGE_UPDATE_XUEYANGBOXING = 1011;
    //刷新血压数据
    public static final int MESSAGE_UPDATE_XUEYADATA = 1012;
    //刷新血氧数据
    public static final int MESSAGE_UPDATE_XUEYANGDATA = 1013;
    //刷新心电数据
    public static final int MESSAGE_UPDATE_XINDIANDATA = 1014;
    // 开始身份证阅读读卡
    public static final int MESSAGE_IDCARD_START_SCAN = 1007;
    // 显示身份证阅读器扫描结果
    public static final int MESSAGE_IDCARD_INFO = 1008;
    // 刷新各个体检设备的体检人
    public static final int MESSAGE_UPDATE_EXAM_PERSON = 1009;

    /**
     * Extras Constants
     */
    public static final String EXTRA_HRM_EEVALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_HRM_EEVALUE";
    public static final String EXTRA_HRM_RRVALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_HRM_RRVALUE";
    public static final String EXTRA_HRM_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_HRM_VALUE";
    public static final String EXTRA_BSL_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_BSL_VALUE";
    public static final String EXTRA_MNS_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_MNS_VALUE";
    public static final String EXTRA_MONS_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_MONS_VALUE";
    public static final String EXTRA_SNS_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_SNS_VALUE";
    public static final String EXTRA_HRS_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_HRS_VALUE";
    public static final String EXTRA_FRS_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_FRS_VALUE";
    public static final String EXTRA_SRS_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_SRS_VALUE";
    public static final String EXTRA_PNP_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_PNP_VALUE";
    public static final String EXTRA_SID_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_SID_VALUE";
    public static final String EXTRA_RCDL_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_RCDL_VALUE";
    public static final String EXTRA_HTM_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_HTM_VALUE";
    public static final String EXTRA_HSL_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_HSL_VALUE";
    public static final String EXTRA_BTL_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_BTL_VALUE";
    public static final String EXTRA_CAPPROX_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_CAPPROX_VALUE";
    public static final String EXTRA_CAPSLIDER_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_CAPSLIDER_VALUE";
    public static final String EXTRA_CAPBUTTONS_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_CAPBUTTONS_VALUE";
    public static final String EXTRA_ALERT_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_ALERT_VALUE";
    public static final String EXTRA_POWER_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_POWER_VALUE";
    public static final String EXTRA_RGB_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_RGB_VALUE";
    public static final String EXTRA_GLUCOSE_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_GLUCOSE_VALUE";
    public static final String EXTRA_BYTE_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_BYTE_VALUE";
    public static final String EXTRA_BYTE_UUID_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_BYTE_UUID_VALUE";
    public static final String EXTRA_PRESURE_SYSTOLIC_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_PRESURE_SYSTOLIC_VALUE";
    public static final String EXTRA_PRESURE_DIASTOLIC_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_PRESURE_DIASTOLIC_VALUE";
    public static final String EXTRA_PRESURE_SYSTOLIC_UNIT_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_PRESURE_SYSTOLIC_UNIT_VALUE";
    public static final String EXTRA_PRESURE_DIASTOLIC_UNIT_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_PRESURE_DIASTOLIC_UNIT_VALUE";
    public static final String EXTRA_RSC_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_RSC_VALUE";
    public static final String EXTRA_CSC_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_CSC_VALUE";
    public static final String EXTRA_ACCX_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_ACCX_VALUE";
    public static final String EXTRA_ACCY_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_ACCY_VALUE";
    public static final String EXTRA_ACCZ_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_ACCZ_VALUE";
    public static final String EXTRA_STEMP_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_STEMP_VALUE";
    public static final String EXTRA_SPRESSURE_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_SPRESSURE_VALUE";
    public static final String EXTRA_ACC_SENSOR_SCAN_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_ACC_SENSOR_SCAN_VALUE";
    public static final String EXTRA_ACC_SENSOR_TYPE_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_ACC_SENSOR_TYPE_VALUE";
    public static final String EXTRA_ACC_FILTER_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_ACC_FILTER_VALUE";
    public static final String EXTRA_STEMP_SENSOR_SCAN_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_STEMP_SENSOR_SCAN_VALUE";
    public static final String EXTRA_STEMP_SENSOR_TYPE_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_STEMP_SENSOR_TYPE_VALUE";
    public static final String EXTRA_SPRESSURE_SENSOR_SCAN_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_SPRESSURE_SENSOR_SCAN_VALUE";
    public static final String EXTRA_SPRESSURE_SENSOR_TYPE_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_SPRESSURE_SENSOR_TYPE_VALUE";
    public static final String EXTRA_SPRESSURE_THRESHOLD_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_SPRESSURE_THRESHOLD_VALUE";
    public static final String EXTRA_DESCRIPTOR_BYTE_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_DESCRIPTOR_BYTE_VALUE";
    public static final String EXTRA_DESCRIPTOR_BYTE_VALUE_UUID = "com.usr.ble.backgroundservices." +
            "EXTRA_DESCRIPTOR_BYTE_VALUE_UUID";
    public static final String EXTRA_DESCRIPTOR_BYTE_VALUE_CHARACTERISTIC_UUID = "com.usr.ble.backgroundservices." +
            "EXTRA_DESCRIPTOR_BYTE_VALUE_CHARACTERISTIC_UUID";
    public static final String EXTRA_DESCRIPTOR_VALUE = "com.usr.ble.backgroundservices." +
            "EXTRA_DESCRIPTOR_VALUE";
    public static final String EXTRA_DESCRIPTOR_REPORT_REFERENCE_ID = "com.usr.ble.backgroundservices." +
            "EXTRA_DESCRIPTOR_REPORT_REFERENCE_ID";
    public static final String EXTRA_DESCRIPTOR_REPORT_REFERENCE_TYPE = "com.usr.ble.backgroundservices." +
            "EXTRA_DESCRIPTOR_REPORT_REFERENCE_TYPE";
    public static final String EXTRA_CHARACTERISTIC_ERROR_MESSAGE = "com.usr.ble.backgroundservices." +
            "EXTRA_CHARACTERISTIC_ERROR_MESSAGE";

    /**
     * add by usr_ljq
     */
    public static final String EXTRA_DESCRIPTOR_WRITE_RESULT = "descriptor_write_result";

    /**
     * Links
     */
    public static final String LINK_CONTACT_US = "http://www.cypress.com/contactus/";
    public static final String LINK_BLE_PRODUCTS = "http://www.cypress.com/ble";
    public static final String LINK_CYPRESS_HOME = "http://www.cypress.com/";
    public static final String LINK_CYSMART_MOBILE = "http://www.cypress.com/cysmartmobile/";

    /**
     * Descriptor constants
     */
    public static final String firstBitValueKey = "FIRST BIT VALUE KEY";
    public static final String secondBitValueKey = "Second BIT VALUE KEY";
    public static final String EXTRA_SILICON_ID = "com.usr.ble.backgroundservices." +
            "EXTRA_SILICON_ID";
    public static final String EXTRA_SILICON_REV = "com.usr.ble.backgroundservices." +
            "EXTRA_SILICON_REV";
    public static final String EXTRA_START_ROW = "com.usr.ble.backgroundservices." +
            "EXTRA_START_ROW";
    public static final String EXTRA_END_ROW = "com.usr.ble.backgroundservices." +
            "EXTRA_END_ROW";
    public static final String EXTRA_SEND_DATA_ROW_STATUS = "com.usr.ble.backgroundservices." +
            "EXTRA_SEND_DATA_ROW_STATUS";
    public static final String EXTRA_PROGRAM_ROW_STATUS = "com.usr.ble.backgroundservices." +
            "EXTRA_PROGRAM_ROW_STATUS";
    public static final String EXTRA_VERIFY_ROW_STATUS = "com.usr.ble.backgroundservices." +
            "EXTRA_VERIFY_ROW_STATUS";
    public static final String EXTRA_VERIFY_ROW_CHECKSUM = "com.usr.ble.backgroundservices." +
            "EXTRA_VERIFY_ROW_CHECKSUM";
    public static final String EXTRA_VERIFY_CHECKSUM_STATUS = "com.usr.ble.backgroundservices." +
            "EXTRA_VERIFY_CHECKSUM_STATUS";
    public static final String EXTRA_VERIFY_EXIT_BOOTLOADER = "com.usr.ble.backgroundservices." +
            "EXTRA_VERIFY_EXIT_BOOTLOADER";
    public static final String EXTRA_ERROR_OTA = "com.usr.ble.backgroundservices." +
            "EXTRA_ERROR_OTA";
    /**
     * Shared Prefernce Status HandShake State
     */
    public static final String PREF_BOOTLOADER_STATE = "PREF_BOOTLOADER_STATE";
    public static final String PREF_PROGRAM_ROW_NO = "PREF_PROGRAM_ROW_NO";
    public static final String PREF_PROGRAM_ROW_START_POS = "PREF_PROGRAM_ROW_START_POS";
    /**
     * OTA File Selection Extras
     */
    public static final String REQ_FILE_COUNT = "REQ_FILE_COUNT";
    public static final String SELECTION_FLAG = "SELECTION_FLAG";
    public static final String ARRAYLIST_SELECTED_FILE_PATHS = "ARRAYLIST_SELECTED_FILE_PATHS";
    public static final String ARRAYLIST_SELECTED_FILE_NAMES = "ARRAYLIST_SELECTED_FILE_NAMES";
    public static final String REQ_FILE_COUNT_STATE = "REQ_FILE_COUNT_STATE";
    public static final String OTA_OPTION = "OTA_OPTION";
    public static final String OTA_OPTION_SELCETED = "OTA_OPTION_SELCETED";
    /**
     * Shared Prefernce Status File State
     */
    public static final String PREF_OTA_FILE_ONE_PATH = "PREF_OTA_FILE_ONE_PATH";
    public static final String PREF_OTA_FILE_ONE_NAME = "PREF_OTA_FILE_ONE_NAME";
    public static final String PREF_OTA_FILE_TWO_PATH = "PREF_OTA_FILE_TWO_PATH";
    public static final String PREF_OTA_FILE_TWO_NAME = "PREF_OTA_FILE_TWO_NAME";
    public static final String PREF_OTA_FILE_COMPLETED = "PREF_OTA_FILE_COMPLETED";
    public static final String PREF_OTA_FILE_COUNT = "PREF_OTA_FILE_COUNT";
    public static final String PREF_DEV_ADDRESS = "PREF_DEV_ADDRESS";
    /**
     * Shared preference of the google developer api key
     */
    public static final String PREF_GOOGLE_API_KEY = "PREF_GOOGLE_API_KEY";
    public static final int TEXT_SIZE_XHDPI = 24;
    public static final int TEXT_SIZE_XXHDPI = 30;
    public static final int TEXT_SIZE_HDPI = 20;
    public static final int TEXT_SIZE_LDPI = 13;
    /**
     * Magic numbers
     */
    public static final int FIRST_BITMASK = 0x01;
    /**
     * OTA and RDK Disable flags
     */
    public static final boolean OTA_ENABLED = true;
    public static final boolean RDK_ENABLED = true;
    public static final boolean GMS_ENABLED = false;
    /**
     * Fragment Tags
     */
    public static String PROFILE_SCANNING_FRAGMENT_TAG = "BLE Devices";
    public static String ABOUT_FRAGMENT_TAG = "about cysmart";
    public static String BLE_PRODUCTS_FRAGMENT_TAG = "ble products";
    public static String DATALOGER_HISTORY = "Data Logger";
    public static String PROFILE_CONTROL_FRAGMENT_TAG = "Services";
    public static String GATTDB_SELECTED_SERVICE = "gatt db service";
    public static String GATTDB_SELECTED_CHARACTERISTICE = "selected characterisitics";
    /**
     * DataLogger constants
     */
    public static String DATA_LOGGER_FILE_NAAME = "file name";
    public static String DATA_LOGGER_FLAG = "Data Logger Flag";
}
