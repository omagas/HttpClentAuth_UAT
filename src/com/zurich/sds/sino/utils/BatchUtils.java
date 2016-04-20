/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.sds.sino.utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang.StringUtils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author jason.huang
 */
public class BatchUtils {
private Log log = LogFactory.getLog(this.getClass());
    private static BatchUtils instance = new BatchUtils();
    private Connection connGlobal = null;

    private BatchUtils() {
    }

    public static BatchUtils getInstance() {
        return instance;
    }
//
//    public List query(String sql, Object[] params) throws Exception {
//        return query(sql, new MapListHandler(), params);
//    }
//
//    public List query(String sql, ResultSetHandler handler, Object[] params) throws Exception {
//        Connection conn = null;
//        try {
//            conn = ConnectionFactory.getInstance().getConnection();
//            return (List) new QueryRunner().query(conn, sql, handler, params);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        } finally {
//            ConnectionFactory.getInstance().releaseConnection(conn);
//        }
//    }
//
//    public Map querySingle(String sql, Object[] params) throws Exception {
//        List result = query(sql, new MapListHandler(), params);
//        return CollectionUtils.isEmpty(result) ? null : (Map) result.get(0);
//    }
//
//    public void update(String sql, Object[] params) throws Exception {
//        Connection conn = null;
//        try {
//            conn = ConnectionFactory.getInstance().getConnection();
//            //System.out.println("updateSQL=" + sql + ",params" + params);
//            new QueryRunner().update(conn, sql, params);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        } finally {
//            ConnectionFactory.getInstance().releaseConnection(conn);
//        }
//    }
//
//    private Connection getConnection() throws Exception {
//        return ConnectionFactory.getInstance().getConnection();
//    }

    public String[] splitByLength(String data, int length) {
        if (StringUtils.isEmpty(data)) {
            System.out.println("''=" + "");
            return new String[]{""};
        } else if (this.calculateAS400Length(data) < length) {
            System.out.println("data=" + data);
            return new String[]{data};
        }
        List<String> coll = new ArrayList<String>();
        int totalLen = 0;
        String temp = "";
        boolean preIsDigit = false;
        boolean changeDigit = false;
        boolean isFirst = true;
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            int lenAS400Min = this.calAS400Length(c, isFirst, false, !isFirst && preIsDigit != this.isDigit(c));
            int lenAS400Max = this.calAS400Length(c, isFirst, true, !isFirst && preIsDigit != this.isDigit(c));
            System.out.print("c=" + c + ",totalLen=" + totalLen + ",lenAS400Min=" + lenAS400Min + ",lenAS400Max=" + lenAS400Max + ",isFirst=" + isFirst + ",changeDigit=" + changeDigit + ",preIsDigit=" + preIsDigit);
            if (length < (totalLen + lenAS400Max)) {
                coll.add(temp);
                temp = "" + c;
                preIsDigit = false;
                changeDigit = false;
                isFirst = false;
                totalLen = this.calAS400Length(c, true, false, false);
            } else {
                temp += c;
                totalLen += lenAS400Min;
                isFirst = false;
                changeDigit = preIsDigit != this.isDigit(c);
                preIsDigit = this.isDigit(c);
            }
            System.out.println(",temp=" + temp);
            //System.out.println(data.charAt(i) + "-" + isDigit + ",length="+length);
        }
        if (!StringUtils.isEmpty(temp)) {
            coll.add(temp);
        }
        System.out.println("coll=" + coll);
        String[] result = new String[coll.size()];
        for (int i = 0; i < coll.size(); i++) {
            result[i] = coll.get(i);
        }

        return result;
//        System.out.println(data + ",length=" + len);
    }

    public String[] splitByLength2(String data, int length) {
        if (StringUtils.isEmpty(data)) {
            System.out.println("''=" + "");
            return new String[]{""};
        } else if (this.calculateAS400Length(data) < length) {
            System.out.println("data=" + data);
            return new String[]{data};
        }
        String dataWith0E0F = "";
        char gap0E = 0x0E;
        char gap0F = 0x0F;
        Boolean isPreviousDigit = null;
        // ?¨å???º¤?????? 0F
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            boolean isDigit = this.isDigit(c);
            if (isPreviousDigit == null) {
                if (!isDigit) {
                    dataWith0E0F += gap0E;
                }
            } else {
                if (isDigit != isPreviousDigit) {
                    if (isDigit) {
                        dataWith0E0F += gap0F;
                    } else {
                        dataWith0E0F += gap0E;
                    }
                }
            }
            dataWith0E0F += c;
            isPreviousDigit = isDigit;
        }
        if (!this.isDigit(data.charAt(data.length()-1))) {
            dataWith0E0F += gap0F;
        }
