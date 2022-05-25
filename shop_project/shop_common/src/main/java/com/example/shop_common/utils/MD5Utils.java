package com.example.shop_common.utils;

import com.example.shop_common.common.constant.NormalConstant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String getMD5Str(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((val+ NormalConstant.SALT).getBytes()));
    }

    public static void main(String[] args) {
        try {
            System.out.println(getMD5Str("yang"+NormalConstant.SALT));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
