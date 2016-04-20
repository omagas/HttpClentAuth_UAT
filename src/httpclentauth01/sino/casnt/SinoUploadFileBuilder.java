/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpclentauth01.sino.casnt;
import com.zurich.sds.sino.utils.UploadZurichFTP;


import com.zurich.sds.sino.utils.PropertiesTool;
import com.zurich.sds.sino.utils.QueryUtils36;
import com.zurich.sds.sino.utils.TXTBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import sun.awt.geom.AreaOp.AddOp;



/**
 *
 * @author louie.zheng
 */
public class SinoUploadFileBuilder {
    private static SinoUploadFileBuilder instance;
    private Log logger = LogFactory.getLog(this.getClass());
    private static String LocDir;
    private static String server;
    private static int port;
    private static String user;
    private static String pass;
    private static String SDSpath;
            
            
    public void PDFModel2Txt(String sentence1,String sentence2,String DataStringid){
        logger.info("Begin to print Sentence to txt...");
        TXTBuilder tBuilder=new TXTBuilder();
        tBuilder.Write2TXT(sentence1, LocDir, SDSpath+"/",DataStringid);
        tBuilder.Write2TXT(sentence2, LocDir, SDSpath+"/", DataStringid, "QA");
    }
    
        
    public ArrayList<String> TxtSenteneBuilder(String dataid){
        //SP020131226485Fnwyt 
        String sentence="";
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       String inputTime = sdf.format(new Date());
       System.out.printf("Your Date is = %s\n", inputTime);
       
       String dateString[]=inputTime.split("-");
        int yy = Integer.parseInt(dateString[0])-1911;
        String Stryy=String.valueOf(yy);
        String Strmm = dateString[1];
        String Strdd = dateString[2];
        System.out.println(Stryy);
        ArrayList<PdfModelTbE> entiTbEArrayList=new ArrayList();
        String Rsk10_Amt;
        String Rsk11_Amt;
        String Rsk12_Amt;
        int sum_101112;
        String Rsk09_Amt;
        String Rsk08_Amt;
        Object appYY;
        int sY;
        int sT;
        int sM;
        int sD;
        String appMM;
        String appDD;
        String AddDie;
        String AddDisable;
        Integer insage;
        String insYY="";
        String sentence2 = "";
        try {
            List<Map> resultsino = (ArrayList) QueryUtils36.getInstance().query("SELECT *  FROM [dbo].[GTL_TO_PDF_MODEL] where DataID=?", new Object[]{dataid});
               if(resultsino==null ){
                   logger.info("無資料");
               }else{ 
                   for(Map result:resultsino){ 
                            PdfModelTbE entiTbE=new PdfModelTbE();
                            entiTbE.setINSLIFETYPE("r");//1.壽險險別
                            entiTbE.setINSCOMPANY("06");//2.公司代號
                            entiTbE.setINSNAME((result.get("Insured_Nm")!=null)?result.get("Insured_Nm").toString():"");//3.被保險人姓名
                            entiTbE.setINSID((result.get("Insured_ID")!=null)?result.get("Insured_ID").toString():"");//4.被保險人ID
                            System.out.println("Insured_Bth: "+result.get("Insured_Bth").toString().split("/")[0]);         
                            System.out.println("Insured_Bth: "+result.get("Insured_Bth").toString().split("/")[1]);
                            System.out.println("Insured_Bth: "+result.get("Insured_Bth").toString().split("/")[2]);
                            if(result.get("Insured_Bth")!=null){
                                insYY=result.get("Insured_Bth").toString().split("/")[0];
                                insage=Integer.valueOf(Stryy)-Integer.valueOf(insYY);
                            }else{
                                insage=0;    
                            }
                            
                            entiTbE.setINSBIRTHYY(insYY.toString());//5-1.被保險人生日年
                            entiTbE.setINSBIRTHMM((result.get("Insured_Bth")!=null)?result.get("Insured_Bth").toString().split("/")[1]:"");//5-2.被保險人生日月
                            entiTbE.setINSBIRTHDD((result.get("Insured_Bth")!=null)?result.get("Insured_Bth").toString().split("/")[2]:"");//5-2.被保險人生日日
                            entiTbE.setINSSEX((result.get("Insured_Sex")!=null)?result.get("Insured_Sex").toString():"");//6.被保險人性別                 
                            entiTbE.setINSDATAID((result.get("DataID")!=null)?result.get("DataID").toString():"");//7.保單號碼                   
                            entiTbE.setINSPOLICYTYPE("2");//8.保單分類 
                            entiTbE.setINSPROCATE("2");//9.險種分類
                            entiTbE.setINSPRODUCT("13");//10.險種(商品)  
                               if(result.get("Rsk09_Amt")!=null && !result.get("Rsk09_Amt").toString().equals("")){
                                    
                                 if(insage>15){
                                        AddDie=Rsk09_Amt=result.get("Rsk09_Amt").toString().replace("萬", "0000");
                                        AddDisable="";
                                    }else{
                                        AddDisable=Rsk09_Amt=result.get("Rsk09_Amt").toString().replace("萬", "0000");
                                        AddDie="";
                                    }
                                }else{
                                        AddDisable="";
                                        AddDie="";
                                }                   
                            entiTbE.setINSCLAIMDIE(AddDie);//11.給付項目(身故)
                            entiTbE.setINSCLAIMDISABLEA(AddDisable);//12.給付項目(全殘)
                            entiTbE.setINSCLAIMDISABLEP("");//13.給付項目(殘廢扶助金) 
                            entiTbE.setINSCLAIMSPEMONY("");//14.給付項目(特定事故保險金)
                            entiTbE.setINSCLAIMFIRST("");//15.給付項目(初次罹患)
                                if(result.get("Rsk10_Amt")!=null && !result.get("Rsk10_Amt").toString().equals("")){
                                    Rsk10_Amt=result.get("Rsk10_Amt").toString().replace("萬", "0000");
                                }else{
                                    Rsk10_Amt="0";
                                }
                                if(result.get("Rsk11_Amt")!=null && !result.get("Rsk11_Amt").toString().equals("")){
                                    Rsk11_Amt=result.get("Rsk11_Amt").toString().replace("萬", "0000");
                                }else{
                                    Rsk11_Amt="0";
                                }
                                if(result.get("Rsk12_Amt")!=null && !result.get("Rsk12_Amt").toString().equals("")){
                                    Rsk12_Amt=result.get("Rsk12_Amt").toString().replace("萬", "0000");
                                }else{
                                    Rsk12_Amt="0";
                                }   
                            sum_101112=Integer.parseInt(Rsk10_Amt)+Integer.parseInt(Rsk11_Amt)+Integer.parseInt(Rsk12_Amt);
                            entiTbE.setINSCLAIMMED(String.valueOf(sum_101112));//16.給付項目(醫療限額)
                            entiTbE.setINSCLAIMMEDSELF("");//17.給付項目(醫療限額 自負額)
                            entiTbE.setINSCLAIMDAY("");//18.給付項目(日額)
                            entiTbE.setINSCLAIMSURGERYH("");//19.給付項目(住院手術) 
                            entiTbE.setINSCLAIMSURGERYN("");//20.給付項目(門診手術) 
                            entiTbE.setINSCLAIMCLINIC("");//21.給付項目(門診)
                            entiTbE.setINSCLAIMCRITICAL("");//22.給付項目(重大疾病(含特定傷病))
                                if(result.get("Rsk08_Amt")!=null && !result.get("Rsk08_Amt").toString().equals("")){
                                    Rsk08_Amt=result.get("Rsk08_Amt").toString().replace("萬", "0000");
                                }else{
                                    Rsk08_Amt="0";
                                }                              
                            entiTbE.setINSCLAIMCRIBURN(Rsk08_Amt);//23.給付項目(重大燒燙傷)
                            entiTbE.setINSCLAIMCANCER("");//24.給付項目(癌症療養)
                            entiTbE.setINSCLAIMRECOVER("");//25.給付項目(出院療養)
                            entiTbE.setINSCLAIMDISABLE("");//26.給付項目(失能)
                            entiTbE.setINSRECEIVEYY(Stryy);//27-1.收件日期 年
                            entiTbE.setINSRECEIVEMM(Strmm);//27-2.收件日期 月
                            entiTbE.setINSRECEIVEDD(Strdd);//27-3.收件日期 日
                            entiTbE.setINSCONTEXPIRYY((result.get("Ins_Exp_Dt_YY")!=null)?result.get("Ins_Exp_Dt_YY").toString():"");//28-1.契約滿期日期 年
                            entiTbE.setINSCONTEXPIRMM((result.get("Ins_Exp_Dt_MM")!=null)?result.get("Ins_Exp_Dt_MM").toString():"");//28-2.契約滿期日期 月
                            entiTbE.setINSCONTEXPIRDD((result.get("Ins_Exp_Dt_DD")!=null)?result.get("Ins_Exp_Dt_DD").toString():"");//28-3.契約滿期日期 日
                            entiTbE.setINSPRM((result.get("Insured_Nm")!=null)?result.get("Tot_Prm").toString():"");//29.保費
                            entiTbE.setINSPAYTYP("2");//30.保費繳別
                            entiTbE.setINSSTATUS("01");//31.保單狀況                     
//                            entiTbE.setINSSTATUSYY((result.get("Ins_Eff_Dt_YY")!=null)?result.get("Ins_Eff_Dt_YY").toString():"");//32-1.保單狀況生效日期 年
//                            entiTbE.setINSSTATUSMM((result.get("Ins_Eff_Dt_MM")!=null)?result.get("Ins_Eff_Dt_MM").toString():"");//32-2.保單狀況生效日期 月
//                            entiTbE.setINSSTATUSDD((result.get("Ins_Eff_Dt_DD")!=null)?result.get("Ins_Eff_Dt_DD").toString():"");//32-3.保單狀況生效日期 日
                            entiTbE.setINSSTATUSYY(Stryy);//32-1.保單狀況生效日期 年
                            entiTbE.setINSSTATUSMM(Strmm);//32-2.保單狀況生效日期 月
                            entiTbE.setINSSTATUSDD(Strdd);//32-3.保單狀況生效日期 日
                            entiTbE.setINSAPPNAME((result.get("Applicant_Nm")!=null)?result.get("Applicant_Nm").toString():"");//33.要保人姓名
                            entiTbE.setINSAPPID((result.get("Applicant_ID")!=null)?result.get("Applicant_ID").toString():"");//34.要保人ID
                            if(result.get("Applicant_Bth")!=null){
                                //民國53年05月19日　歲
                                
                                sY=result.get("Applicant_Bth").toString().indexOf("年");
                                sT=result.get("Applicant_Bth").toString().indexOf("國");
                                sM=result.get("Applicant_Bth").toString().indexOf("月");
                                sD=result.get("Applicant_Bth").toString().indexOf("日");
                                
                                appYY=result.get("Applicant_Bth").toString().substring(sT+1, sY);
                                appMM=result.get("Applicant_Bth").toString().substring(sY+1, sM);
                                appDD=result.get("Applicant_Bth").toString().substring(sM+1, sD);
                            }else{
                                appYY="";
                                appMM="";
                                appDD="";
                            }
                            
                            entiTbE.setINSAPPBIRYY(appYY.toString());//35-1.要保人出生日期 年
                            entiTbE.setINSAPPBIRMM(appMM.toString());//35-2.要保人出生日期 月
                            entiTbE.setINSAPPBIRDD(appDD.toString());//35-3.要保人出生日期 日
                            String rel="";
                            if(result.get("Applicant_Rel_CD")!=null){
                                
                                if(result.get("Applicant_Rel_CD").equals("01")){
                                    rel="01";
                                }else if(result.get("Applicant_Rel_CD").equals("02")){
                                    rel="02";
                                }else if(result.get("Applicant_Rel_CD").equals("03")){
                                    rel="03";
                                }else if(result.get("Applicant_Rel_CD").equals("04")){
                                    rel="04";
                                }
                            }else {
                                rel="05";
                            }
                            
                            entiTbE.setINSREALTION(rel);//36.要保人與被保險人關係
                            
                            entiTbEArrayList.add(entiTbE);
                   }
               }
           
            
        } catch (SQLException ex) {
            logger.error(ex);
            ex.printStackTrace();
        }
        

        for(PdfModelTbE enti:entiTbEArrayList){
            sentence+=enti.getINSLIFETYPE();
            sentence+=enti.getINSCOMPANY();
            sentence+=enti.getINSNAME();
            sentence+=enti.getINSID();
            sentence+=enti.getINSBIRTHYY();
            sentence+=enti.getINSBIRTHMM();
            sentence+=enti.getINSBIRTHDD();
            sentence+=enti.getINSSEX();
            sentence+=enti.getINSDATAID();
            sentence+=enti.getINSPOLICYTYPE();
            sentence+=enti.getINSPROCATE();
            sentence+=enti.getINSPRODUCT();
            sentence+=enti.getINSCLAIMDIE();
            sentence+=enti.getINSCLAIMDISABLEA();
            sentence+=enti.getINSCLAIMDISABLEP();
            sentence+=enti.getINSCLAIMSPEMONY();            
            sentence+=enti.getINSCLAIMFIRST();
            sentence+=enti.getINSCLAIMMED();
            System.out.println(enti.getINSCLAIMMED());
            sentence+=enti.getINSCLAIMMEDSELF();
            sentence+=enti.getINSCLAIMDAY();
            sentence+=enti.getINSCLAIMSURGERYH();
            sentence+=enti.getINSCLAIMSURGERYN();
            sentence+=enti.getINSCLAIMCLINIC();
            sentence+=enti.getINSCLAIMCRITICAL();
            sentence+=enti.getINSCLAIMCRIBURN();
            sentence+=enti.getINSCLAIMCANCER();
            sentence+=enti.getINSCLAIMRECOVER();
            sentence+=enti.getINSCLAIMDISABLE();
            sentence+=enti.getINSRECEIVEYY();
            sentence+=enti.getINSRECEIVEMM();
            sentence+=enti.getINSRECEIVEDD();
            sentence+=enti.getINSCONTEXPIRYY();
            sentence+=enti.getINSCONTEXPIRMM();
            sentence+=enti.getINSCONTEXPIRDD();
            sentence+=enti.getINSPRM();
            sentence+=enti.getINSPAYTYP();
            sentence+=enti.getINSSTATUS();
            sentence+=enti.getINSSTATUSYY();
            sentence+=enti.getINSSTATUSMM();
            sentence+=enti.getINSSTATUSDD();
            sentence+=enti.getINSAPPNAME();
            sentence+=enti.getINSAPPID();
            sentence+=enti.getINSAPPBIRYY();
            sentence+=enti.getINSAPPBIRMM();
            sentence+=enti.getINSAPPBIRDD();
            sentence+=enti.getINSREALTION();
            sentence+="\r\n";
            
            sentence2+=enti.getINSID();
            sentence2+="\r\n";
        }
       
        
        System.out.println(sentence);
        System.out.println(sentence2);
        logger.info(sentence);
        
        ArrayList<String> arrSentence=new ArrayList();
        arrSentence.add(sentence);        
        arrSentence.add(sentence2);
        return arrSentence;
    }
    