System.out.println("data=[" + data + "]"+",data.length="+this.calculateLengthByByte(data));
System.out.println("dataWith0E0F=[" + dataWith0E0F + "]"+",dataWith0E0F.length="+this.calculateLengthByByte(dataWith0E0F)+",calculateAS400Length="+this.calculateAS400Length(data));
        List<String> coll = new ArrayList<String>();
        String temp = "";
        int totalLen = 0;
        int index = 0;
        while (true) {
            if (index >= dataWith0E0F.length()) {
                break;
            }
            int currLen = 0;
            char c = data.charAt(index);
            if (this.isDigit(c)) {
                currLen = 1;
            } else {
                currLen = 2;
            }
            if (index < dataWith0E0F.length()-1) {
                char next = data.charAt(index+1);
                if (next == gap0F) {
                    currLen = 3;
                }
            }
            if ((totalLen + currLen) > length) {
                coll.add(temp);
                temp = "" + c;
                //totalLen =
            }
            index ++;
        }
        Character pre = null;
        for (int i = 0; i < dataWith0E0F.length(); i++) {
            char c = data.charAt(i);
            int lenAS400Min = this.calAS400Length(c, pre != null, false, pre != null && this.isDigit(pre) != this.isDigit(c));
            int lenAS400Max = this.calAS400Length(c, pre != null, true, pre != null && this.isDigit(pre) != this.isDigit(c));
            System.out.print("c=" + c + ",totalLen=" + totalLen + ",lenAS400Min=" + lenAS400Min + ",lenAS400Max=" + lenAS400Max);
            if (length < (totalLen + lenAS400Max)) {
                coll.add(temp);
                temp = "" + c;
                totalLen = this.calAS400Length(c, true, false, false);
            } else {
                temp += c;
                totalLen += lenAS400Min;
            }
            pre = c;
            System.out.println(",temp=" + temp);
            //System.out.println(data.charAt(i) + "-" + isDigit + ",length="+length);
        }
        if (!StringUtils.isEmpty(temp)) {
            coll.add(temp);
        }
        System.out.println("coll=" + coll);
        String[] result = new String[coll.size()];
        for (int i = 0; i < coll.size(); i++) {
            result[i] = coll.get(i);
        }

        return result;
//        System.out.println(data + ",length=" + len);
    }

    public int calculateAS400Length(String data) {
        int len = 0;
        boolean preIsDigit = false;
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            len += this.calAS400Length(c, i == 0, i == data.length() - 1, preIsDigit != this.isDigit(c));
            preIsDigit = this.isDigit(c);
            //System.out.println(data.charAt(i) + "-" + isDigit + ",length="+length);
        }
