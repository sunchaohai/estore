package com.hai.chao.utils;

/**
 * @program: web-review
 * @description: 目录分离
 * @author: xiaohai
 * @create: 2018-08-27 00:10
 **/
public class DirUtil {
   /**
    * 获取路径，不存在不会创建，需使用者自己创建
    * @Param: [fileName]
    * @return: java.lang.String
    * @Author: xiaohai
    * @Date: 2018/8/27
    */
    public static String getDir(String fileName){
        //获取哈希值
        int hashCode = fileName.hashCode();

        //开始计算
        //int类型数据，4个字节，32位，每四位获取一个数字，可以获取8个数字
        //这8个数字，都，作为当前文件要存贮的目录
        //获取当前哈希值最低4位，作为一级目录
        int dir1 = hashCode & 15;
        //将哈希值，向右移动4位，更新最低四位
        hashCode = hashCode >>> 4;

        int dir2 = hashCode & 15;

        return "/"+dir1+"/"+dir2;
    }
}
