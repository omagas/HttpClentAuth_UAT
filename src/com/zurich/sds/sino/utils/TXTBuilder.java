/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.sds.sino.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author louie.zheng
 */
public class TXTBuilder {
    private Log logger = LogFactory.getLog(this.getClass());
    
    public String getDateTime(){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String strDate = sdFormat.format(date);
        //System.out.println(strDate);
        return strDate;
    }       

    
    //寫入檔案至TXT
    public void  Write2TXT(String sentence,String locPath,String folderPath,String DataStringid){
      
        System.out.println("File name is: "+getDateTime());
        System.out.println(locPath+folderPath+DataStringid.trim()+"_IA.txt");
            // new InputStreamReader(new ByteArrayInputStream(str.getBytes(), "Big5")); //字串
            
            PrintWriter pw;
            try {
                //"./Upload_tmp/"
                pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(locPath+folderPath+DataStringid.trim()+"_IA.txt"), "Big5"));
                pw.write(sentence);
                pw.flush();
                pw.close();   
            } catch (UnsupportedEncodingException ex) {
                logger.error(ex);
                ex.printStackTrace(); 
            } catch (FileNotFoundException ex) {
                logger.error(ex);
                ex.printStackTrace();
            }
            logger.info("File has been saved /SDSALE"+getDateTime().trim()+".txt");
            //FileWriter fw = new FileWriter("test.txt");
            
        //while (line != null) {
                //System.out.println(line);
         
        //}    

   }       

    //寫入檔案至TXT
    public void  Write2TXT(String sentence,String locPath,String folderPath,String DataStringid,String Type){
      
        System.out.println("File name is: "+getDateTime());
        System.out.println(locPath+folderPath+DataStringid.trim()+"_QA.txt");
            // new InputStreamReader(new ByteArrayInputStream(str.getBytes(), "Big5")); //字串
            
            PrintWriter pw;
            try {
                //"./Upload_tmp/"
                pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(locPath+folderPath+DataStringid.trim()+"_QA.txt"), "Big5"));
                pw.write(sentence);
                pw.flush();
                pw.close();   
            } catch (UnsupportedEncodingException ex) {
                logger.error(ex);
                ex.printStackTrace(); 
            } catch (FileNotFoundException ex) {
                logger.error(ex);
                ex.printStackTrace();
            }
            logger.info("File has been saved /SDSALE"+getDateTime().trim()+".txt");
            //FileWriter fw = new FileWriter("test.txt");
            
        //while (line != null) {
                //System.out.println(line);
         
        //}    

   }     
  
}
