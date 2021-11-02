package com.bovefqm.javafx.Utils;

public class typeChanges {
    public static int ipToLong(String ip) {
        String[] ipArray = ip.split("\\.");
        int num = 0;
        for (int i = 0; i < ipArray.length; i++) {
            int valueOfSection = Integer.parseInt(ipArray[i]);//将8位字符串转化为整型
            //对每段ip值左移，再进行按位或运算，最后得到一个十进制数值
            num = (valueOfSection << 8 * (3 - i)) | num;
        }
        return num;
    }
    public static String longToIp(int num) {

        String[] ipString = new String[4];
        for (int i = 0; i < 4; i++) {
            //将数值每8位跟 11111111 按位与运算，得到8位的二进制数
            int and = num & (255 << (8 * (3 - i)));
            //再将每段进行无符号右移，得到每段的ip值
            ipString[i] = String.valueOf(and >>> (8 * (3 - i)));
        }
        String ip;//将字符串数组拼接为IP地址形式
        ip = String.join(".", ipString);
        return ip;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param hexString 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }

    /** 计算校验位 ，返回十六进制校验位 */
    public static String makeCheckSum(String data) {
        int dSum = 0;
        int length = data.length();
        int index = 0;
        // 遍历十六进制，并计算总和
        while (index < length) {
            String s = data.substring(index, index + 2); // 截取2位字符

            dSum += Integer.parseInt(s, 16); // 十六进制转成十进制 , 并计算十进制的总和

            index = index + 2;
        }

        //int mod = dSum % 256; // 用256取余，十六进制最大是FF，FF的十进制是255

        //String checkSumHex = Integer.toHexString(mod); // 余数转成十六进制
        String checkSumHex = Integer.toHexString(dSum);  //十进制校验和转16进制
        length = checkSumHex.length();
        if (length < 8) {
            checkSumHex = "0" + checkSumHex;  // 校验位不足两位的，在前面补0
        }
        System.out.println("chesum:"+checkSumHex);
        return checkSumHex;
    }

}
