/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.sds.sino.utils;

import com.zurich.sds.batch.utils.BatchUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class Type {

    public static final Type STRING = new Type() {

        private Log log = LogFactory.getLog(this.getClass());

        public String fillData(String dataStr, String direction, String lengthStr, String padded) {
            if (padded == null) {
                padded = this.getDefaultPadded();
            }
            int length = NumberUtils.toInt(lengthStr);

            if (dataStr == null) {
                dataStr = "";
            }
          
            // 超過 AS400 長度的部分截掉
            if (BatchUtils.getInstance().calculateAS400Length(dataStr) > length) {
                dataStr = BatchUtils.getInstance().splitByLength(dataStr, length)[0];
            }

            // 補滿資料
            int lengthByByte = BatchUtils.getInstance().calculateLengthByByte(dataStr);
            String result = null;
            if (StringUtils.equals(direction, "L")) {
                result = StringUtils.leftPad(dataStr, length - lengthByByte + dataStr.length(), padded);
            } else {
                result = StringUtils.rightPad(dataStr, length - lengthByByte + dataStr.length(), padded);
            }
            log.info("dataStr=" + dataStr + ",direction=" + direction + ",length=" + lengthStr + ",padded=" + padded + ",lengthByByte=" + lengthByByte + ",dataLength=" + dataStr.length() + ",result=" + result);
            return result;
        }

        public String getDefaultPadded() {
            return " ";
        }
    };
    public static final Type NUMBER = new Type() {

        private Log log = LogFactory.getLog(this.getClass());

        public String fillData(String dataStr, String direction, String lengthStr, String padded) {
            // lengthStr 需訂為 X,Y 其中 X 為整數位數, Y 為小數位數
            int integerLen = NumberUtils.toInt(StringUtils.substringBefore(lengthStr, ","));
            int decimalLen = NumberUtils.toInt(StringUtils.substringAfter(lengthStr, ","));
            if (padded == null) {
                padded = this.getDefaultPadded();
            }
            dataStr = StringUtils.trimToEmpty(dataStr);
            int integerData = NumberUtils.toInt(StringUtils.substringBefore(dataStr, "."));
            double decimalData = NumberUtils.toDouble("0." + StringUtils.substringAfter(dataStr, "."));
            BigDecimal decimalDataBD = new BigDecimal(decimalData).setScale(8, RoundingMode.HALF_UP); // 小數點第六位四捨五入, 以避免 47.6 變成 47.5999...
            if (decimalDataBD.toString().indexOf("1.") >= 0) { // 假設是 0.99999... 進位成1.00000..., 整數位需加1, 小數位需減1
                integerData += 1;
                decimalDataBD.add(new BigDecimal(-1));
            }
            String result = null;
            if (decimalLen > 0) {
                // 整數部分從左邊補0, 小數部分從右邊補0, 不理會 direction 參數
                result = Type.STRING.fillData(String.valueOf(integerData), "L", String.valueOf(integerLen), padded)
                        + Type.STRING.fillData(StringUtils.substringAfter(decimalDataBD.toString(), "0."), "R", String.valueOf(decimalLen), padded);
            } else {
                result = Type.STRING.fillData(String.valueOf(integerData), "L", String.valueOf(integerLen), padded);
            }

            log.trace("dataStr=" + dataStr + ",direction=" + direction + ",length=" + lengthStr + ",padded=" + padded + ",integerLen=" + integerLen + ",decimalLen=" + decimalLen + ",result=" + result);
            return result;
        }

        public String getDefaultPadded() {
            return "0";
        }
    };

    public abstract String fillData(String data, String direction, String length, String padded);

    public abstract String getDefaultPadded();

    public static Type getType(String type) {
        if (StringUtils.contains(type, "string")) {
            return STRING;
        } else if (StringUtils.contains(type, "number")) {
            return NUMBER;
//        } else if (StringUtils.contains(type, "date")) {
//            return DATE;
        } else {
            return STRING;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(this.getClass().getName()).append("]\r\n");
        return sb.toString();
    }

    public static void main(String[] args) {
//        System.out.println(BatchUtils.getInstance().calculateLengthByByte("123"));
//        System.out.println(BatchUtils.getInstance().calculateLengthByByte("１２３"));
//        System.out.println(BatchUtils.getInstance().calculateLengthByByte("①②③"));
//        System.out.println(BatchUtils.getInstance().calculateLengthByByte("ⅠⅡⅢ"));
//        System.out.println(BatchUtils.getInstance().calculateLengthByByte("雞?GG"));
//        System.out.println("[" + Type.STRING.fillData("雞?GG雞?GG雞?GG雞?GG雞?GG雞?GG", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData(null, null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("台一", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("aaa", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("台a一", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("a台a", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("a台aa台a", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("a台a台a", null, "8", null) + "]");
//
//        System.out.println("[" + Type.NUMBER.fillData("", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData(null, null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("0", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("0.", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData(".0", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("0.0", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData(".234", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("0.234", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("15", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("15.", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("15.0", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("15.234", null, "10", null) + "]");
//
//        System.out.println("[" + Type.NUMBER.fillData("", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData(null, null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("0", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("0.", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData(".0", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("0.0", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData(".234", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("0.234", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("15", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("15.", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("15.0", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("15.234", null, "10,5", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("500000.000000", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("500000.056780", null, "10", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("500000.000000", null, "10,3", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("500000.056780", null, "10,3", null) + "]");
//        System.out.println("[" + Type.NUMBER.fillData("0.4514", null, "2,5", null) + "]");
    }
}
