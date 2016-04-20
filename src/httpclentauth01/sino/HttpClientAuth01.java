/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpclentauth01.sino;
import java.sql.*;
import com.zurich.sds.batch.utils.ConnectionFactory4Properties;
import com.zurich.sds.batch.utils.QueryUtils;
import com.zurich.sds.sino.utils.ConnectionFactory4Properties36;
import com.zurich.sds.sino.utils.HttpRequestUtils;
import com.zurich.sds.sino.utils.QueryUtils36;
import com.zurich.sds.sino.utils.Zip4SinoAES;
import com.zurich.sds.sino.utils.MailNote;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.activation.MimetypesFileTypeMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


//new
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;
import javax.jws.WebParam.Mode;
import org.apache.commons.io.FileUtils;//�NURL Response �����ରPDF���x�s
import org.apache.log4j.Logger;
import com.zurich.sds.sino.utils.PropertiesTool;
import httpclentauth01.sino.casnt.SinoUploadFileBuilder;

/**
 *
 * @author louie.zheng
 */
public class HttpClientAuth01 {

    private static final String CHAR_SET = "UTF-8";
    private static final String AUTH_STR = "zurich:zurich123";
    //private static final String GET_SINOPAC_ORDER_URL = "https://propertyins.sinopac.com/be/api/TravelPAOrderInfoDateJson";
    private static final String GET_SINOPAC_ORDER_URL = "http://211.76.157.13/be/api/TravelPAOrderInfoDateJson";//???
    //private static final String POST_SINOPAC_APPLICATION_FORM_URL = "https://propertyins.sinopac.com/be/api/InsUploadData";
    private static final String POST_SINOPAC_APPLICATION_FORM_URL = "http://211.76.157.13/be/api/InsUploadData";
    private static final int RETURN_SUCCESS = 200;
    private static final String ORDER_DATE_STR = "OrderDate";
    private static final String ORDER_SEQ_NO_STR = "Order_seq";    
    private static final String ORDER_SEQ_STR = "order_seq";
    private static final String CODE_ID_KEY_STR = "codeID";
    private static final String CODE_ID_VALUE_STR = "InsProposal";
    private static final String JSON_ROOT_STR = "TravelPA";//    private static final String JSON_ROOT_STR = "TravelPA";
    //private static final String PDF_MODEL_URL = "http://192.168.128.44:8080/PDFModel/VerifyAccount";
    //private static final String PDF_PARAMS_URL = "pdfreport=1&pdfreport=2&pdfreport=3&pdfreport=4&pdfreport=5&mode=7&modename=IZGTLAPP&passwords=passwords&datano=";
    //private static final String PDF_MODEL_URL = "http://192.168.128.44:8080/PDFModel/VerifyAccount";
    //private static final String PDF_MODEL_URL = "http://192.168.128.172:9080/PDFModel/VerifyAccount";
    private static final String PDF_MODEL_URL = "http://192.168.128.172:9980/PDFModel/VerifyAccount";//150313 PDFModel �w�q�쥻�� 44 �󴫨�s server �W�A�H�U���U���ҹ�������m�G
    private static final String PDF_PARAMS_URL = "pdfreport=2&mode=8&modename=SinoPac&passwords=passwords&datano=";    
    private static final String PDF_FILE_PATH = "\\\\192.168.128.44\\SDSPDocu\\";
    private static final String INSERT_GTL_ORDER_INFO_SQL = "insert into GTL_ORDER_INFO (create_dt, order_Str) values (?, ?) ";
    private static final String QUERY_GTL_TO_PDF_MODEL_SQL = "select DataID from GTL_TO_PDF_MODEL where DataID = ? ";
    private static final String QUERY_Cmpgn_Ref_Tb = "SELECT Cmpgn_Nm FROM Cmpgn_Ref_Tb";    
    private static final String INSERT_GTL_TO_PDF_MODEL_SQL = "INSERT INTO GTL_TO_PDF_MODEL (Apply_Clause,Apply_Typ,Insured_Item,Service_VoiceFax_Desc,DataID,Data_ID_VerNo,Recpt_No,Chl_CD,Chl_Nm,Cmpgn_CD,Cmpgn_Nm,Host_Proj_CD,Delegate_Nm,Agnt_CD,Issue_Brh_CD,Sales_CD,Data_ID,Paymnt_Mthd_CD,Recpt_No_Desc,Service_Tel_Desc,Service_Fax_Desc,Ins_Eff_Dt_YY,Ins_Eff_Dt_MM,Ins_Eff_Dt_DD,Ins_Eff_Dt_HH,Ins_Exp_Dt_YY,Ins_Exp_Dt_MM,Ins_Exp_Dt_DD,Ins_Exp_Dt_HH,Income_Dt,ApplyFm_TotPage,ApplyList_MK,Chl_Data_No,Applicant_Cust_No,TourArea_CD,TourPlace_Desc,TourCust_Cnt,Tour_Days,Tot_Prm,Applicant_Data_ID,Applicant_CustNo,Applicant_ID,Applicant_Sex,Applicant_Nm,Applicant_Bth,Applicant_Rel_CD,Applicant_Rel_Desc,Applicant_Insured_Rel_Desc,Applicant_Tel_O,Applicant_Tel_O_1,Applicant_Tel_O_2,Applicant_Tel_O_3,Cust_Tel_H,Applicant_Tel_H_1,Applicant_Tel_H_2,Applicant_Mobile,Applicant_Fax,Applicant_EMail,Applicant_ZipCD,Applicant_ZipCD_1,Applicant_ZipCD_2,Applicant_ZipCD_3,Applicant_ZipCD_4,Applicant_ZipCD_5,Applicant_Adrs_ZipDesc,Applicant_Adrs_EZipDesc,Applicant_Adrs,PA_Data_ID,PA_Data_ID_VerNo,PA_Recpt_No,PA_Itm_SeqNo,Host_Pkge_CD,EnCert_MK,Insured_Cust_No,Insured_Sex,Insured_ID,Insured_Nm,Insured_Bth,Insured_Benef,Insured_Job,Rsk01_Amt,Rsk02_Amt,Rsk05_Amt,Rsk06_Amt,Rsk07_Amt,Rsk08_Amt,Rsk09_Amt,Rsk10_Amt,Rsk11_Amt,Rsk12_Amt,Rsk13_Amt,Rsk14_Amt,Rsk15_Amt,Rsk16_Amt,Rsk17_Amt,Rsk18_Amt,Rsk19_Amt,Rsk20_Amt,Rsk21_Amt,Rsk22_Amt,Rsk23_Amt,Rsk24_Amt,Rsk25_Amt,Rsk01_MK,Rsk02_MK,Rsk05_MK,Rsk06_MK,Rsk07_MK,Rsk08_MK,Rsk13_MK,Rsk20_MK,Rsk21_MK,Rsk22_MK,Rsk23_MK,Rsk24_MK,Rsk25_MK,Per_Prm,Seq_No) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