    public void UploadProcess(FTPClient ftpClient,String dir,String floder) throws FileNotFoundException, IOException{
            //UPLOAD PART1 ---start---
            int count=0;  
            UploadZurichFTP FileUp = new UploadZurichFTP();
            String oldFileDest =dir+floder+"/";
            String remoteFileDest =floder+"/";
            System.out.println("From: "+oldFileDest+"to: "+remoteFileDest);
            logger.info("From: "+oldFileDest+"to: "+remoteFileDest);
            
            File oldDir = new File(oldFileDest);//可以寫相對路徑。
            File[] files = oldDir.listFiles();//列出檔案     
            
            
            if(files != null && files.length > 0){
            
                for (File lists : files) {//列舉一種新的陣列寫法 意同 for(int i=0;i<files.length;i++){}                
                    String fname=lists.getName();
                    count++;
                    //System.out.println("ArrayList.."+"["+count+"]"+lists);
                    logger.info("ArrayList.."+"["+count+"]"+lists);
                           if(lists.isFile()){
                               System.out.println(dir+"/"+lists.getName());
                               logger.info(dir+"/"+lists.getName());
                                                              
                               /*
                                * main utility of upload modul //exe fTPUploadController
                                */
                               FileUp.setLocalFile(fname);//set file name to fTPUploadController
                               FileUp.controller(ftpClient,oldFileDest,remoteFileDest);
 

                               boolean isComplete = FileUp.isCompleted();
                               if(isComplete){    
                                   System.out.println("【UPLOAD】FINISH UPLOADING TO FTP<<<<<<<<<");
                                   logger.info("【UPLOAD】FINISH UPLOADING TO FTP<<<<<<<<<");  

                                   //DELETE PART AFTER UPLOAD FILEs
                                   File file = new File(oldFileDest+fname);

                                   if(file.delete()){
                                           System.out.println(file.getName() + " is deleted!");
                                           logger.info(file.getName() + " is deleted!");
                                   }else{
                                           System.out.println("Delete operation is failed.");
                                           logger.info("Delete operation is failed.");
                                   }                                
                               } 
                           }else {
                               System.out.println("【UPLOAD】Not .txt file: ................."
                                       + "/"+dir+"/"+lists.getName());
                               logger.info("【UPLOAD】Not .txt file: ................."
                                       + "/"+dir+"/"+lists.getName());
                           }
                }
            }else{
                     count++;
                    System.out.println(count+" Try:"+" No file");      
                    logger.info("count: "+count+" Try:"+" No file");
            }
            //UPLOAD PART ---start---           
    }   
    

