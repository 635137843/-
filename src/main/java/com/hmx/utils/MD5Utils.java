package com.hmx.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName MD5Utils
 * @Description MD5加密实现类
 * @Author xin
 * @Date 2020/3/8 9:12
 * @Version 1.0
 **/
public class MD5Utils {

    public static String md5(String painText) {
        byte[] secretBytes = null;

        try {
            secretBytes = MessageDigest.getInstance("md5").digest(painText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("md5加密失败");
        }

        String md5code = new BigInteger(1, secretBytes).toString(16);

        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public static void main(String[] args) {
        System.out.println(md5("123123"));
    }
}
