package cn.wilson.hotblogs.helper;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by KingFlyer on 2015/1/21.
 */
public class TokenHelper {
    public static String GetSignature() {
        //String sign = GetSignString();
        String TimeStamp = GetTimeStamp();
        String result = "open." + GetSignString(TimeStamp) + "." + TimeStamp + ".headin2011";

        return result;
    }

    //获得时间戳
    public static String GetTimeStamp(){
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    //自封装join方法
    public static String JoinArrar( Object[] o , String flag ){
        StringBuffer str_buff = new StringBuffer();

        for(int i=0 , len=o.length ; i<len ; i++){
            str_buff.append(String.valueOf(o[i]));
            if(i<len-1)
                str_buff.append(flag);
        }

        return str_buff.toString();
    }

    //获得加密签名
    private static String GetSignString(String TimeStamp){
        String[] arr = {"hszv85lpyuy9lp06muexwzd3", TimeStamp, "headin2011"};
        Arrays.sort(arr);
        String lpSign = JoinArrar(arr, "");
        //String lpSign = "1421833957headin2011hszv85lpyuy9lp06muexwzd3";==>aab5486e04e65ea7b220586578776a21a9b88459
        try {
            byte[] plainText = lpSign.getBytes("utf-8");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(plainText);
            byte[] digest = messageDigest.digest();
            lpSign = byteArrayToHexString(digest);
        }
        catch (Exception ex){}
        return lpSign;
    }

    // 将字节数组转换为十六进制字符串
    private static String byteArrayToHexString(byte[] bytearray) {
        String strDigest = "";
        for (int i = 0; i < bytearray.length; i++) {
            strDigest += byteToHexString(bytearray[i]);
        }
        return strDigest.toLowerCase();
    }

    // 将字节转换为十六进制字符串
    private static String byteToHexString(byte ib) {
        char[] Digit = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                'D', 'E', 'F'
        };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }
}