//        System.out.println(data + ",length=" + len);
        return len;
    }

    public int calculateAS400Length2(String data) {
        String dataWith0E0F = "";
        char gap0E = 0x0E;
        char gap0F = 0x0F;
        Boolean isPreviousDigit = null;
        // ?¨å???º¤?????? 0F
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            boolean isDigit = this.isDigit(c);
            if (isPreviousDigit == null) {
                if (!isDigit) {
                    dataWith0E0F += gap0E;
                }
            } else {
                if (isDigit != isPreviousDigit) {
                    if (isDigit) {
                        dataWith0E0F += gap0F;
                    } else {
                        dataWith0E0F += gap0E;
                    }
                }
            }
            dataWith0E0F += c;
            isPreviousDigit = isDigit;
        }
        if (!this.isDigit(data.charAt(data.length()-1))) {
            dataWith0E0F += gap0F;
        }
        return this.calculateLengthByByte(dataWith0E0F);
    }

    private boolean isDigit(char c) {
        return c < 128;
    }

    private int calAS400Length(char c, boolean isFirst, boolean isLast, boolean changeDigit) {
        boolean isDigit = this.isDigit(c);
        int result = 0;
        if (isFirst) { //ç¬¬ä???har
            result = isDigit ? 1 : 3;
        } else if (isLast) { //???ä¸??char
            result = isDigit ? 1 : 3;
            result += changeDigit ? 1 : 0;
        } else {
            result = isDigit ? 1 : 2;
            result += changeDigit ? 1 : 0;
        }
        return result;
    }

    public int calculateLengthByByte(String data) {
        try {
            return StringUtils.isEmpty(data) ? 0 : data.getBytes("big5").length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.length();
    }

    public void putAll(Map oldMap, Map newMap) {
        if (newMap != null) {
            oldMap.putAll(newMap);
        }
    }

    public Map copyMapWithoutValue(Map data) {
        Map result = new HashMap();
        for (Object key : data.keySet()) {
            result.put(key, null);
        }
        return result;
    }

    public Map copyMapWithValue(Map data) {
        Map result = new HashMap();
        for (Object key : data.keySet()) {
            result.put(key, data.get(key));
        }
        return result;
    }
    
    public String translateBig5AndReplaceQuestionMark(String dataStr) {
        try {
            if (dataStr == null) {
                return dataStr;
            }
            String repl = "?";
            String with = "? ";
            int replaceCount = 0;
            String returnStr = new String(dataStr.getBytes("big5"), "big5");
            int fromIndex = 0;
            while (true) {
                int index = returnStr.indexOf(repl, fromIndex);
                if (index >= 0) {
                    if (!StringUtils.equals(new Character(dataStr.charAt(index - replaceCount)).toString(), repl)) {
                        // ?¨å?å­?¸²ä¸?? "?" ?? ??? "?" å¾?? " ", ä»¥ä???byte ?¸ä???                        returnStr = this.replaceFirst(returnStr, repl, with, index);
                        fromIndex = index + with.length();
                        replaceCount ++;
                    } else {
                        fromIndex = index + repl.length();
                    }
                } else {
                    break;
                }
            }
            return returnStr;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();  
            throw new RuntimeException(ex);
        }
    }    

    public String replaceFirst(String returnStr,String repl,String with,int index){
            //returnStr=returnStr.replaceAll("[?]", with);              
            String front=returnStr.substring(0, index);
            String end=returnStr.substring(index);           
            System.out.println("front:"+front+",end:"+end); 
            end=end.replaceFirst("["+repl+"]", with);
            System.out.println(front+end);
    return front+end;
    }    
    
    public static void main(String args[]) {
        //"?°å?å¸?¸­å±±å??±å?è¡??ï¼??ï¼??ï¼??ï¼?®¤"
//        BatchUtils.getInstance().calculateAS400Length("123");
//        BatchUtils.getInstance().calculateAS400Length("?°å?å¸?);
//        BatchUtils.getInstance().calculateAS400Length("?????");
//        BatchUtils.getInstance().calculateAS400Length("??1");
//        BatchUtils.getInstance().calculateAS400Length("11??);
//        BatchUtils.getInstance().calculateAS400Length("11??1");
//        BatchUtils.getInstance().calculateAS400Length("??1??);
//        BatchUtils.getInstance().calculateAS400Length("?°å?å¸?¸­å±±å??±å?è¡??ï¼??ï¼??ï¼??ï¼?®¤");
//        BatchUtils.getInstance().calculateAS400Length("?°å?å¸?¸­å±±å? ?±å?è¡?ï¼?????æ¨?ï¼??ï¼?®¤");
//        BatchUtils.getInstance().splitByLength2(null, 20);
//        BatchUtils.getInstance().splitByLength2("", 20);
//        BatchUtils.getInstance().splitByLength2("??1??, 20);
//        BatchUtils.getInstance().splitByLength2("?°å?å¸?¸­å±±å??±å?è¡??ï¼??ï¼??ï¼??ï¼?®¤", 20); //
//        BatchUtils.getInstance().splitByLength2("?°å?å¸?¸­å±±å??±å?è¡??ï¼??ï¼??ï¼??ï¼?®¤ä¹??", 20);
//        BatchUtils.getInstance().splitByLength2("?°å?å¸?ä¸­å±±???å´??ï¼?2ï¼??ï¼??ï¼??ï¼?å®¤ä?ä¸?, 20);
//        BatchUtils.getInstance().splitByLength2("?°å?å¸?11ä¸­å±±???±å?è¡??22ï¼??ï¼?æ¨??ï¼??1å®¤ä?ä¸?, 20);
//        BatchUtils.getInstance().splitByLength2("?°å?å¸?11ä¸­å±±???±å?è¡??22ï¼??ï¼?æ¨??ï¼??1å®¤ä?ä¸?, 10);
    }
}
