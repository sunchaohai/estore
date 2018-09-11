package com.hai.chao.utils;

import java.util.UUID;

/**
 * @program: estore
 * @description: UUID工具类
 * @author: xiaohai
 * @create: 2018-09-07 21:37
 **/
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
