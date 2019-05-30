package com.ceshi.ha.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import static android.content.Context.TELEPHONY_SERVICE;


/**
 * Created by Administrator on 2016/7/12.
 */
public class PhoneMsgUtils {

    public static void getTelePhone(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
  /*
   * 电话状态：
   * 1.tm.CALL_STATE_IDLE=0          无活动
   * 2.tm.CALL_STATE_RINGING=1  响铃
   * 3.tm.CALL_STATE_OFFHOOK=2  摘机
   */
        tm.getCallState();//int
        Log.d("电话状态：", "" + tm.getCallState());

  /*
   * 电话方位：
   *
   */
        tm.getCellLocation();//CellLocation
        Log.d("电话方位：", "" + tm.getCellLocation());

  /*
   * 唯一的设备ID：
   * GSM手机的 IMEI 和 CDMA手机的 MEID.
   * Return null if device ID is not available.
   */
        tm.getDeviceId();//String
        Log.d("唯一的设备ID(IMEI)：", "" + tm.getDeviceId());

  /*
   * 设备的软件版本号：
   * 例如：the IMEI/SV(software version) for GSM phones.
   * Return null if the software version is not available.
   */
        tm.getDeviceSoftwareVersion();//String
        Log.d("设备的软件版本号：", "" + tm.getDeviceSoftwareVersion());

  /*
   * 手机号：
   * GSM手机的 MSISDN.
   * Return null if it is unavailable.
   */
        tm.getLine1Number();//String
        Log.d("手机号：", "" + tm.getLine1Number());

  /*
   * 附近的电话的信息:
   * 类型：List<NeighboringCellInfo>
   * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES
   */
//        tm.getNeighboringCellInfo();//List<NeighboringCellInfo>
//        Log.d("附近的电话的信息:：", "" + tm.getNeighboringCellInfo());

  /*
   * 获取ISO标准的国家码，即国际长途区号。
   * 注意：仅当用户已在网络注册后有效。
   *       在CDMA网络中结果也许不可靠。
   */
        tm.getNetworkCountryIso();//String
        Log.d("国际长途区号:：", "" + tm.getNetworkCountryIso());

  /*
   * MCC+MNC(mobile country code + mobile network code)
   * 注意：仅当用户已在网络注册时有效。
   *    在CDMA网络中结果也许不可靠。
   */
        tm.getNetworkOperator();//String
        Log.d("国家代码和网络代码", "" + tm.getNetworkOperator());

  /*
   * 按照字母次序的current registered operator(当前已注册的用户)的名字
   * 注意：仅当用户已在网络注册时有效。
   *    在CDMA网络中结果也许不可靠。
   */
        tm.getNetworkOperatorName();//String
        Log.d("用户名字", "" + tm.getNetworkOperatorName());

  /*
   * 当前使用的网络类型：
   * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0
     NETWORK_TYPE_GPRS     GPRS网络  1
     NETWORK_TYPE_EDGE     EDGE网络  2
     NETWORK_TYPE_UMTS     UMTS网络  3
     NETWORK_TYPE_HSDPA    HSDPA网络  8
     NETWORK_TYPE_HSUPA    HSUPA网络  9
     NETWORK_TYPE_HSPA     HSPA网络  10
     NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
     NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
     NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
     NETWORK_TYPE_1xRTT    1xRTT网络  7
   */
        tm.getNetworkType();//int
        Log.d("当前使用的网络类型", "" + tm.getNetworkType());

  /*
   * 手机类型：
   * 例如： PHONE_TYPE_NONE  无信号
     PHONE_TYPE_GSM   GSM信号
     PHONE_TYPE_CDMA  CDMA信号
   */
        tm.getPhoneType();//int
        Log.d("手机类型", "" + tm.getPhoneType());

  /*
   * Returns the ISO country code equivalent for the SIM provider's country code.
   * 获取ISO国家码，相当于提供SIM卡的国家码。
   *
   */
        tm.getSimCountryIso();//String
        Log.d("ISO国家码", "" + tm.getSimCountryIso());

  /*
   * Returns the MCC+MNC (mobile country code + mobile network code) of the provider of the SIM. 5 or 6 decimal digits.
   * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字.
   * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
   */
        tm.getSimOperator();//String
        Log.d("获取SIM卡提供的移动国家码和移动网络码", "" + tm.getSimOperator());

  /*
   * 服务商名称：
   * 例如：中国移动、联通
   * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
   */
        tm.getSimOperatorName();//String
        Log.d("服务商名称", "" + tm.getSimOperatorName());

  /*
   * SIM卡的序列号：
   * 需要权限：READ_PHONE_STATE
   */
        tm.getSimSerialNumber();//String
        Log.d("SIM卡的序列号", "" + tm.getSimSerialNumber());

  /*
   * SIM的状态信息：
   *  SIM_STATE_UNKNOWN          未知状态 0
   SIM_STATE_ABSENT           没插卡 1
   SIM_STATE_PIN_REQUIRED     锁定状态，需要用户的PIN码解锁 2
   SIM_STATE_PUK_REQUIRED     锁定状态，需要用户的PUK码解锁 3
   SIM_STATE_NETWORK_LOCKED   锁定状态，需要网络的PIN码解锁 4
   SIM_STATE_READY            就绪状态 5
   */
        tm.getSimState();//int
        Log.d("SIM的状态信息", "" + tm.getSimState());

  /*
   * 唯一的用户ID：
   * 例如：IMSI(国际移动用户识别码) for activity_m1 GSM phone.
   * 需要权限：READ_PHONE_STATE
   */
        tm.getSubscriberId();//String
        Log.d("唯一的用户ID(IMSI)", "" + tm.getSubscriberId());

  /*
   * 取得和语音邮件相关的标签，即为识别符
   * 需要权限：READ_PHONE_STATE
   */
        tm.getVoiceMailAlphaTag();//String
        Log.d("识别符", "" + tm.getVoiceMailAlphaTag());

  /*
   * 获取语音邮件号码：
   * 需要权限：READ_PHONE_STATE
   */
        tm.getVoiceMailNumber();//String
        Log.d("获取语音邮件号码", "" + tm.getVoiceMailNumber());

  /*
   * ICC卡是否存在
   */
        tm.hasIccCard();//boolean
        Log.d("ICC卡是否存在", "" + tm.hasIccCard());

  /*
   * 是否漫游:
   * (在GSM用途下)
   */
        tm.isNetworkRoaming();//boolean
        Log.d("是否漫游", "" + tm.isNetworkRoaming());
    }
}

