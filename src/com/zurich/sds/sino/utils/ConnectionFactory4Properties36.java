/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.sds.sino.utils;

import com.zurich.sds.batch.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ??DBSetting.properties è¨­å??? ??? Connection, Batch ä½¿ç?
 * @author jason.huang
 */
public class ConnectionFactory4Properties36 extends ConnectionFactory {

    private Log log = LogFactory.getLog(this.getClass());
    private String driver = null;
    private String url = null;
    private String user = null;
    private String passwd = null;

    private ConnectionFactory4Properties36() {
        init();
    }

    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory4Properties36();
        }
        return instance;
    }

    private void init() {
        try {
            ResourceBundle resource = ResourceBundle.getBundle("DBSetting36");
            driver = resource.getString("db.driver.class");
            url = resource.getString("db.url");
            user = resource.getString("db.user");
            passwd = resource.getString("db.passwd");
            Class.forName(driver).newInstance();
            log.info("initial 36  JDBC driver OK");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw new RuntimeException("Get database connection failed, caused by: " + e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, passwd);
    }
}