    private Log log = LogFactory.getLog(this.getClass());
    private static Logger logger = Logger.getLogger(HttpClientAuth01.class);
    /**
     * �z�L http �� Pinopac ���o�q����
     * @return
     */
    private String getOrderInfo(Date orderDate) {
        try {
            String encodingStr = Base64.encodeBase64String(AUTH_STR.getBytes(CHAR_SET));
            //logger.info("encodingStr=" + encodingStr);
            HttpClient client = new HttpClient();
            PostMethod postMethod = new PostMethod(GET_SINOPAC_ORDER_URL);
            postMethod.setRequestHeader("Authorization", "Basic " + encodingStr);
            postMethod.addParameter(ORDER_DATE_STR, new SimpleDateFormat("MM/dd/yyyy").format(orderDate));
            // ��json����, �����B���ݭn
//            String orderDateStr = "{\"OrderDate\":\"10/02/2013\"}";
//            StringRequestEntity requestEntity = new StringRequestEntity(orderDateStr, "application/json", "UTF-8");
//            postMethod.setRequestEntity(requestEntity);
            int returnCode = client.executeMethod(postMethod);
            logger.info("Post TravelPAOrderInfoDateJson returnCode=" + returnCode + ", charSet=" + postMethod.getResponseCharSet());
            if (returnCode != RETURN_SUCCESS) {
                throw new RuntimeException("Error while TravelPAOrderInfoDateJson return code : " + returnCode);
            }

            String responseStr = postMethod.getResponseBodyAsString();
//            log.info(new String(responseStr.getBytes("ISO-8859-1"),"BIG5"));
            logger.info("responseStr=" + responseStr);
            return responseStr;
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    
    /**
     * �z�L http �� Pinopac ���o�q����
     * @return
     */
    private String getOrderInfoBySqNo(String orderSeq) {
        try {
            String encodingStr = Base64.encodeBase64String(AUTH_STR.getBytes(CHAR_SET));
            log.info("encodingStr=" + encodingStr);
            HttpClient client = new HttpClient();
            PostMethod postMethod = new PostMethod(GET_SINOPAC_ORDER_URL);
            postMethod.setRequestHeader("Authorization", "Basic " + encodingStr);
            //postMethod.addParameter(ORDER_SEQ_NO_STR,orderSeq.trim());
            
            
            String url=GET_SINOPAC_ORDER_URL+"?"+ORDER_SEQ_STR+"="+orderSeq;
            log.info("URL"+url);
            GetMethod getMethod = new GetMethod(url);
            getMethod.setRequestHeader("Authorization", "Basic " + encodingStr);
            // ��json����, �����B���ݭn
//            String orderDateStr = "{\"OrderDate\":\"10/02/2013\"}";
//            StringRequestEntity requestEntity = new StringRequestEntity(orderDateStr, "application/json", "UTF-8");
//            postMethod.setRequestEntity(requestEntity);
            int returnCode = client.executeMethod(getMethod);
            //int returnCodeG = client.executeMethod(getMethod);
            
            String responseStr = getMethod.getResponseBodyAsString();
            log.info("[Mark]Post TravelPAOrderInfoDateJson returnCode=" + returnCode + ", charSet=" + postMethod.getResponseCharSet());
            if (returnCode != RETURN_SUCCESS) {
                throw new RuntimeException("Error while TravelPAOrderInfoDateJson return code : " + returnCode);
            }else{
                return "Your are marked "+orderSeq+"return"+returnCode+","+"responseStr"+responseStr;
            }

            //String responseStr = postMethod.getResponseBodyAsString();
            //log.info(new String(responseStr.getBytes("ISO-8859-1"),"BIG5"));
            //log.info("responseStr=" + responseStr);
            
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }    
    
    /**
     * �N pinopac �ۦ� replace �����T�� JSON �r��� parse �� JSON
     * @param str
     * @return
     */
    private JSONObject parseStringToJSON(String str) {
        str = StringUtils.replace(str, "\\r\\n", "\r\n");
        str = StringUtils.replace(str, "\\\"", "\"");
        str = StringUtils.removeStart(str, "\"");
        str = StringUtils.removeEnd(str, "\"");
        str = "{\"" + JSON_ROOT_STR + "\":" + str + "}";
        log.info("after replace, str=" + str);
        JSONObject obj = JSONObject.fromObject(str);
        return obj;
    }

    private void insertGTLOrderInfo(String orderInfo) throws Exception {
        //��ƤӪ�, �����g
        //QueryUtils.getInstance().update(INSERT_GTL_ORDER_INFO_SQL, new Object[]{new Date(), orderInfo});
    }

    private boolean isExistsOrder(String orderSeq) throws Exception {//"select DataID from GTL_TO_PDF_MODEL where DataID = ? "
        Map result = QueryUtils.getInstance().querySingle(QUERY_GTL_TO_PDF_MODEL_SQL, new Object[]{orderSeq});
        if (result != null && !StringUtils.isEmpty((String) result.get("DataID"))) {
            return true;
        } else {
            return false;
        }
    }
//    
//    private String getCmpgn_CD(String PrdtSeq) throws Exception {//"select DataID from GTL_TO_PDF_MODEL where DataID = ? "
//        Map result2=QueryUtils36.getInstance().querySingle(QUERY_Cmpgn_Ref_Tb, new Object[]{PrdtSeq});//'AB06700001'
//        if (result2 != null && !StringUtils.isEmpty((String) result2.get("Cmpgn_Nm"))) {            
//            return result2.get("Cmpgn_Nm").toString();
//        } else {
//            return "false";
//        }        
//        
//        
//    }
    

    private void insertOrder(GTLToPdfBuilder builder) throws Exception {
        QueryUtils.getInstance().update(INSERT_GTL_TO_PDF_MODEL_SQL, builder.toObjectArray());
    }
    
    private double getFileSize(File file){
        double FileSize=0;
		//File file =new File("c:\\java_xml_logo.jpg");
 
		if(file.exists()){
 
			double bytes = file.length();
			double kilobytes = (bytes / 1024);
//			double megabytes = (kilobytes / 1024);
//			double gigabytes = (megabytes / 1024);
//			double terabytes = (gigabytes / 1024);
//			double petabytes = (terabytes / 1024);
//			double exabytes = (petabytes / 1024);
//			double zettabytes = (exabytes / 1024);
//			double yottabytes = (zettabytes / 1024);
 
			//System.out.println("bytes : " + bytes);
			System.out.println("kilobytes : " + kilobytes);
//			System.out.println("megabytes : " + megabytes);
//			System.out.println("gigabytes : " + gigabytes);
//			System.out.println("terabytes : " + terabytes);
//			System.out.println("petabytes : " + petabytes);
//			System.out.println("exabytes : " + exabytes);
//			System.out.println("zettabytes : " + zettabytes);
//			System.out.println("yottabytes : " + yottabytes);
                        FileSize=kilobytes;
		}else{
			 System.out.println("File does not exists!");
                         
		}        
        
        
        
        return FileSize;
    }
    

    private Boolean callPDFModel(String policySerial, String orderSeq, String incomeId) throws Exception {
        // TODO �Ѽƻݽվ�
        //String result = new HttpRequestUtils().httpPostRequest(PDF_MODEL_URL, PDF_PARAMS_URL + orderSeq + "&account=" + incomeId);
        Boolean PdfStatus=true;
        String uri = PDF_MODEL_URL+"?"+PDF_PARAMS_URL+orderSeq+"&account="+incomeId;
        log.info(uri);
        try{

        URL url = new URL(uri);
        File destination = new File("./Upload_tmp/"+orderSeq+".pdf");
        FileUtils.copyURLToFile(url, destination);
        if(getFileSize(destination)<10){
            PdfStatus=false;  
            log.info("�g�JPDF�ɮץ���--�ɮפj�p���`");
        }else{
            log.info("�g�JPDF�ɮצ��\");
        }

        }catch(Exception e) {
             log.info("�g�JPDF�ɮץ���--�t�β��`");
             PdfStatus=false; 
        }        
        
        return PdfStatus;
        
        
//        if ("OK".equals(result)) {
//            log.info("generate PDF OK, policySerial=" + policySerial + ",orderSeq=" + orderSeq);
//        } else {
//            log.info("generate PDF Fail, policySerial=" + policySerial + ",orderSeq=" + orderSeq + ",result=" + result);
//        }
    }

    public void postPDFFile(String policySerial, String orderSeq) throws Exception {
        String fileName = "./Upload_tmp/"+orderSeq+".zip";
//        String fileName = PDF_FILE_PATH + policySerial + "\\" + policySerial + ".pdf";
        File pdf = new File(fileName);
        if (!pdf.exists()) {
            throw new RuntimeException("file not found! " + fileName);
        }


        HttpClient client = new HttpClient();
        String encodingStr = Base64.encodeBase64String(AUTH_STR.getBytes(CHAR_SET));
        log.info("encodingStr=" + encodingStr);
        PostMethod postMethod = new PostMethod(POST_SINOPAC_APPLICATION_FORM_URL + "?" + CODE_ID_KEY_STR + "=" + CODE_ID_VALUE_STR);
        postMethod.setRequestHeader("Authorization", "Basic " + encodingStr);
        // �[�F�o�ǤϦ� server �ݷ|������ file
//        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//        postMethod.setRequestHeader("Content-Type", "application/octet-stream");
//        postMethod.setRequestHeader("Content-Type", "application/x-pdf");
//        postMethod.setRequestHeader("Content-Type", "application/pdf");
//        postMethod.setRequestHeader("Content-Type", "multipart/related");
//        postMethod.setRequestHeader("Content-Type", "image/gif");
//        String boundary = Long.toHexString(System.currentTimeMillis());
//        postMethod.setRequestHeader("Content-Type", "multipart/form-data;boundary=-----" + boundary);
//        postMethod.setRequestHeader("Content-Disposition", "form-data; name=\"aaa.pdf\"; filename=\""+orderSeq+".pdf\"");
        log.info("ContentType=" + new MimetypesFileTypeMap().getContentType(pdf));
        FilePart filePart = new FilePart(orderSeq + ".pdf", pdf, new MimetypesFileTypeMap().getContentType(pdf), "UTF-8");
        MultipartRequestEntity requestEntity = new MultipartRequestEntity(new Part[]{filePart}, postMethod.getParams());
        postMethod.setRequestEntity(requestEntity);
        int returnCode = client.executeMethod(postMethod);
        log.info("post InsUploadData, returnCode=" + returnCode);
        log.info(new String(postMethod.getResponseBodyAsString().getBytes("ISO-8859-1"), "BIG5"));
        if (returnCode != RETURN_SUCCESS) {
            throw new RuntimeException("Error while Post InsUploadData, return code : " + returnCode);
        }
    }

    synchronized public void process(Date orderDate, String incomeId) throws Exception {
        // �z�L http �� Pinopac ���o�q����
        //wait(20000);
         Boolean PDF_complete_flag;      

        String orderInfo;// = this.getOrderInfo(orderDate);
//        Map result = QueryUtils.getInstance().querySingle(QUERY_GTL_TO_PDF_MODEL_SQL, new Object[]{"111"});
//        List<Map> result2 = QueryUtils.getInstance().query(QUERY_Cmpgn_Ref_Tb, new Object[]{});
//        
//	for (int i = 0; i < result2.size(); i++) {
//		log.info(result2.get(i).get("Cmpgn_Nm").toString());
//	}            

        //String orderInfo = this.getOrderInfoBySqNo("00620130712lLisxKHy");
        //orderInfo="[\r\n  {\r\n    \"order_seq\": \"SP020140903Aqcbe85y\",\r\n    \"policy_serial\": \"20140903020617750\",\r\n    \"policy_status_code\": \"01\",\r\n    \"owner_id\": \"Q222901619\",\r\n    \"po_issue_date\": \"20140905\",\r\n    \"proj_no\": \"AB06700004\",\r\n    \"total_prem\": 1652.0,\r\n    \"method\": \"K\",\r\n    \"Modx\": \"12\",\r\n    \"po_send_date\": \"20140903\",\r\n    \"company_code\": \"006\",\r\n    \"channel\": null,\r\n    \"PolicyDetail\": [\r\n      {\r\n        \"policy_serial\": \"20140903020617750\",\r\n        \"coverage_No\": \"000\",\r\n        \"relation_owner\": \"1\",\r\n        \"ins_id\": \"Q222901619\",\r\n        \"age\": 35,\r\n        \"face_amt\": 8000000.0,\r\n        \"mode_prem\": 826.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20140905\",\r\n        \"issue_end_date\": \"20140910\",\r\n        \"issued_days\": 5,\r\n        \"issued_start_time\": \"4\",\r\n        \"issued_end_time\": \"4\",\r\n        \"isuued_sasia\": \"06\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"4\",\r\n        \"Bene_id\": null,\r\n        \"Bene_name\": \"���Q��\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20140903020617750\",\r\n        \"coverage_No\": \"001\",\r\n        \"relation_owner\": \"5\",\r\n        \"ins_id\": \"P122562637\",\r\n        \"age\": 36,\r\n        \"face_amt\": 8000000.0,\r\n        \"mode_prem\": 826.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20140905\",\r\n        \"issue_end_date\": \"20140910\",\r\n        \"issued_days\": 5,\r\n        \"issued_start_time\": \"4\",\r\n        \"issued_end_time\": \"4\",\r\n        \"isuued_sasia\": \"06\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"4\",\r\n        \"Bene_id\": null,\r\n        \"Bene_name\": \"�����s\"\r\n      }\r\n    ],\r\n    \"NAIN\": [\r\n      {\r\n        \"policy_serial\": \"20140903020617750\",\r\n        \"client_id\": \"Q222901619\",\r\n        \"name\": \"�d�{��\",\r\n        \"birth\": \"19790713\",\r\n        \"sex\": \"2\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0921564419\",\r\n        \"email1\": \"greenannefish@gmail.com\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"1\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20140903020617750\",\r\n        \"client_id\": \"Q222901619\",\r\n        \"name\": \"�d�{��\",\r\n        \"birth\": \"19790713\",\r\n        \"sex\": \"2\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0921564419\",\r\n        \"email1\": \"greenannefish@gmail.com\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20140903020617750\",\r\n        \"client_id\": \"P122562637\",\r\n        \"name\": \"���y��\",\r\n        \"birth\": \"19771019\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": null,\r\n        \"email1\": null,\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      }\r\n    ],\r\n    \"ADIN\": [\r\n      {\r\n        \"policy_serial\": \"20140903020617750\",\r\n        \"client_id\": \"Q222901619\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"606\",\r\n        \"address\": \"�Ÿq�����H�m�������Q�r��11��5��\",\r\n        \"Phone_home\": \"05-2534838\",\r\n        \"Phone_office\": \"05-2534838\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20140903020617750\",\r\n        \"client_id\": \"Q222901619\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"606\",\r\n        \"address\": \"�Ÿq�����H�m�������Q�r��11��5��\",\r\n        \"Phone_home\": \"05-2534838\",\r\n        \"Phone_office\": \"05-2534838\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20140903020617750\",\r\n        \"client_id\": \"P122562637\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"\",\r\n        \"address\": \"\",\r\n        \"Phone_home\": null,\r\n        \"Phone_office\": null\r\n      }\r\n    ],\r\n    \"Policy_No\": null,\r\n    \"Policy_issue_date\": null,\r\n    \"Policy_end_date\": null,\r\n    \"Policy_com_date\": null,\r\n    \"CreateDate\": \"20140903\",\r\n    \"payment_seq\": \"000459\"\r\n  },\r\n  {\r\n    \"order_seq\": \"SP020140903S3ptrmZ2\",\r\n    \"policy_serial\": \"20140903020852751\",\r\n    \"policy_status_code\": \"01\",\r\n    \"owner_id\": \"A120072950\",\r\n    \"po_issue_date\": \"20140905\",\r\n    \"proj_no\": \"AB06700004\",\r\n    \"total_prem\": 1247.0,\r\n    \"method\": \"K\",\r\n    \"Modx\": \"12\",\r\n    \"po_send_date\": \"20140903\",\r\n    \"company_code\": \"006\",\r\n    \"channel\": null,\r\n    \"PolicyDetail\": [\r\n      {\r\n        \"policy_serial\": \"20140903020852751\",\r\n        \"coverage_No\": \"000\",\r\n        \"relation_owner\": \"1\",\r\n        \"ins_id\": \"A120072950\",\r\n        \"age\": 55,\r\n        \"face_amt\": 10000000.0,\r\n        \"mode_prem\": 1247.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20140905\",\r\n        \"issue_end_date\": \"20140915\",\r\n        \"issued_days\": 10,\r\n        \"issued_start_time\": \"6\",\r\n        \"issued_end_time\": \"6\",\r\n        \"isuued_sasia\": \"08\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"6\",\r\n        \"Bene_id\": null,\r\n        \"Bene_name\": \"���߷O\"\r\n      }\r\n    ],\r\n    \"NAIN\": [\r\n      {\r\n        \"policy_serial\": \"20140903020852751\",\r\n        \"client_id\": \"A120072950\",\r\n        \"name\": \"������\",\r\n        \"birth\": \"19581213\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0988566186\",\r\n        \"email1\": \"aimimic@gmail.com\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"1\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20140903020852751\",\r\n        \"client_id\": \"A120072950\",\r\n        \"name\": \"������\",\r\n        \"birth\": \"19581213\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0988566186\",\r\n        \"email1\": \"aimimic@gmail.com\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      }\r\n    ],\r\n    \"ADIN\": [\r\n      {\r\n        \"policy_serial\": \"20140903020852751\",\r\n        \"client_id\": \"A120072950\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"114\",\r\n        \"address\": \"�O�_������Ϸs����380��9��18���T��\",\r\n        \"Phone_home\": \" - \",\r\n        \"Phone_office\": \"02-27916202\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20140903020852751\",\r\n        \"client_id\": \"A120072950\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"114\",\r\n        \"address\": \"�O�_������Ϸs����380��9��18���T��\",\r\n        \"Phone_home\": \" - \",\r\n        \"Phone_office\": \"02-27916202\"\r\n      }\r\n    ],\r\n    \"Policy_No\": null,\r\n    \"Policy_issue_date\": null,\r\n    \"Policy_end_date\": null,\r\n    \"Policy_com_date\": null,\r\n    \"CreateDate\": \"20140903\",\r\n    \"payment_seq\": \"000464\"\r\n  }\r\n]";
//orderInfo="[\r\n  {\r\n    \"order_seq\": \"SP020140507esUbga6u\",\r\n    \"policy_serial\": \"20140507104649312\",\r\n    \"policy_status_code\": \"01\",\r\n    \"owner_id\": \"T221448867\",\r\n    \"po_issue_date\": \"20140514\",\r\n    \"proj_no\": \"AB06700004\",\r\n    \"total_prem\": 483.0,\r\n    \"method\": \"K\",\r\n    \"Modx\": \"12\",\r\n    \"po_send_date\": \"20140507\",\r\n    \"company_code\": \"006\",\r\n    \"channel\": null,\r\n    \"PolicyDetail\": [\r\n      {\r\n        \"policy_serial\": \"20140507104649340\",\r\n        \"coverage_No\": \"000\",\r\n        \"relation_owner\": \"1\",\r\n        \"ins_id\": \"T221448867\",\r\n        \"age\": 41,\r\n        \"face_amt\": 3000000.0,\r\n        \"mode_prem\": 483.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20140514\",\r\n        \"issue_end_date\": \"20140519\",\r\n        \"issued_days\": 5,\r\n        \"issued_start_time\": \"6\",\r\n        \"issued_end_time\": \"6\",\r\n        \"isuued_sasia\": \"06\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"6\",\r\n        \"Bene_id\": null,\r\n        \"Bene_name\": \"�\�صX\"\r\n      }\r\n    ],\r\n    \"NAIN\": [\r\n      {\r\n        \"policy_serial\": \"20140507104649340\",\r\n        \"client_id\": \"T221448867\",\r\n        \"name\": \"�\�ؾ�\",\r\n        \"birth\": \"19721011\",\r\n        \"sex\": \"2\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0928491781\",\r\n        \"email1\": \"960547@ms.kmuh.org.tw\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"1\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20140507104649340\",\r\n        \"client_id\": \"T221448867\",\r\n        \"name\": \"�\�ؾ�\",\r\n        \"birth\": \"19721011\",\r\n        \"sex\": \"2\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0928491781\",\r\n        \"email1\": \"960547@ms.kmuh.org.tw\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      }\r\n    ],\r\n    \"ADIN\": [\r\n      {\r\n        \"policy_serial\": \"20140507104649340\",\r\n        \"client_id\": \"T221448867\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"807\",\r\n        \"address\": \"�������T���ϴ¶���31��\",\r\n        \"Phone_home\": \"07-3221261\",\r\n        \"Phone_office\": null\r\n      },\r\n      {\r\n        \"policy_serial\": \"20140507104649340\",\r\n        \"client_id\": \"T221448867\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"807\",\r\n        \"address\": \"�������T���ϴ¶���31��\",\r\n        \"Phone_home\": \"07-3221261\",\r\n        \"Phone_office\": null\r\n      }\r\n    ],\r\n    \"Policy_No\": null,\r\n    \"Policy_issue_date\": null,\r\n    \"Policy_end_date\": null,\r\n    \"Policy_com_date\": null,\r\n    \"CreateDate\": \"20140507\",\r\n    \"payment_seq\": \"004980\"\r\n  }\r\n]";
        orderInfo="[\r\n  {\r\n    \"order_seq\": \"SP0201504234UYNflMH\",\r\n    \"policy_serial\": \"201504230009461658\",\r\n    \"policy_status_code\": \"01\",\r\n    \"owner_id\": \"A124544822\",\r\n    \"po_issue_date\": \"20150423\",\r\n    \"proj_no\": \"AB06700004\",\r\n    \"total_prem\": 959.0,\r\n    \"method\": \"K\",\r\n    \"Modx\": \"12\",\r\n    \"po_send_date\": \"20150423\",\r\n    \"company_code\": \"006\",\r\n    \"channel\": null,\r\n    \"PolicyDetail\": [\r\n      {\r\n        \"policy_serial\": \"201504230009461658\",\r\n        \"coverage_No\": \"000\",\r\n        \"relation_owner\": \"1\",\r\n        \"ins_id\": \"A124544822\",\r\n        \"age\": 36,\r\n        \"face_amt\": 10000000.0,\r\n        \"mode_prem\": 959.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20150423\",\r\n        \"issue_end_date\": \"20150428\",\r\n        \"issued_days\": 5,\r\n        \"issued_start_time\": \"5\",\r\n        \"issued_end_time\": \"5\",\r\n        \"isuued_sasia\": \"06\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"4\",\r\n        \"Bene_id\": null,\r\n        \"Bene_name\": \"����ǡB���\��\"\r\n      }\r\n    ],\r\n    \"NAIN\": [\r\n      {\r\n        \"policy_serial\": \"201504230009461658\",\r\n        \"client_id\": \"A124544822\",\r\n        \"name\": \"�����k\",\r\n        \"birth\": \"19780520\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0933211236\",\r\n        \"email1\": \"gdiwstw@gmail.com\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"1\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"201504230009461658\",\r\n        \"client_id\": \"A124544822\",\r\n        \"name\": \"�����k\",\r\n        \"birth\": \"19780520\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0933211236\",\r\n        \"email1\": \"gdiwstw@gmail.com\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      }\r\n    ],\r\n    \"ADIN\": [\r\n      {\r\n        \"policy_serial\": \"201504230009461658\",\r\n        \"client_id\": \"A124544822\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"247\",\r\n        \"address\": \"�s�_��Ī�w�ϥæw�_���G�q54��3��6��\",\r\n        \"Phone_home\": \"02-82835766\",\r\n        \"Phone_office\": \"02-82835766\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"201504230009461658\",\r\n        \"client_id\": \"A124544822\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"247\",\r\n        \"address\": \"�s�_��Ī�w�ϥæw�_���G�q54��3��6��\",\r\n        \"Phone_home\": \"02-82835766\",\r\n        \"Phone_office\": \"02-82835766\"\r\n      }\r\n    ],\r\n    \"Policy_No\": null,\r\n    \"Policy_issue_date\": null,\r\n    \"Policy_end_date\": null,\r\n    \"Policy_com_date\": null,\r\n    \"CreateDate\": \"20150423\",\r\n    \"payment_seq\": \"014714\",\r\n    \"traveldest\": \"06\"\r\n  },\r\n  {\r\n    \"order_seq\": \"SP0201504236Dr7gn4z\",\r\n    \"policy_serial\": \"201504230132341659\",\r\n    \"policy_status_code\": \"01\",\r\n    \"owner_id\": \"U121536736\",\r\n    \"po_issue_date\": \"20150427\",\r\n    \"proj_no\": \"AB06700004\",\r\n    \"total_prem\": 1335.0,\r\n    \"method\": \"K\",\r\n    \"Modx\": \"12\",\r\n    \"po_send_date\": \"20150423\",\r\n    \"company_code\": \"006\",\r\n    \"channel\": null,\r\n    \"PolicyDetail\": [\r\n      {\r\n        \"policy_serial\": \"201504230132341659\",\r\n        \"coverage_No\": \"000\",\r\n        \"relation_owner\": \"1\",\r\n        \"ins_id\": \"U121536736\",\r\n        \"age\": 27,\r\n        \"face_amt\": 10000000.0,\r\n        \"mode_prem\": 1335.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20150427\",\r\n        \"issue_end_date\": \"20150509\",\r\n        \"issued_days\": 12,\r\n        \"issued_start_time\": \"15\",\r\n        \"issued_end_time\": \"15\",\r\n        \"isuued_sasia\": \"11\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"4\",\r\n        \"Bene_id\": null,\r\n        \"Bene_name\": \"�x�ۤs\"\r\n      }\r\n    ],\r\n    \"NAIN\": [\r\n      {\r\n        \"policy_serial\": \"201504230132341659\",\r\n        \"client_id\": \"U121536736\",\r\n        \"name\": \"�x����\",\r\n        \"birth\": \"19870606\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0932003228\",\r\n        \"email1\": \"h0304126@hotmail.com\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"1\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"201504230132341659\",\r\n        \"client_id\": \"U121536736\",\r\n        \"name\": \"�x����\",\r\n        \"birth\": \"19870606\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0932003228\",\r\n        \"email1\": \"h0304126@hotmail.com\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      }\r\n    ],\r\n    \"ADIN\": [\r\n      {\r\n        \"policy_serial\": \"201504230132341659\",\r\n        \"client_id\": \"U121536736\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"242\",\r\n        \"address\": \"�s�_���s���ϥ|����97��10��4��\",\r\n        \"Phone_home\": \"02-22042820\",\r\n        \"Phone_office\": \"02-22042820\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"201504230132341659\",\r\n        \"client_id\": \"U121536736\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"242\",\r\n        \"address\": \"�s�_���s���ϥ|����97��10��4��\",\r\n        \"Phone_home\": \"02-22042820\",\r\n        \"Phone_office\": \"02-22042820\"\r\n      }\r\n    ],\r\n    \"Policy_No\": null,\r\n    \"Policy_issue_date\": null,\r\n    \"Policy_end_date\": null,\r\n    \"Policy_com_date\": null,\r\n    \"CreateDate\": \"20150423\",\r\n    \"payment_seq\": \"F89933\",\r\n    \"traveldest\": \"11\"\r\n  },\r\n  {\r\n    \"order_seq\": \"SP0201504231dvxkbHn\",\r\n    \"policy_serial\": \"201504230316131660\",\r\n    \"policy_status_code\": \"01\",\r\n    \"owner_id\": \"A128873691\",\r\n    \"po_issue_date\": \"20150426\",\r\n    \"proj_no\": \"AB06700003\",\r\n    \"total_prem\": 656.0,\r\n    \"method\": \"K\",\r\n    \"Modx\": \"12\",\r\n    \"po_send_date\": \"20150423\",\r\n    \"company_code\": \"006\",\r\n    \"channel\": null,\r\n    \"PolicyDetail\": [\r\n      {\r\n        \"policy_serial\": \"201504230316131660\",\r\n        \"coverage_No\": \"000\",\r\n        \"relation_owner\": \"1\",\r\n        \"ins_id\": \"A128873691\",\r\n        \"age\": 29,\r\n        \"face_amt\": 1000000.0,\r\n        \"mode_prem\": 656.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20150426\",\r\n        \"issue_end_date\": \"20150621\",\r\n        \"issued_days\": 56,\r\n        \"issued_start_time\": \"0\",\r\n        \"issued_end_time\": \"0\",\r\n        \"isuued_sasia\": \"07\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"8\",\r\n        \"Bene_id\": \"L00000000000\",\r\n        \"Bene_name\": \"\"\r\n      }\r\n    ],\r\n    \"NAIN\": [\r\n      {\r\n        \"policy_serial\": \"201504230316131660\",\r\n        \"client_id\": \"A128873691\",\r\n        \"name\": \"���R�a\",\r\n        \"birth\": \"19851117\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0975796774\",\r\n        \"email1\": \"a071221@yahoo.com.tw\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"1\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"201504230316131660\",\r\n        \"client_id\": \"A128873691\",\r\n        \"name\": \"���R�a\",\r\n        \"birth\": \"19851117\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0975796774\",\r\n        \"email1\": \"a071221@yahoo.com.tw\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      }\r\n    ],\r\n    \"ADIN\": [\r\n      {\r\n        \"policy_serial\": \"201504230316131660\",\r\n        \"client_id\": \"A128873691\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"114\",\r\n        \"address\": \"�O�_������ϫn�ʪF�����q322��\",\r\n        \"Phone_home\": \"02-27943773\",\r\n        \"Phone_office\": \"02-27926849\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"201504230316131660\",\r\n        \"client_id\": \"A128873691\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"114\",\r\n        \"address\": \"�O�_������ϫn�ʪF�����q322��\",\r\n        \"Phone_home\": \"02-27943773\",\r\n        \"Phone_office\": \"02-27926849\"\r\n      }\r\n    ],\r\n    \"Policy_No\": null,\r\n    \"Policy_issue_date\": null,\r\n    \"Policy_end_date\": null,\r\n    \"Policy_com_date\": null,\r\n    \"CreateDate\": \"20150423\",\r\n    \"payment_seq\": \"000732\",\r\n    \"traveldest\": \"06\"\r\n  }\r\n]";
        //orderInfo="[\r\n {\r\n \"order_seq\":\"SP020140905wzMoJI75\",\"policy_serial\":\"20140905102222457\",\"policy_status_code\":\"01\",\"owner_id\":\"N125002262\",\"po_issue_date\":\"20140905\",\"proj_no\":\"AB06700004\",\"total_prem\":792,\"method\":\"K\",\"Modx\":\"12\",\"po_send_date\":\"20140905\",\"company_code\":\"006\",\"channel\":null,\"PolicyDetail\":[{\"policy_serial\":\"20140905102222457\",\"coverage_No\":\"000\",\"relation_owner\":\"1\",\"ins_id\":\"N125002262\",\"age\":26,\"face_amt\":3000000,\"mode_prem\":396,\"coverage_type\":\"2\",\"issue_str_date\":\"20140905\",\"issue_end_date\":\"20140909\",\"issued_days\":4,\"issued_start_time\":\"15\",\"issued_end_time\":\"15\",\"isuued_sasia\":\"06\",\"English_Name\":null,\"Passport_no\":null,\"relation\":\"6\",\"Bene_id\":null,\"Bene_name\":\"�L�ѻ�\"},{\"policy_serial\":\"20140905102222457\",\"coverage_No\":\"001\",\"relation_owner\":\"2\",\"ins_id\":\"A117416813\",\"age\":20,\"face_amt\":3000000,\"mode_prem\":396,\"coverage_type\":\"2\",\"issue_str_date\":\"20140905\",\"issue_end_date\":\"20140909\",\"issued_days\":4,\"issued_start_time\":\"15\",\"issued_end_time\":\"15\",\"isuued_sasia\":\"06\",\"English_Name\":null,\"Passport_no\":null,\"relation\":\"3\",\"Bene_id\":null,\"Bene_name\":\"majaja\"}],\"NAIN\":[{\"policy_serial\":\"20140905102222457\",\"client_id\":\"N125002262\",\"name\":\"������\",\"birth\":\"19880425\",\"sex\":\"1\",\"occupation_code\":null,\"occupation_letter\":null,\"marriage\":null,\"education\":null,\"cellur_phone_no\":\"0928545703\",\"email1\":\"roychen@sinopac.com\",\"email2\":null,\"Body_type\":null,\"Id_type\":\"1\"},{\"policy_serial\":\"20140905102222457\",\"client_id\":\"N125002262\",\"name\":\"������\",\"birth\":\"19880425\",\"sex\":\"1\",\"occupation_code\":null,\"occupation_letter\":null,\"marriage\":null,\"education\":null,\"cellur_phone_no\":\"0928545703\",\"email1\":\"roychen@sinopac.com\",\"email2\":null,\"Body_type\":null,\"Id_type\":\"2\"},{\"policy_serial\":\"20140905102222457\",\"client_id\":\"A117416813\",\"name\":\"����\",\"birth\":\"19940614\",\"sex\":\"1\",\"occupation_code\":null,\"occupation_letter\":null,\"marriage\":null,\"education\":null,\"cellur_phone_no\":null,\"email1\":null,\"email2\":null,\"Body_type\":null,\"Id_type\":\"2\"}],\"ADIN\":[{\"policy_serial\":\"20140905102222457\",\"client_id\":\"N125002262\",\"Address_type\":\"4\",\"Zip\":\"505\",\"address\":\"���ƿ��������s��287��\",\"Phone_home\":\"02-21835782\",\"Phone_office\":\"04-7764138\"},{\"policy_serial\":\"20140905102222457\",\"client_id\":\"N125002262\",\"Address_type\":\"4\",\"Zip\":\"505\",\"address\":\"���ƿ��������s��287��\",\"Phone_home\":\"02-21835782\",\"Phone_office\":\"04-7764138\"},{\"policy_serial\":\"20140905102222457\",\"client_id\":\"A117416813\",\"Address_type\":\"4\",\"Zip\":\"\",\"address\":\"\",\"Phone_home\":null,\"Phone_office\":null}],\"Policy_No\":null,\"Policy_issue_date\":null,\"Policy_end_date\":null,\"Policy_com_date\":null,\"CreateDate\":\"20140905\",\"payment_seq\":\"AB1234\"}\r\n]";
        //orderInfo="[\r\n  {\r\n    \"order_seq\": \"SP020141006SQeil5Tq\",\r\n    \"policy_serial\": \"20141006192520900\",\r\n    \"policy_status_code\": \"01\",\r\n    \"owner_id\": \"J122155147\",\r\n    \"po_issue_date\": \"20141012\",\r\n    \"proj_no\": \"AB06700002\",\r\n    \"total_prem\": 420.0,\r\n    \"method\": \"K\",\r\n    \"Modx\": \"12\",\r\n    \"po_send_date\": \"20141006\",\r\n    \"company_code\": \"006\",\r\n    \"channel\": null,\r\n    \"PolicyDetail\": [\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"coverage_No\": \"000\",\r\n        \"relation_owner\": \"1\",\r\n        \"ins_id\": \"J122155147\",\r\n        \"age\": 32,\r\n        \"face_amt\": 2000000.0,\r\n        \"mode_prem\": 74.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20141012\",\r\n        \"issue_end_date\": \"20141014\",\r\n        \"issued_days\": 2,\r\n        \"issued_start_time\": \"6\",\r\n        \"issued_end_time\": \"6\",\r\n        \"isuued_sasia\": \"01\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"8\",\r\n        \"Bene_id\": \"L00000000000\",\r\n        \"Bene_name\": \"\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"coverage_No\": \"001\",\r\n        \"relation_owner\": \"4\",\r\n        \"ins_id\": \"J102639919\",\r\n        \"age\": 59,\r\n        \"face_amt\": 2000000.0,\r\n        \"mode_prem\": 74.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20141012\",\r\n        \"issue_end_date\": \"20141014\",\r\n        \"issued_days\": 2,\r\n        \"issued_start_time\": \"6\",\r\n        \"issued_end_time\": \"6\",\r\n        \"isuued_sasia\": \"01\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"8\",\r\n        \"Bene_id\": \"L00000000000\",\r\n        \"Bene_name\": \"\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"coverage_No\": \"002\",\r\n        \"relation_owner\": \"4\",\r\n        \"ins_id\": \"M220223735\",\r\n        \"age\": 55,\r\n        \"face_amt\": 2000000.0,\r\n        \"mode_prem\": 74.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20141012\",\r\n        \"issue_end_date\": \"20141014\",\r\n        \"issued_days\": 2,\r\n        \"issued_start_time\": \"6\",\r\n        \"issued_end_time\": \"6\",\r\n        \"isuued_sasia\": \"01\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"8\",\r\n        \"Bene_id\": \"L00000000000\",\r\n        \"Bene_name\": \"\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"coverage_No\": \"003\",\r\n        \"relation_owner\": \"3\",\r\n        \"ins_id\": \"A132626128\",\r\n        \"age\": 2,\r\n        \"face_amt\": 2000000.0,\r\n        \"mode_prem\": 25.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20141012\",\r\n        \"issue_end_date\": \"20141014\",\r\n        \"issued_days\": 2,\r\n        \"issued_start_time\": \"6\",\r\n        \"issued_end_time\": \"6\",\r\n        \"isuued_sasia\": \"01\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"8\",\r\n        \"Bene_id\": \"L00000000000\",\r\n        \"Bene_name\": \"\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"coverage_No\": \"004\",\r\n        \"relation_owner\": \"3\",\r\n        \"ins_id\": \"A232159222\",\r\n        \"age\": 0,\r\n        \"face_amt\": 2000000.0,\r\n        \"mode_prem\": 25.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20141012\",\r\n        \"issue_end_date\": \"20141014\",\r\n        \"issued_days\": 2,\r\n        \"issued_start_time\": \"6\",\r\n        \"issued_end_time\": \"6\",\r\n        \"isuued_sasia\": \"01\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"8\",\r\n        \"Bene_id\": \"L00000000000\",\r\n        \"Bene_name\": \"\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"coverage_No\": \"005\",\r\n        \"relation_owner\": \"5\",\r\n        \"ins_id\": \"N102825543\",\r\n        \"age\": 67,\r\n        \"face_amt\": 2000000.0,\r\n        \"mode_prem\": 74.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20141012\",\r\n        \"issue_end_date\": \"20141014\",\r\n        \"issued_days\": 2,\r\n        \"issued_start_time\": \"6\",\r\n        \"issued_end_time\": \"6\",\r\n        \"isuued_sasia\": \"01\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"8\",\r\n        \"Bene_id\": \"L00000000000\",\r\n        \"Bene_name\": \"\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"coverage_No\": \"006\",\r\n        \"relation_owner\": \"5\",\r\n        \"ins_id\": \"F200736192\",\r\n        \"age\": 64,\r\n        \"face_amt\": 2000000.0,\r\n        \"mode_prem\": 74.0,\r\n        \"coverage_type\": \"2\",\r\n        \"issue_str_date\": \"20141012\",\r\n        \"issue_end_date\": \"20141014\",\r\n        \"issued_days\": 2,\r\n        \"issued_start_time\": \"6\",\r\n        \"issued_end_time\": \"6\",\r\n        \"isuued_sasia\": \"01\",\r\n        \"English_Name\": null,\r\n        \"Passport_no\": null,\r\n        \"relation\": \"8\",\r\n        \"Bene_id\": \"L00000000000\",\r\n        \"Bene_name\": \"\"\r\n      }\r\n    ],\r\n    \"NAIN\": [\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"J122155147\",\r\n        \"name\": \"�^�˽n\",\r\n        \"birth\": \"19820327\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0953965355\",\r\n        \"email1\": \"mars.peng@lhic.com.tw\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"1\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"J122155147\",\r\n        \"name\": \"�^�˽n\",\r\n        \"birth\": \"19820327\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": \"0953965355\",\r\n        \"email1\": \"mars.peng@lhic.com.tw\",\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"J102639919\",\r\n        \"name\": \"�^�Ǯi\",\r\n        \"birth\": \"19550216\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": null,\r\n        \"email1\": null,\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"M220223735\",\r\n        \"name\": \"�}����\",\r\n        \"birth\": \"19590925\",\r\n        \"sex\": \"2\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": null,\r\n        \"email1\": null,\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"A132626128\",\r\n        \"name\": \"�^�~��\",\r\n        \"birth\": \"20120509\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": null,\r\n        \"email1\": null,\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"A232159222\",\r\n        \"name\": \"�^ˡ޷\",\r\n        \"birth\": \"20140302\",\r\n        \"sex\": \"2\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": null,\r\n        \"email1\": null,\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"N102825543\",\r\n        \"name\": \"Ĭ����\",\r\n        \"birth\": \"19470801\",\r\n        \"sex\": \"1\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": null,\r\n        \"email1\": null,\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"F200736192\",\r\n        \"name\": \"������\",\r\n        \"birth\": \"19491103\",\r\n        \"sex\": \"2\",\r\n        \"occupation_code\": null,\r\n        \"occupation_letter\": null,\r\n        \"marriage\": null,\r\n        \"education\": null,\r\n        \"cellur_phone_no\": null,\r\n        \"email1\": null,\r\n        \"email2\": null,\r\n        \"Body_type\": null,\r\n        \"Id_type\": \"2\"\r\n      }\r\n    ],\r\n    \"ADIN\": [\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"J122155147\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"220\",\r\n        \"address\": \"�s�_���O���Ϸ˱X�G��106��18��\",\r\n        \"Phone_home\": \"-\",\r\n        \"Phone_office\": \"02-26876855\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"J122155147\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"220\",\r\n        \"address\": \"�s�_���O���Ϸ˱X�G��106��18��\",\r\n        \"Phone_home\": \"-\",\r\n        \"Phone_office\": \"02-26876855\"\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"J102639919\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"\",\r\n        \"address\": \"\",\r\n        \"Phone_home\": null,\r\n        \"Phone_office\": null\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"M220223735\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"\",\r\n        \"address\": \"\",\r\n        \"Phone_home\": null,\r\n        \"Phone_office\": null\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"A132626128\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"\",\r\n        \"address\": \"\",\r\n        \"Phone_home\": null,\r\n        \"Phone_office\": null\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"A232159222\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"\",\r\n        \"address\": \"\",\r\n        \"Phone_home\": null,\r\n        \"Phone_office\": null\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"N102825543\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"\",\r\n        \"address\": \"\",\r\n        \"Phone_home\": null,\r\n        \"Phone_office\": null\r\n      },\r\n      {\r\n        \"policy_serial\": \"20141006192520900\",\r\n        \"client_id\": \"F200736192\",\r\n        \"Address_type\": \"4\",\r\n        \"Zip\": \"\",\r\n        \"address\": \"\",\r\n        \"Phone_home\": null,\r\n        \"Phone_office\": null\r\n      }\r\n    ],\r\n    \"Policy_No\": null,\r\n    \"Policy_issue_date\": null,\r\n    \"Policy_end_date\": null,\r\n    \"Policy_com_date\": null,\r\n    \"CreateDate\": \"20141006\",\r\n    \"payment_seq\": \"003318\"\r\n  }\r\n]";
        // �g�J��Ʈw
        this.insertGTLOrderInfo(orderInfo);
        // �N pinopac �ۦ� replace �����T�� JSON �r��� parse
        JSONObject json = this.parseStringToJSON(orderInfo);
        // �N json ��Ѧ��� GTL pdf �ݭn�����, �üg�J��Ʈw
        JSONArray policies = json.getJSONArray(JSON_ROOT_STR);

        //GTLToPdfBuilder builder1 = new GTLToPdfJsonBuilder();                    
        //this.insertOrder(builder1);
        // �z�L http �� PDF MODEL ���� PDF
        //this.callPDFModel("SDS13121800005", "SDS13121800005", incomeId);//TEST               
        //INSERT DATA PART-----------------------------------------------------------
            for (int i = 0; i < policies.size(); i++) {
                JSONObject policy = (JSONObject) policies.get(i);
                logger.info("policy[" + i + "]=" + policy);
                System.out.printf("policy[" + i + "]=" + policy);
                
                String policySerial = policy.getString("policy_serial");
                
                String orderSeq = policy.getString(ORDER_SEQ_STR);//order_seq
                logger.info("orderSeq=**************" + orderSeq);
                String owner_id = policy.getString("owner_id");//order_seq
                logger.info("owner_id=**************" + owner_id);                
                 //�קK���׭��ưe�w�ǹL���O��
                  
                if (this.isExistsOrder(orderSeq)) {//����mark 
                    logger.info("��ƭ���Data duplicate");
                    
                    //String markMSG=this.getOrderInfoBySqNo(orderSeq);//�����׵��O�hmark���w�ǰe���
                    try {
                            PDF_complete_flag=this.callPDFModel(policySerial, orderSeq, incomeId);
                            if(PDF_complete_flag){//140923�令�YPDF���ѡA�h�|�H�H�q���A�ä�������ZIP���ʧ@
                                log.info("�g�JPDF�ɮק����A�ǳƤW��");
                                MailNote mailS=new MailNote();
                                mailS.mailServer(true,orderSeq);   
                                 // ��[�Kzip
                                Zip4SinoAES zsinoAES=new Zip4SinoAES();
                                zsinoAES.setId4zip(owner_id.trim());
                                zsinoAES.Process(orderSeq.trim());


                                // Ū���ɮ�, �z�L http �^�� Sinopac
//                                this.postPDFFile(policySerial, orderSeq);//���״��ռȮɤ��W�Ǫ��A


//                              String markMSG=this.getOrderInfoBySqNo(orderSeq);//�����׵��O�hmark���w�ǰe���
//                                log.info(markMSG);      
                           SinoUploadFileBuilder sufb=new SinoUploadFileBuilder();   
                           sufb.Builder(orderSeq);//���פ��|��XTXT
                            }else{
                                log.info("�g�JPDF���ѡA�N���W��");
                                MailNote mailS=new MailNote();
                                mailS.mailServer(false,orderSeq);   

                            }
                    }catch(Exception e){
                            log.error("Error while process policySerial=" + policySerial + ",orderSeq=" + orderSeq + ":"+e );
                            e.printStackTrace();                    
                    }
                }else{
                    try {
                        // �z�L builder �^�� JSON ���, �üg�J table

                     for (int j = 0; j <  policy.getJSONArray("NAIN").size(); j++) {
                        JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(j);
                        if(NAIN.getString("Id_type").equalsIgnoreCase("1")){
                            System.out.printf("single Id_type"+NAIN.getString("Id_type")+"\n");
                        }else{
                            System.out.printf("multi Id_type"+j+"\n");

                         GTLToPdfBuilder builder = new GTLToPdfJsonBuilder(policy,j);  

                         log.info("SeqNo...["+i+"]:"+builder.getPA_Itm_SeqNo());


                                this.insertOrder(builder);//140923�令�Y��Ƥw�s�b��Ʈw�A�h���i��g�J�A�����򲣥�PDF�٬O�|��
              

                        }
                     }                          


                        // �z�L http �� PDF MODEL ���� PDF
                        PDF_complete_flag=this.callPDFModel(policySerial, orderSeq, incomeId);
                        if(PDF_complete_flag){//140923�令�YPDF���ѡA�h�|�H�H�q���A�ä�������ZIP���ʧ@
                            log.info("�g�JPDF�ɮק����A�ǳƤW��");
                             // ��[�Kzip
                            Zip4SinoAES zsinoAES=new Zip4SinoAES();
                            zsinoAES.setId4zip(owner_id.trim());
                            zsinoAES.Process(orderSeq.trim());
                            
                            MailNote mailS=new MailNote();
                            mailS.mailServer(true,orderSeq);                             
                            
                            // Ū���ɮ�, �z�L http �^�� Sinopac
    //                        this.postPDFFile(policySerial, orderSeq);//���״��ռȮɤ��W�Ǫ��A


    //                      String markMSG=this.getOrderInfoBySqNo(orderSeq);//�����׵��O�hmark���w�ǰe���
    //                        log.info(markMSG);     
                            
                           SinoUploadFileBuilder sufb=new SinoUploadFileBuilder();   
                           sufb.Builder(orderSeq);//���פ��|��XTXT
                        }else{
                            log.info("�g�JPDF���ѡA�N���W��");
                            MailNote mailS=new MailNote();
                            mailS.mailServer(false,orderSeq);   

                        }


                    } catch (Exception e) {
                        log.error("Error while process policySerial=" + policySerial + ",orderSeq=" + orderSeq + ":"+e );
                        e.printStackTrace();
                    }
                }        
            }
        //��PDF PART-------------------------------------------------------------
        if (policies != null && !policies.isEmpty()) {
            for (int i = 0; i < policies.size(); i++) {
                JSONObject policy = (JSONObject) policies.get(i);
                //log.info("policy[" + i + "]=" + policy);
                String policySerial = policy.getString("policy_serial");
                String orderSeq = policy.getString(ORDER_SEQ_STR);
                 //�קK���׭��ưe�w�ǹL���O��
                              
                if (this.isExistsOrder(orderSeq)) {//����mark 
                    continue;
                }
                try {
                    // �z�L builder �^�� JSON ���, �üg�J table
//                    GTLToPdfBuilder builder = new GTLToPdfJsonBuilder(policy);
//                   log.info("GET0001:"+builder.getCmpgn_Nm());       
                    
                    //this.insertOrder(builder);

                    // �z�L http �� PDF MODEL ���� PDF
                    //this.callPDFModel(policySerial, orderSeq, incomeId);

                    // Ū���ɮ�, �z�L http �^�� Sinopac
                    //this.postPDFFile(policySerial, orderSeq);

                } catch (Exception e) {
                    log.error("Error while process policySerial=" + policySerial + ",orderSeq=" + orderSeq + ":" + e);
                }
            }
        } else {
            log.info("���׵L���!");
            //new HttpClientAuth01().postPDFFile("1", "2");
        }
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) throws Exception {
////        try {
////                Properties properties = PropertiesTool.getProperties("default.properties");
////                System.out.println((String)properties.get("host"));
////	} catch (Exception e) {
////		e.printStackTrace();
////	} 
//     
//        Scanner scanner = new Scanner(System.in); 
//        System.out.printf("Hello Plz input date format like this (2014-10-03):");
//        
//        String inputTime=scanner.next().toString();
//        System.out.printf("Your Date is ! %s!", inputTime);
//        
//        ConnectionFactory4Properties36.getInstance();
//        //ConnectionFactory4Properties.getInstance();*/
//        
//        
//        
//        String incomeId = "A123456789";
//       
//        //new HttpClientAuth01().process(java.sql.Date.valueOf("2013-07-12"), incomeId);TEST
//        new HttpClientAuth01().process(java.sql.Date.valueOf(inputTime), incomeId);
////        new HttpClientAuth01().postPDFFile("1","2");
//    }
    public static void main(String[] args) throws Exception {
        /*
        Scanner scanner = new Scanner(System.in); 
        System.out.printf("Hello Plz input date format like this (2013-07-12):");
     
        String inputTime=scanner.next().toString();
        System.out.printf("Your Date is ! %s!", inputTime);
        */
        
        ConnectionFactory4Properties36.getInstance();
        //ConnectionFactory4Properties.getInstance();
                
        String incomeId = "A226035462";        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String inputTime = null;
        HttpClientAuth01 client = new HttpClientAuth01();
        
        //while (true)
        //{
            inputTime = sdf.format(new Date());
            System.out.printf("Your Date is = %s\n", inputTime);

            //new HttpClientAuth01().process(java.sql.Date.valueOf("2013-07-12"), incomeId);TEST
            client.process(java.sql.Date.valueOf(inputTime), incomeId);
            //new HttpClientAuth01().postPDFFile("1","2");            
            
            //Thread.sleep(120000); //���j������]�@��
        //}
            

    }    
    
}