    public void ProcessControl(){
        try {
           
            String server = this.server;
            int port = this.port;
            String user = this.user;
            String pass = this.pass;
            String LocDir = this.LocDir;
            String SDSpath =this.SDSpath;
            //System.out.println(server+":"+port+":"+user+":"+pass);  
            
            //FTP CONNECT PART 
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
            ftpClient.printWorkingDirectory();
            
            for(FTPFile f:ftpClient.listFiles()){
                
                System.out.println("Connected: "+f.getName());
            }
            logger.info("Intra FTP connnected: "+ftpClient.printWorkingDirectory());            
            
            UploadProcess(ftpClient,LocDir,SDSpath);   
            
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SinoUploadFileBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SinoUploadFileBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        //FTP CONNECT PART 

         //UploadProcess(ftpClient,dir,"RESP");  
        
    }
    
    public  void Builder(String data_idString){
        try {
            SinoUploadFileBuilder ssBuilder=new SinoUploadFileBuilder();
                Properties pt=new PropertiesTool().getProperties("conf.properties");
                ssBuilder.server = pt.getProperty("ftp.zurich.server").toString();
                ssBuilder.port = Integer.parseInt(pt.getProperty("ftp.zurich.port"));
                ssBuilder.user = pt.getProperty("ftp.zurich.user").toString();
                ssBuilder.pass = pt.getProperty("ftp.zurich.pass").toString();
                ssBuilder.LocDir = pt.getProperty("loc.zurich.path").toString();
                ssBuilder.SDSpath = pt.getProperty("ftp.zurich.folder3").toString();        
            
            ArrayList<String> senteces=ssBuilder.TxtSenteneBuilder(data_idString);
            
            ssBuilder.PDFModel2Txt(senteces.get(0),senteces.get(1),data_idString);
            ssBuilder.ProcessControl();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SinoUploadFileBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SinoUploadFileBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }  
    
    public static void main(String args[]){
        try {
            SinoUploadFileBuilder ssBuilder=new SinoUploadFileBuilder();
                Properties pt=new PropertiesTool().getProperties("conf.properties");
                ssBuilder.server = pt.getProperty("ftp.zurich.server").toString();
                ssBuilder.port = Integer.parseInt(pt.getProperty("ftp.zurich.port"));
                ssBuilder.user = pt.getProperty("ftp.zurich.user").toString();
                ssBuilder.pass = pt.getProperty("ftp.zurich.pass").toString();
                ssBuilder.LocDir = pt.getProperty("loc.zurich.path").toString();
                ssBuilder.SDSpath = pt.getProperty("ftp.zurich.folder3").toString();        
            
             ArrayList<String> senteces=ssBuilder.TxtSenteneBuilder("SP020150422pET482hj");
            ssBuilder.PDFModel2Txt(senteces.get(0),senteces.get(1),"SP020150422pET482hj");
            ssBuilder.ProcessControl();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SinoUploadFileBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SinoUploadFileBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }        
}
