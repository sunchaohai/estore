package com.hai.chao.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: web-review
 * @description: MD5加密实现
 * @author: xiaohai
 * @create: 2018-08-21 16:35
 **/
public class MD5Utils {
    public static String getMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] bts = md.digest(str.getBytes());
        //加密好了，固定32长度输出
        String mdtStr = "";

        for(byte bt : bts){
            String temp = Integer.toHexString(bt&0Xff);
            if(temp.length() == 1){
              temp = "0" + temp;
            }

            mdtStr = mdtStr + temp;
        }

        return mdtStr;
    }
}
