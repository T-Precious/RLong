package com.rongzer.suzhou.scm.util;

import com.rongzer.eloan.common.starter.exception.BizException;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/2/11
 * Description: 数字相关工具类
 */
public class DigitUtil {

    /**
     * 百分比数值*100
     * @param pecentNum
     * @return
     */
    public static Integer pecentNumMutiply100(BigDecimal pecentNum) {
        if (pecentNum.scale() > 2) {
            throw new BizException("百分比数值保留小数不能超过2位");
        }
        return pecentNum.multiply(BigDecimal.valueOf(100)).intValue();
    }

    /**
     * 数值/100，结果保留两位小数
     * @param num
     * @return
     */
    public static BigDecimal numDivide100(int num) {
        return BigDecimal.valueOf(num).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_UNNECESSARY);
    }

    /**
     * 获取浮点数整数部分
     * @param num
     * @return
     */
    public static long getFloatIntegerPart(BigDecimal num) {
        return num.setScale(0, BigDecimal.ROUND_DOWN).longValue();
    }

    /**
     * 获取浮点数(保留两位小数)，小数部分数值
     * @param num
     * @return
     */
    public static Integer getFloatDecimalPart(BigDecimal num) {
        BigDecimal decimal = num.subtract(num.setScale(0, BigDecimal.ROUND_DOWN));

        return decimal.multiply(BigDecimal.valueOf(100)).intValue();
    }

    /**
     * 根据整数部分数值与小数部分数值计算得到浮点数（保留两位小数）
     * @param integerPart
     * @param decimalPart
     * @return
     */
    public static BigDecimal getFloatWithIntegerAndDecimalPart(long integerPart, int decimalPart) {
        BigDecimal decimal = BigDecimal.valueOf(decimalPart).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_UNNECESSARY);
        return BigDecimal.valueOf(integerPart).add(decimal);
    }

}
