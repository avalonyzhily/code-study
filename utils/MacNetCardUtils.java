import org.springframework.util.StringUtils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 网卡mac地址获取工具类
 */
public class MacNetCardUtils {
    public static Map<String,String> queryNetCardNo() {
        Map<String,String> res = new HashMap<>();
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaceEnumeration.hasMoreElements()){
                NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
                if(networkInterface.getHardwareAddress()!=null) {
                    String macAddress = toMacString(networkInterface.getHardwareAddress());
                    if(!StringUtils.isEmpty(macAddress)) {
                        res.put(networkInterface.getDisplayName(), toMacString(networkInterface.getHardwareAddress()));
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return res;
    }

    private final static char[] HEX = "0123456789ABCDEF".toCharArray();

    /**
     * 二进制数组转换为16进制mac地址
     * @param bys
     * @return
     */
    private static String toMacString(byte[] bys) {
        if(bys == null) {
            return null;
        }
        char[] chs = new char[bys.length * 2];
        for(int i = 0, k = 0; i < bys.length; i++) {
            chs[k++] = HEX[(bys[i] >> 4) & 0xf];
            chs[k++] = HEX[bys[i] & 0xf];
        }
        return new String(chs);
    }
}
