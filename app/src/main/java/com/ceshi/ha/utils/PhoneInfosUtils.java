package com.ceshi.ha.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/8/18.
 */
public class PhoneInfosUtils {

    Context mcontext;
    BluetoothAdapter mBluetoothAdapter;

    public PhoneInfosUtils(Context context) {
        mcontext = context;
    }

    /**
     * 获取用户手机信息
     *
     * @return list集合
     */
    public List<String> getPhoneInfos() {
        List<String> list = new ArrayList<String>();
        TelephonyManager telManager = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
        String mode = android.os.Build.MODEL;//手机型号
        list.add(mode);
        String device = android.os.Build.BRAND;//手机品牌
        list.add(device);
        String imei = telManager.getDeviceId();//手机imei
        list.add(imei);
        String imsi = telManager.getSubscriberId();//手机imsi
        list.add(imsi);
        String phoneNumber = telManager.getLine1Number();
        if (phoneNumber != null && phoneNumber.startsWith("+86")) {
            phoneNumber = phoneNumber.substring(3);
        }
        list.add(phoneNumber);//手机号码
        String phoneType = "";
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                phoneType = "中国移动";
            } else if (imsi.startsWith("46001")) {
                phoneType = "中国联通  ";
            } else if (imsi.startsWith("46003")) {
                phoneType = "中国电信";
            }
            list.add(phoneType);//手机运营商
        }
        return list;
    }

    /**
     * 获取WIFI的IP
     *
     * @return
     */
    public String getWifi() {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) mcontext.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            return "";
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int i = wifiInfo.getIpAddress();
        //对ip地址拼接
        String ipAddress = (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
        return ipAddress;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获得UUID
     *
     * @return
     */
    public String getUUID() {
        // 1 compute IMEI
        TelephonyManager TelephonyMgr = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
        String m_szImei = TelephonyMgr.getDeviceId(); // Requires

        // 2 compute DEVICE ID
        String m_szDevIDShort = "35" + // we make this look like activity_m1 valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; // 13 digits
        // 3 android ID - unreliable
        String m_szAndroidID = Settings.Secure.getString(mcontext.getContentResolver(), Settings.Secure.ANDROID_ID);

        // 4 wifi manager, read MAC address - requires
        // android.permission.ACCESS_WIFI_STATE or comes as null
        WifiManager wm = (WifiManager) mcontext.getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();

        // 5 Bluetooth MAC address android.permission.BLUETOOTH required
        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        String m_szBTMAC = "";
        try {
            m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (m_BluetoothAdapter != null) {
                m_szBTMAC = m_BluetoothAdapter.getAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 6 SUM THE IDs
        String m_szLongID = m_szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        byte p_md5Data[] = m.digest();

        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is activity_m1 single digit, make sure it have 0 in front (proper
            // padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
            // add number to string
            m_szUniqueID += Integer.toHexString(b);
        }
        m_szUniqueID = m_szUniqueID.toUpperCase();
        return m_szUniqueID;
    }

    /**
     * 获取已经保存过的设备信息
     *
     * @param address
     */
    public void getBlue(String address) {
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        if (devices.size() > 0) {
            for (Iterator<BluetoothDevice> iterator = devices.iterator(); iterator.hasNext(); ) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                System.out.println("设备：" + bluetoothDevice.getName() + " " + bluetoothDevice.getAddress());
            }
        }
    }

    /**
     * 获取手机IP
     *
     * @return
     */
    public static String getPhoneIP() {
        String IP = "";
        try {
//            String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
            String address = "";
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();

                // 将流转化为字符串
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String tmpString = "";
                StringBuilder retJSON = new StringBuilder();
                while ((tmpString = reader.readLine()) != null) {
                    retJSON.append(tmpString + "\n");
                }

                JSONObject jsonObject = new JSONObject(retJSON.toString());
                String code = jsonObject.getString("code");
                if (code.equals("0")) {
                    JSONObject data = jsonObject.getJSONObject("data");
//                  IP = data.getString("ip") + "(" + data.getString("country")
//                          + data.getString("area") + "区"
//                          + data.getString("region") + data.getString("city")
//                          + data.getString("isp") + ")";
                    IP = data.getString("ip");

                    Log.e("提示", "您的IP地址是：" + IP);
                } else {
                    IP = "";
                    Log.e("提示", "IP接口异常，无法获取IP地址！");
                }
            } else {
                IP = "";
                Log.e("提示", "网络连接异常，无法获取IP地址！");
            }
        } catch (Exception e) {
            IP = "";
            Log.e("提示", "获取IP地址时出现异常，异常信息是：" + e.toString());
        }
        return IP;
    }
}

