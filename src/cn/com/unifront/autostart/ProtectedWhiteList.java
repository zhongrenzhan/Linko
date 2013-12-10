
package cn.com.unifront.autostart;

public class ProtectedWhiteList {
    /*
     * label                packageName
     * Android 系统          android
       UnifSystemApp        cn.com.unifront.systemapp
       系统更新              cn.com.unifront.upgrade
       装配                 cn.gosomo.evt
       蓝牙共享              com.android.bluetooth
       小区广播              com.android.cellbroadcastreceiver
       联系人                com.android.contacts
       时钟                 com.android.deskclock
       电子邮件              com.android.email
       Exchange 服务        com.android.exchange
       图库                 com.android.gallery3d
       Input Devices       com.android.inputdevices
       Key Chain           com.android.keychain
       信息                 com.android.mms
       手机                 com.android.phone
       搜索应用提供商         com.android.providers.applications
       日历存储              com.android.providers.calendar
       联系人存储            com.android.providers.contacts
       下载管理器            com.android.providers.downloads
       下载内容              com.android.providers.downloads.ui
       受 DRM 保护的内容的存储   com.android.providers.drm
       媒体存储              com.android.providers.media
       设置存储              com.android.providers.settings
       电话/信息存储          com.android.providers.telephony
       User Dictionary      com.android.providers.userdictionary
       设备信息              com.android.qrd.engineeringmode
       设置                 com.android.settings
       UIM 卡应用            com.android.stk
       系统用户界面           com.android.systemui
       SIM卡联系人           com.qrd.simcontacts
       国际漫游设置           com.qualcomm.CTroamingsettings
       小区广播              com.qualcomm.CellBCSetting
       Content Adaptive Backlight Settings      com.qualcomm.cabl
       FastBoot             com.qualcomm.fastboot
       NoSIMAlert           com.qualcomm.nosimalert
       com.qualcomm.privinit        com.qualcomm.privinit
       com.qualcomm.recorder        com.qualcomm.recorder
       RestoreAirplaneMode          com.qualcomm.restore.airplanemode
       存储卡管理                     com.qualcomm.storage
       Wiper App                    com.qualcomm.wiper
       FM 收音机                     com.quicinc.fmradio
       天地通                        com.yaloe8077
       LED手电筒                     qualcomm.android.LEDFlashlight
     */
    static final String[] APP_WHITE_LIST = {
            "android", "com.android.phone", "com.android.mms", "com.android.systemui",
            "com.android.settings", "com.android.calendar", "com.android.contacts",
            "com.android.providers.calendar", "com.android.providers.contacts",
            "com.android.providers.downloads", "com.android.providers.downloads.ui",
            "com.android.providers.media","com.qualcomm.wiper","com.qualcomm.storage",
            "com.qualcomm.restore.airplanemode",""
    };
    public static final String[] MAIN_CONCERN_ACTINS = {
            "android.intent.action.BOOT_COMPLETED", "android.intent.action.SIG_STR",
            "android.intent.action.USER_PRESENT", "android.intent.action.UMS_CONNECTED",
            "android.intent.action.MEDIA_EJECT", "android.intent.action.PHONE_STATE",
            "android.intent.action.NEW_OUTGOING_CALL", "android.provider.Telephony.SMS_RECEIVED",
            "android.provider.Telephony.WAP_PUSH_RECEIVED", "android.net.thrott.POLL_ACTION",
            "android.hardware.usb.action.USB_STATE",
            "com.android.mms.transaction.MessageStatusReceiver.MESSAGE_STATUS_RECEIVED",
            "android.intent.action.MEDIA_MOUNTED", "android.intent.action.MEDIA_UNMOUNTED",
            "android.intent.action.MEDIA_REMOVED",
            "android.intent.action.ACTION_MEDIA_BAD_REMOVAL", "android.intent.action.MEDIA_SHARED",
            "yi.intent.action.UBC_SUBMITDATA", "com.android.statusbar.ACTION_TRANSPARENT",
            "intent.action.HOME_ACTIVITY_CHANGED"
    };
    public static final String[] MAIN_PROTECTED_SYSTEM_ACTIONS = {
            "android.intent.action.TIMEZONE_CHANGED", "android.intent.action.PACKAGE_INSTALL",
            "android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_REPLACED",
            "android.intent.action.MY_PACKAGE_REPLACED", "android.intent.action.PACKAGE_REMOVED",
            "android.intent.action.PACKAGE_FULLY_REMOVED", "android.intent.action.PACKAGE_CHANGED",
            "android.intent.action.PACKAGE_RESTARTED",
            "android.intent.action.PACKAGE_DATA_CLEARED",
            "android.intent.action.PACKAGE_FIRST_LAUNCH",
            "android.intent.action.PACKAGE_NEEDS_VERIFICATION",
            "android.intent.action.UID_REMOVED", "android.intent.action.LOCALE_CHANGED",
            "android.intent.action.BATTERY_LOW", "android.intent.action.BATTERY_OKAY",
            "android.intent.action.ACTION_POWER_CONNECTED",
            "android.intent.action.ACTION_POWER_DISCONNECTED",
            "android.intent.action.ACTION_SHUTDOWN", "android.intent.action.DEVICE_STORAGE_LOW",
            "android.intent.action.DEVICE_STORAGE_OK", "android.intent.action.DEVICE_STORAGE_FULL",
            "android.intent.action.DEVICE_STORAGE_NOT_FULL", "android.intent.action.DOCK_EVENT",
            "android.intent.action.MASTER_CLEAR_NOTIFICATION", "android.app.action.ENTER_CAR_MODE",
            "android.app.action.EXIT_CAR_MODE", "android.app.action.ENTER_DESK_MODE",
            "android.app.action.EXIT_DESK_MODE", "android.backup.intent.RUN",
            "android.backup.intent.CLEAR", "android.backup.intent.INIT",
            "android.bluetooth.adapter.action.STATE_CHANGED",
            "android.bluetooth.adapter.action.SCAN_MODE_CHANGED",
            "android.bluetooth.adapter.action.DISCOVERY_STARTED",
            "android.bluetooth.adapter.action.DISCOVERY_FINISHED",
            "android.bluetooth.adapter.action.LOCAL_NAME_CHANGED",
            "android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.device.action.FOUND", "android.bluetooth.device.action.DISAPPEARED",
            "android.bluetooth.device.action.CLASS_CHANGED",
            "android.bluetooth.device.action.ACL_CONNECTED",
            "android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED",
            "android.bluetooth.device.action.ACL_DISCONNECTED",
            "android.bluetooth.device.action.NAME_CHANGED",
            "android.bluetooth.device.action.BOND_STATE_CHANGED",
            "android.bluetooth.device.action.NAME_FAILED",
            "android.bluetooth.device.action.PAIRING_REQUEST",
            "android.bluetooth.device.action.PAIRING_CANCEL",
            "android.bluetooth.device.action.CONNECTION_ACCESS_REPLY",
            "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED",
            "android.bluetooth.headset.action.VENDOR_SPECIFIC_HEADSET_EVENT",
            "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED",
            "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED",
            "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED",
            "android.hardware.usb.action.USB_ACCESSORY_ATTACHED",
            "android.hardware.usb.action.USB_ACCESSORY_ATTACHED",
            "android.hardware.usb.action.USB_DEVICE_ATTACHED",
            "android.hardware.usb.action.USB_DEVICE_DETACHED",
            "android.net.conn.CONNECTIVITY_CHANGE",
            "android.net.conn.CONNECTIVITY_CHANGE_IMMEDIATE",
            "android.nfc.action.LLCP_LINK_STATE_CHANGED",
            "com.android.nfc_extras.action.RF_FIELD_ON_DETECTED",
            "com.android.nfc_extras.action.RF_FIELD_OFF_DETECTED",
            "com.android.nfc_extras.action.AID_SELECTED",
            "android.nfc.action.TRANSACTION_DETECTED", "android.intent.action.CLEAR_DNS_CACHE",
            "android.intent.action.PROXY_CHANGE", "android.provider.Telephony.SPN_STRINGS_UPDATED",
            "android.intent.action.SERVICE_STATE", "android.intent.action.DATA_CONNECTION_FAILED",
            "android.intent.action.RADIO_TECHNOLOGY",
            "android.intent.action.ACTION_SHOW_NOTICE_ECM_BLOCK_OTHERS",
            "android.intent.action.NETWORK_SET_TIME", "android.intent.action.NETWORK_SET_TIMEZONE",
            "android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED",
            "android.intent.action.ACTION_MDN_STATE_CHANGED",
            "android.intent.action.SIM_STATE_CHANGED", "android.intent.action.ANY_DATA_STATE"
    };
}
