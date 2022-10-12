package com.imooc.mall.util;

import com.imooc.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * MD5工具
 */
public class MD5utils {
    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((strValue + Constant.SALT).getBytes()));
    }

    //测试生成MD5的值
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String md5Str = getMD5Str(("1234"));
        System.out.println(md5Str);
    }
}
