package cn.wilson.hotblogs.helper;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * Created by KingFlyer on 2015/3/20.
 */
public class HardwareHelper {

    //获取系统版本
    public static String GetAndroidVersion(){

        return TextUtils.isEmpty(Build.VERSION.RELEASE) ? "" : Build.VERSION.RELEASE;
    }

    //获取手机型号
    public static String GetPhoneType(){

        return TextUtils.isEmpty(Build.MODEL) ? "" : Build.MODEL;
    }

    //获得本机IP
    public static String GetLocalIpAddress() {
        try {
            for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();en.hasMoreElements();){
                NetworkInterface intf = en.nextElement();
                for(Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();){
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if(!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)){
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    //获得Wifi IP
    public static String GetWIFIIpAddress(Context context){
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            // 获取32位整型IP地址
            int ipAddress = wifiInfo.getIpAddress();

            //返回整型地址转换成“*.*.*.*”地址
            return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "";
    }

    //获得本机Mac
    public static String GetLocalMacAddress(){
        String macSerial = "";
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return macSerial;
    }

    //获得Wifi Mac
    public static String GetWifiMacAddress(Context context){
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();

            return info.getMacAddress() + "|" + info.getSSID();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "";
    }

    //获取本机号码
    public static String GetPhoneNumber(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return tm.getLine1Number();
    }

    //获得Sim Serial Number
    public static String GetSimSerialNumber(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return tm.getSimSerialNumber();
    }

    //获得DevideID
    public static String GetDeviceID(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return tm.getDeviceId();
    }

    //获得设备的软件版本号
    public static String GetDeviceSoftwareVersion(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return tm.getDeviceSoftwareVersion();
    }

    //获得手机设备信息
    public static String GetDeviceInfor(Context context){
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            return tm.getLine1Number() + "|" + tm.getSimSerialNumber() + "|" + tm.getDeviceId() + "|" + tm.getDeviceSoftwareVersion();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "";
    }

    //获得AndroidID
    public static String GetAndroidID(Context context){

        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "";
    }

    private static String uuID = "";
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String GetUUID(Context context) {
        if (TextUtils.isEmpty(uuID)) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                uuID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return uuID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }

}
