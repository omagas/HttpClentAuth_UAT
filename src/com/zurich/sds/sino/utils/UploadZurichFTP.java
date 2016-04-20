/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.sds.sino.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;




import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author web.ap
 */
public class UploadZurichFTP {
    private Log logger = LogFactory.getLog(this.getClass());
    private String LocalFile;   
    private boolean completed;  


    public String getLocalFile() {
        return LocalFile;
    }

    public void setLocalFile(String LocalFile) {
        this.LocalFile = LocalFile;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    
    
    public void controller(FTPClient ftpClient,String preaperUpDirPath,String remoteDirPath_dir) throws FileNotFoundException, IOException{

		//FTPClient ftpClient = new FTPClient();
		try {


                        // APPROACH #2: uploads second file using an OutputStream
                        File secondLocalFile = new File(preaperUpDirPath+this.LocalFile);
                        //System.out.println("..........LocalFile..prepare up load loc:"+secondLocalFile);
                        String secondRemoteFile = remoteDirPath_dir+"/"+this.LocalFile;
                        InputStream inputStream = new FileInputStream(secondLocalFile);

                       
                        System.out.println("..........¡iUPLOAD¡j the file from "+secondLocalFile+" to "+secondRemoteFile);
                        logger.info("..........¡iUPLOAD¡j the file from "+secondLocalFile+" to "+secondRemoteFile);
                        OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
                        
                            //ftpClient.deleteFile(secondRemoteFile);
                        byte[] bytesIn = new byte[4096];
                        int read = 0;

                        while ((read = inputStream.read(bytesIn)) != -1) {
                                outputStream.write(bytesIn, 0, read);
                        }
                        inputStream.close();
                        outputStream.close();

                        boolean completed = ftpClient.completePendingCommand();
                        if (completed) {
                                System.out.println("The  file is uploaded successfully.");
                                logger.info("The  file is uploaded successfully.");
                                setCompleted(completed);
                        }

		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
                        logger.info("Error: " + ex.getMessage());
			ex.printStackTrace();                        
		}    
               
    }    
}
