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
          
            // �W�L AS400 ���ת������I��
            if (BatchUtils.getInstance().calculateAS400Length(dataStr) > length) {
                dataStr = BatchUtils.getInstance().splitByLength(dataStr, length)[0];
            }

            // �ɺ����
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
            // lengthStr �ݭq�� X,Y �䤤 X ����Ʀ��, Y ���p�Ʀ��
            int integerLen = NumberUtils.toInt(StringUtils.substringBefore(lengthStr, ","));
            int decimalLen = NumberUtils.toInt(StringUtils.substringAfter(lengthStr, ","));
            if (padded == null) {
                padded = this.getDefaultPadded();
            }
            dataStr = StringUtils.trimToEmpty(dataStr);
            int integerData = NumberUtils.toInt(StringUtils.substringBefore(dataStr, "."));
            double decimalData = NumberUtils.toDouble("0." + StringUtils.substringAfter(dataStr, "."));
            BigDecimal decimalDataBD = new BigDecimal(decimalData).setScale(8, RoundingMode.HALF_UP); // �p���I�Ĥ���|�ˤ��J, �H�קK 47.6 �ܦ� 47.5999...
            if (decimalDataBD.toString().indexOf("1.") >= 0) { // ���]�O 0.99999... �i�즨1.00000..., ��Ʀ�ݥ[1, �p�Ʀ�ݴ�1
                integerData += 1;
                decimalDataBD.add(new BigDecimal(-1));
            }
            String result = null;
            if (decimalLen > 0) {
                // ��Ƴ����q�����0, �p�Ƴ����q�k���0, ���z�| direction �Ѽ�
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
//        System.out.println(BatchUtils.getInstance().calculateLengthByByte("������"));
//        System.out.println(BatchUtils.getInstance().calculateLengthByByte("������"));
//        System.out.println(BatchUtils.getInstance().calculateLengthByByte("������"));
//        System.out.println(BatchUtils.getInstance().calculateLengthByByte("��?GG"));
//        System.out.println("[" + Type.STRING.fillData("��?GG��?GG��?GG��?GG��?GG��?GG", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData(null, null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("�x�@", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("aaa", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("�xa�@", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("a�xa", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("a�xaa�xa", null, "8", null) + "]");
//        System.out.println("[" + Type.STRING.fillData("a�xa�xa", null, "8", null) + "]");
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
