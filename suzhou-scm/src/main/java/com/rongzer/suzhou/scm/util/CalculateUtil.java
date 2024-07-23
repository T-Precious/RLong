package com.rongzer.suzhou.scm.util;

import java.text.DecimalFormat;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2022/12/2 14:46
 * @Version 1.0
 **/
public class CalculateUtil {

    /**
     * 获取百分比
     * @param number1 /
     * @param number2 总数/
     * @return java.lang.String /
     * @date 2022/5/30 14:48
     */
    public static String getRatio(Long number1, Long number2) {
        DecimalFormat df = new DecimalFormat("0.0");
        if(number2 > number1) {
            if (number2 != 0) {
                return df.format((float) (number1 / (double) number2) * 100) + "%";
            } else {
                return "0%";
            }
        }else if(number2 < number1){
            if (number1 != 0) {
                return df.format((float) (number2 / (double) number1) * 100) + "%";
            } else {
                return "0%";
            }
        }else{
            return "0%";
        }
    }
  /**
   * @Description:获取固定百分比
   * @Author: tian.changlong
   * @Date: 2023/1/3 17:21
   * @param molecule: 分子
   * @param denominator: 分母
   * @return: java.lang.String
   **/
    public static String getFixRatio(Long molecule, Long denominator) {
        DecimalFormat df = new DecimalFormat("0.0");
            if (denominator != 0) {
                return df.format((float) (molecule / (double) denominator) * 100) + "%";
            } else {
                return "0%";
            }

    }
    public static boolean isNumeric(String str){
        //-?：代表负号（可选）
        //\\d+：代表1个或多个数字
        //(\\.\\d+)?：代表一个小数点和之后的1个或多个数字（可选的）
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    public static void main(String[] args){
        String str1 = "12345";
        String str2 = "12.34";
        String str3 = "null";
        String str4 = "123abc";
        String str5 = "a123bc";
        String str6 = "abc123";

        System.out.println(isNumeric(str1));  // true
        System.out.println(isNumeric(str2));  // true
        System.out.println(isNumeric(str3));  // false
        System.out.println(isNumeric(str4));  // true
        System.out.println(isNumeric(str5));  // true
        System.out.println(isNumeric(str6));  // false
    }

}
