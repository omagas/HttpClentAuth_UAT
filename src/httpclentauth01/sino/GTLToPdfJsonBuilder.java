/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpclentauth01.sino;

import com.zurich.sds.batch.utils.QueryUtils;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.Date; 
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jason.huang
 */

public class GTLToPdfJsonBuilder implements GTLToPdfBuilder {
    private static final String QUERY_Cmpgn_Ref_Tb = "SELECT Cmpgn_Nm FROM Cmpgn_Ref_Tb where Cmpgn_CD=?";
    private static final String QUERY_RISK_AMT = "select cast(isnull(Risk_Amt,0) as float)/10000 as Risk_Amt from [dbo].[CmpgnRisk_Amt_Ref_Tb] where Cmpgn_CD=? and Risk_Id=? and Risk_Item_ID=?";
    private static final String QUERY_RISK_AMT02 = "select isnull(Risk_Amt,0) as Risk_Amt from [dbo].[CmpgnRisk_Amt_Ref_Tb] where Cmpgn_CD=? and Risk_Id=? and Risk_Item_ID=?";    
    private static final String QUERY_RISK_AMT_S ="select cast(isnull(sum(Risk_Amt),0) as float)/10000 as Risk_Amt_S from [dbo].[CmpgnRisk_Amt_Ref_Tb] where Cmpgn_CD=? and Risk_Id=? and Risk_Item_ID in (?,?,?)";   
    private String Cmpgn_Nm;
    private JSONObject policy;
    private Integer SeqNo;
    private String Risk_Amt;
    private Log log = LogFactory.getLog(this.getClass());
    private String Cmpgn_CD_T;
    public String RSK02_MK;
    public String RSK01_MK;
    public String RSK05_MK;
    private String Rsk01_MK;
    private String TourArea_CD;
    private String isuued_sasia;
    private Map<String,String> Tour_cd_ref;
    private String TourPlace_Des;
    private Integer Rsk09;
    private String Rsk10;    
    private String Rsk11;
    private String Tel_O_1;
    private String Tel_H_1;
    private String Tel_H_2;
    private String Tel_O_2;
    private String Risk13_MK;
    private String RSK20_MK;
    private String RSK21_MK;
    private String RSK22_MK;
    private String RSK23_MK;
    private String RSK24_MK;
    private String RSK25_MK;
    private HashMap<String, String> relation;
    private HashMap<String, String> relation_owner;
            
    public GTLToPdfJsonBuilder(JSONObject policy,Integer SeqNo) {
        this.policy = policy;
        this.SeqNo=SeqNo;
        Cmpgn_CD_T=policy.getString("proj_no");
        try {
            relation = new HashMap<String,String>();   
            relation.put("8", "99");
            relation.put("2", "02");
            relation.put("3", "03");
            relation.put("4", "04");
            relation.put("5", "06");//因為受益人在永豐與蘇黎世代碼不同 
            relation.put("6", "05");//兄弟姊妹-永豐06:蘇黎世05，因為受益人在永豐與蘇黎世代碼不同故需轉換。 
            relation_owner=new HashMap<String,String>(); 
            relation_owner.put("1", "01");
            relation_owner.put("2", "02");
            relation_owner.put("3", "04");
            relation_owner.put("4", "03");
            
            
        } catch (Exception e) {         
           e.printStackTrace();
        }


    }
    
    public GTLToPdfJsonBuilder() {
        
    }    

    public String getApply_Clause() {
        return "";
    }

    public String getApply_Typ() {
        return "N";
    }

    public String getInsured_Item() {
        return "N";
    }

    public String getService_VoiceFax_Desc() {
        return "";
    }

    public String getDataID() {
        return  policy.get("order_seq").toString();
        //return  "SDS13121800005";
    }

    public int getData_ID_VerNo() {
        return 1;
    }

    public String getRecpt_No() {
        return "";//不知道資料在哪
    }

    public String getChl_CD() {
        return "";
    }

    public String getChl_Nm() {
        return "";
    }

    public String getCmpgn_CD() {
        return Cmpgn_CD_T;
    }

    public String getCmpgn_Nm() {
        
        try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_Cmpgn_Ref_Tb, new Object[]{Cmpgn_CD_T});
            
            if(result2!=null ){
            Cmpgn_Nm=result2.get("Cmpgn_Nm").toString();
            }else{
            Cmpgn_Nm="";   
            }
        } catch (SQLException ex) {
            Logger.getLogger(GTLToPdfJsonBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Cmpgn_Nm;
    }

    public String getHost_Proj_CD() {
   
        return "";
    }

    public String getDelegate_Nm() {
        return "";
    }

    public String getAgnt_CD() {
        return "XW995";
    }

    public String getIssue_Brh_CD() {
        return "90";
    }

    public String getSales_CD() {
        return "";
        
    }

    public String getData_ID() {
       
        return policy.getString("order_seq");
        //return "SDS13121800005-01";
    }

    public String getPaymnt_Mthd_CD() {
        //return "D000";  有關繳費方式，剛跟維欣討論必須待Zurich法務確認，在有解決方案前，請先將繳費方式的勾選拿掉，避免客戶疑慮。
        return "";
    }

    public String getRecpt_No_Desc() {
        return "";
    }

    public String getService_Tel_Desc() {
        return "";
    }

    public String getService_Fax_Desc() {
        return "";
    }

    public String getIns_Eff_Dt_YY() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0);
        String Eff_Dt_YY;
        if(next_policy.getString("issue_str_date").isEmpty()){
            Eff_Dt_YY=""; 
        }else{
            int tmpYY= Integer.parseInt(next_policy.getString("issue_str_date").substring(0,4));
            Eff_Dt_YY=Integer.toString(tmpYY-1911);
        }
        
        return Eff_Dt_YY;
    }

    public String getIns_Eff_Dt_MM() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0);        
        return next_policy.getString("issue_str_date").substring(4,6);
      
    }

    public String getIns_Eff_Dt_DD() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0);        
        return next_policy.getString("issue_str_date").substring(6,8);
      
    }

    public int getIns_Eff_Dt_HH() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0);        
        return next_policy.getInt("issued_start_time");
    
    }

    public String getIns_Exp_Dt_YY() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0);
        String Eff_Dt_YY;
        if(next_policy.getString("issue_end_date").isEmpty()){
            Eff_Dt_YY=""; 
        }else{
            int tmpYY= Integer.parseInt(next_policy.getString("issue_end_date").substring(0,4));
            Eff_Dt_YY=Integer.toString(tmpYY-1911);
        }
        
        return Eff_Dt_YY;
        
    }

    public String getIns_Exp_Dt_MM() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0);        
        return next_policy.getString("issue_end_date").substring(4,6);
        
    }

    public String getIns_Exp_Dt_DD() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0);        
        return next_policy.getString("issue_end_date").substring(6,8);
        
    }

    public int getIns_Exp_Dt_HH() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0);        
        return next_policy.getInt("issued_end_time");
        
    }

    public String getIncome_Dt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(Calendar.getInstance().getTime());
        return nowTime;
        
    }

    public int getApplyFm_TotPage() {
        if(getTourCust_Cnt()>4){
            return 2;
        }else{
            return 1;
        }
    }

    public String getApplyList_MK() {
        if(getTourCust_Cnt()>4){
            return "Y";
        }else{
            return "N";
        }
    }

    public String getChl_Data_No() {
        return "";
    }

    public int getApplicant_Cust_No() {
        return 99999;
    }

    public String getTourArea_CD() {
//        Tour_cd_ref.put("01", "台灣");
//        Tour_cd_ref.put("02", "澎湖");
//        Tour_cd_ref.put("03", "金門");
//        Tour_cd_ref.put("04", "馬祖");
//        Tour_cd_ref.put("05", "其他離島");
//        Tour_cd_ref.put("06", "東北亞 韓國、日本");
//        Tour_cd_ref.put("07", "東南亞 新、馬、泰");
//        Tour_cd_ref.put("08", "大陸港澳");
//        Tour_cd_ref.put("09", "中東地區");
//        Tour_cd_ref.put("10", "美加地區");
//        Tour_cd_ref.put("11", "紐、澳");
//        Tour_cd_ref.put("12", "歐洲");
//        Tour_cd_ref.put("13", "非洲");
//        Tour_cd_ref.put("14", "中南美洲");
//        Tour_cd_ref.put("15", "其他");
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0); 
       isuued_sasia=next_policy.getString("isuued_sasia");  
        return isuued_sasia;    
    }

    public String getTourPlace_Desc() { 
        if(isuued_sasia.equals("01")){
            TourPlace_Des="台灣";
        }else if(isuued_sasia.equals("02")){
            TourPlace_Des="澎湖";
        }else if(isuued_sasia.equals("03")){
            TourPlace_Des="金門";
        }else if(isuued_sasia.equals("04")){
            TourPlace_Des="馬祖";
        }else if(isuued_sasia.equals("05")){
            TourPlace_Des="其他離島";
        }else if(isuued_sasia.equals("06")){
            TourPlace_Des="東北亞 韓國、日本";
        }else if(isuued_sasia.equals("07")){
            TourPlace_Des="東南亞 新、馬、泰";
        }else if(isuued_sasia.equals("08")){
            TourPlace_Des="大陸港澳";
        }else if(isuued_sasia.equals("09")){
            TourPlace_Des="中東地區";
        }else if(isuued_sasia.equals("10")){
            TourPlace_Des="美加地區";
        }else if(isuued_sasia.equals("11")){
            TourPlace_Des="紐、澳";
        }else if(isuued_sasia.equals("12")){
            TourPlace_Des="歐洲";
        }else if(isuued_sasia.equals("13")){
            TourPlace_Des="非洲";
        }else if(isuued_sasia.equals("14")){
            TourPlace_Des="中南美洲";
        }else if(isuued_sasia.equals("17")){
            TourPlace_Des="法國";
        }else if(isuued_sasia.equals("18")){
            TourPlace_Des="德國";   
        }else if(isuued_sasia.equals("19")){
            TourPlace_Des="西班牙";    
        }else if(isuued_sasia.equals("20")){
            TourPlace_Des="葡萄牙";      
        }else if(isuued_sasia.equals("21")){
            TourPlace_Des="奧地利";  
        }else if(isuued_sasia.equals("22")){
            TourPlace_Des="荷蘭"; 
        }else if(isuued_sasia.equals("23")){
            TourPlace_Des="比利時";               
        }else if(isuued_sasia.equals("24")){
            TourPlace_Des="盧森堡";  
        }else if(isuued_sasia.equals("25")){
            TourPlace_Des="丹麥";     
        }else if(isuued_sasia.equals("26")){
            TourPlace_Des="芬蘭";  
        }else if(isuued_sasia.equals("27")){
            TourPlace_Des="瑞典";     
        }else if(isuued_sasia.equals("28")){
            TourPlace_Des="斯洛伐克";  
        }else if(isuued_sasia.equals("29")){
            TourPlace_Des="斯洛維尼亞";   
        }else if(isuued_sasia.equals("30")){
            TourPlace_Des="波蘭"; 
        }else if(isuued_sasia.equals("31")){
            TourPlace_Des="捷克";    
        }else if(isuued_sasia.equals("32")){
            TourPlace_Des="匈牙利";    
        }else if(isuued_sasia.equals("33")){
            TourPlace_Des="希臘";    
        }else if(isuued_sasia.equals("34")){
            TourPlace_Des="義大利";   
        }else if(isuued_sasia.equals("35")){
            TourPlace_Des="馬爾地";   
        }else if(isuued_sasia.equals("36")){
            TourPlace_Des="愛沙尼亞";  
        }else if(isuued_sasia.equals("37")){
            TourPlace_Des="拉脫維亞";    
        }else if(isuued_sasia.equals("38")){
            TourPlace_Des="立陶宛";     
        }else if(isuued_sasia.equals("39")){
            TourPlace_Des="冰島";      
        }else if(isuued_sasia.equals("40")){
            TourPlace_Des="挪威";   
        }else if(isuued_sasia.equals("41")){
            TourPlace_Des="瑞士";     
        }else if(isuued_sasia.equals("42")){
            TourPlace_Des="列支敦斯登";   
        }else if(isuued_sasia.equals("43")){
            TourPlace_Des="羅馬尼亞"; 
        }else if(isuued_sasia.equals("44")){
            TourPlace_Des="保加利亞";  
        }else if(isuued_sasia.equals("45")){
            TourPlace_Des="賽普勒斯";     
        }else if(isuued_sasia.equals("46")){
            TourPlace_Des="克羅埃西亞";    
        }else if(isuued_sasia.equals("47")){
            TourPlace_Des="丹麥格陵蘭島";
        }else if(isuued_sasia.equals("48")){
            TourPlace_Des="法羅群島";             
        }else{
            TourPlace_Des="其他";
        }
        return TourPlace_Des;
    }

    public int getTourCust_Cnt() {
        int next_policy=policy.getJSONArray("NAIN").size();
        return next_policy-1;
    }

    public int getTour_Days() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0); 
        return Integer.parseInt(next_policy.getString("issued_days"));         
    }

    public int getTot_Prm() {                       
        return  (int)Double.parseDouble(policy.get("total_prem").toString());
    }

    public String getApplicant_Data_ID() {
        return  policy.get("order_seq").toString();
    }

    public int getApplicant_CustNo() {
        return 99999;
    }

    public String getApplicant_ID() {
//        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0); 
//        return next_policy.getString("ins_id");  
        JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(0);
        return NAIN.getString("client_id");        
    }

    public String getApplicant_Sex() {
        JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(0);
        return NAIN.getString("sex");
    }

    public String getApplicant_Nm() {
        //JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(0);name
        JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(0);
        return NAIN.getString("name");

    }

    public String getApplicant_Bth() {
        JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(0);
        String APP_birth; 
        int APP_birth_YY=Integer.parseInt(NAIN.getString("birth").substring(0,4))-1911;
        
        if (NAIN.getString("birth").isEmpty()){
            APP_birth="民國　　　年　　月　　日　　歲";
        }else{
            APP_birth="民國"+APP_birth_YY +"年"+NAIN.getString("birth").substring(4,6)+"月"+NAIN.getString("birth").substring(6,8)+"日　歲";
        }
        
        return APP_birth;
    }

    public String getApplicant_Rel_CD() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0);
            //next_policy.getString("relation");
        String ReturnValu=""; 
        String KeyValu="";
        
        //System.out.println("relation"+next_policy.getString("relation"));
        KeyValu=next_policy.getString("relation_owner");
        if(relation_owner.isEmpty() || relation_owner.get(KeyValu)==null){
            ReturnValu="05";
        }else{
            ReturnValu=relation_owner.get(KeyValu);            
        }

//            if(KeyValu.equals("1")){
//               ReturnValu=Applicant_Rel_Map.getRelation11.toString();
//           }else if(KeyValu.equals("2")){
//               ReturnValu=Applicant_Rel_Map.getRelation12.toString();
//           }else if(KeyValu.equals("3")){
//               ReturnValu=Applicant_Rel_Map.getRelation13.toString();
//           }else if(KeyValu.equals("4")){
//               ReturnValu=Applicant_Rel_Map.getRelation14.toString();
//           }else if(KeyValu.equals("5")){
//               ReturnValu=Applicant_Rel_Map.getRelation15.toString();
//           }else{
//               ReturnValu="00";
//           }      
        return ReturnValu;
    }

    public String getApplicant_Rel_Desc() {
        JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0);
            //next_policy.getString("relation");
        String ReturnValu=""; 
        String KeyValu="";
        //System.out.println("relation"+next_policy.getString("relation"));
        KeyValu=next_policy.getString("relation_owner");
        if(relation_owner.isEmpty() || relation_owner.get(KeyValu)==null){
            ReturnValu="05";
        }else{
            ReturnValu=relation_owner.get(KeyValu);            
        }
        
             
            if(KeyValu.equals("1")){
               ReturnValu="本人";
           }else if(KeyValu.equals("2")){
               ReturnValu="配偶";
           }else if(KeyValu.equals("3")){
               ReturnValu="父母";
           }else if(KeyValu.equals("4")){
               ReturnValu="子女";
           }else{ 
               ReturnValu="其他";
           }     
        
        return ReturnValu;
    }

    public String getApplicant_Insured_Rel_Desc() {
        return "";
    }

    public String getApplicant_Tel_O() {
        JSONObject NAIN=(JSONObject) policy.getJSONArray("ADIN").get(0);
        String Tel_O=NAIN.getString("Phone_office");//14/09/09永豐反映 公司電話 和 家裡電話 相反
        //String Tel_O=NAIN.getString("Phone_home");//14/09/24改回原來
        Integer Tel_O_len=Tel_O.length();
        if (Tel_O_len<5) {
            Tel_O_1="";
            Tel_O_2="";
        }else{
            Tel_O_1=Tel_O.substring(0,2);
            Tel_O_2=Tel_O.substring(3);
        }
        return Tel_O_2.toString();
        
        
    }

    public String getApplicant_Tel_O_1() { 
        return Tel_O_1;
    }

    public String getApplicant_Tel_O_2() {
        return Tel_O_2;
    }

    public String getApplicant_Tel_O_3() {
        return "";
    }

    public String getCust_Tel_H() {
        JSONObject NAIN=(JSONObject) policy.getJSONArray("ADIN").get(0);
        String Tel_O=NAIN.getString("Phone_home");//14/09/09永豐反映 公司電話 和 家裡電話 相反
        //String Tel_O=NAIN.getString("Phone_office");//14/09/24改回原來
        Integer Tel_O_len=Tel_O.length();
        if (Tel_O_len<5) {
            Tel_H_1="";
            Tel_H_2="";
        }else{
            Tel_H_1=Tel_O.substring(0,2);
            Tel_H_2=Tel_O.substring(3);
            
        }
        return Tel_O.toString();
    }

    public String getApplicant_Tel_H_1() {
        return Tel_H_1;
    }

    public String getApplicant_Tel_H_2() {
        return Tel_H_2;
    }

    public String getApplicant_Mobile() {
        JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(0);
        return NAIN.getString("cellur_phone_no");
    }

    public String getApplicant_Fax() {
        return "";
    }

    public String getApplicant_EMail() {
        JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(0);
        return NAIN.getString("email1");
    }

    public String getApplicant_ZipCD() {
        JSONObject NAIN=(JSONObject) policy.getJSONArray("ADIN").get(0);
        return NAIN.getString("Zip");
    }

    public String getApplicant_ZipCD_1() {
        return "";
    }

    public String getApplicant_ZipCD_2() {
        return "";
    }

    public String getApplicant_ZipCD_3() {
        return "";
    }

    public String getApplicant_ZipCD_4() {
        return "";
    }

    public String getApplicant_ZipCD_5() {
        return "";
    }

    public String getApplicant_Adrs_ZipDesc() {
        //return "臺北市文山區";
        return "";
    }

    public String getApplicant_Adrs_EZipDesc() {
        //return "Wunshan District, Taipei City";
        return "";
    }

    public String getApplicant_Adrs() {
               
        JSONObject NAIN=(JSONObject) policy.getJSONArray("ADIN").get(0);        
        return NAIN.getString("address");
    }

    public String getPA_Data_ID() {
        return  policy.get("order_seq").toString();
    }

    public int getPA_Data_ID_VerNo() {
        return 1;
    }

    public String getPA_Recpt_No() {
        return "1";
    }

    public int getPA_Itm_SeqNo() {     
        return SeqNo;
    }

    public String getHost_Pkge_CD() {
        return "";
    }

    public String getEnCert_MK() {
       if(Cmpgn_CD_T.equalsIgnoreCase("AB06700006")||Cmpgn_CD_T.equalsIgnoreCase("AB06700005")){
           return "Y";
       }else{
           return "N";
       } 
    }

    public int getInsured_Cust_No() {
        return 99999;
    }

    public String getInsured_Sex() {
     JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(SeqNo);
        return NAIN.getString("sex");
    }

    public String getInsured_ID() {
     JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(SeqNo);
        return NAIN.getString("client_id");
    }

    public String getInsured_Nm() {
     JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(SeqNo);   
        return NAIN.getString("name");
    }

    public String getInsured_Bth() {
     JSONObject NAIN=(JSONObject) policy.getJSONArray("NAIN").get(SeqNo);   

        
        String APP_birth; 
        int APP_birth_YY=Integer.parseInt(NAIN.getString("birth").substring(0,4))-1911;
        
        if (NAIN.getString("birth").isEmpty()){
            APP_birth="";
        }else{
            APP_birth=APP_birth_YY +"/"+NAIN.getString("birth").substring(4,6)+"/"+NAIN.getString("birth").substring(6,8);
        }      
       return APP_birth;
    }

    public String getInsured_Benef() {//　09/04反映兄弟姊妹會印成法定繼承人(99)
        
     JSONObject PolicyDetail=(JSONObject) policy.getJSONArray("PolicyDetail").get(SeqNo-1);    
        
        String ReturnValu=""; 
        String KeyValu="";
        String RefValu=""; 
        
        //System.out.println("relation"+PolicyDetail.getString("relation"));
        KeyValu=PolicyDetail.getString("relation"); 
        
        String Bene_name = PolicyDetail.getString("Bene_name");
        //System.out.println("relation"+KeyValu+"Bene_name"+Bene_name);
        if(relation.isEmpty() || relation.get(KeyValu)==null){//修改NullPointerException 
            KeyValu="";
        }else{
           RefValu=relation.get(KeyValu); 
        }
            if(KeyValu.equals("8")){
               ReturnValu="法定繼承人";//法定繼承人
           }else if(KeyValu.equals("2")){
               ReturnValu=Bene_name;//配偶
           }else if(KeyValu.equals("3")){
               ReturnValu=Bene_name;//子女
           }else if(KeyValu.equals("4")){
               ReturnValu=Bene_name;//父母
           }else if(KeyValu.equals("5")){
               ReturnValu=Bene_name;//祖父母
           }else if(KeyValu.equals("6")){
               ReturnValu=Bene_name;//兄弟姐妹             
           }else{
               RefValu="99";
               ReturnValu="法定繼承人";
           }    
        return ReturnValu+" ( "+RefValu+" )";
    }

    public String getInsured_Job() {
        //JSONObject PolicyDetail=(JSONObject) policy.getJSONArray("PolicyDetail").get(SeqNo-1);          
        return "";  
    }

    public String getRsk01_Amt() {
         try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"01",0});
            if(result2!=null ){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }            
        } catch (SQLException ex) {
            log.error(ex);
        }
            if(Risk_Amt.equals("0") || Risk_Amt.equals("")){
                Risk_Amt="";
                RSK01_MK="N";
            }else{
                RSK01_MK="Y";
            }  
        return Risk_Amt;
    }

    public String getRsk05_Amt() {
       try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"0７",10});
            if(result2!=null){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }
        } catch (SQLException ex) {
            log.error(ex);
        }        
            if(Risk_Amt.equals("0") || Risk_Amt.equals("")||Risk_Amt==null){
                Risk_Amt="";
                RSK02_MK="N";
            }else{
                RSK02_MK="Y";
            }      
        return Risk_Amt;
    }

    public String getRsk02_Amt() {   
       try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT_S, new Object[]{Cmpgn_CD_T,"07",7,8,9});
            if(result2!=null){
            Risk_Amt=result2.get("Risk_Amt_S").toString();
            }else{
            Risk_Amt="";
            }
        } catch (SQLException ex) {
            //Logger.getLogger(GTLToPdfJsonBuilder.class.getName()).log(Level.SEVERE, null, ex);
             log.error(ex);
        }        
            if(Risk_Amt.equals("0") || Risk_Amt.equals("")||Risk_Amt.equals("0.0")){
                Risk_Amt="";
                RSK05_MK="N";
            }else{
                RSK05_MK="Y";
            }   
        return Risk_Amt;                
       
    }

    public String getRsk06_Amt() {
        return "";
    }

    public String getRsk07_Amt() {
        return "";
    }

    public String getRsk08_Amt() {
        return "";
    }

    public String getRsk09_Amt() {
        Integer rvalue=0;
            //JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(0); 
            JSONObject next_policy=(JSONObject) policy.getJSONArray("PolicyDetail").get(SeqNo-1);//140123 修改 BUG
 
            try {
                Rsk09= (int)Double.parseDouble(next_policy.getString("face_amt"));  
                rvalue=Rsk09/10000;
            } catch (Exception e) {
                log.error("face_amt formate error:" + e);
            }
                  
            
            
        if(Cmpgn_CD_T.equalsIgnoreCase("AB06700001")){
            Rsk10="---";//傷害實支實付
            Rsk11="---";//住院醫療費用保險金            
        }else if(Cmpgn_CD_T.equalsIgnoreCase("AB06700002")){
            Rsk10=Integer.toString(rvalue*10/100);//傷害實支實付
            Rsk11="---";//住院醫療費用保險金               
        }else if(Cmpgn_CD_T.equalsIgnoreCase("AB06700003")){
            Rsk10=Integer.toString(rvalue*10/100);//傷害實支實付
            Rsk11=Integer.toString(rvalue*10/100);//住院醫療費用保險金                           
        }else if(Cmpgn_CD_T.equalsIgnoreCase("AB06700004")){
            Rsk10=Integer.toString(rvalue*10/100);//傷害實支實付
            Rsk11=Integer.toString(rvalue*10/100);//住院醫療費用保險金      
        }else if(Cmpgn_CD_T.equalsIgnoreCase("AB06700005")){
            Rsk10="150";//傷害實支實付
            Rsk11="150";//住院醫療費用保險金             
        }else if(Cmpgn_CD_T.equalsIgnoreCase("AB06700006")){
            Rsk10="150";//傷害實支實付
            Rsk11="150";//住院醫療費用保險金              
        }
        
        
        
        return rvalue.toString()+"萬";
    }

    public String getRsk10_Amt() {
        
        return Rsk10.toString()+"萬";
    }

    public String getRsk11_Amt() {
        return Rsk11.toString()+"萬";
    }

    public String getRsk12_Amt() {
        return "";
    }

    public String getRsk13_Amt() {
       try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"06",1});
            if(result2!=null){
            Risk_Amt=result2.get("Risk_Amt").toString();
            Risk13_MK="Y";
            }else{
            Risk13_MK="N";    
            Risk_Amt="";
            }
        } catch (SQLException ex) {
            log.error(ex);
        }        
            if(Risk_Amt.equals("0")){
                Risk_Amt="";
            }      
        return Risk_Amt;
    }

    public String getRsk14_Amt() {
     try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"06",2});
            if(result2!=null){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }
        } catch (SQLException ex) {
            log.error(ex);
        }        
            if(Risk_Amt.equals("0")){
                Risk_Amt="";
            }      
        return Risk_Amt;
    }

    public String getRsk15_Amt() {
     try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT02, new Object[]{Cmpgn_CD_T,"06",3});
            if(result2!=null){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }
        } catch (SQLException ex) {
            log.error(ex);
        }        
            if(Risk_Amt.equals("0")){
                Risk_Amt="";
            }      
        return Risk_Amt;
    }

    public String getRsk16_Amt() {
     try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"06",4});
            if(result2!=null){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }
        } catch (SQLException ex) {
            log.error(ex);
        }        
            if(Risk_Amt.equals("0")){
                Risk_Amt="";
            }      
        return Risk_Amt;
    }

    public String getRsk17_Amt() {
     try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"06",5});
            if(result2!=null){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }
        } catch (SQLException ex) {
            log.error(ex);
        }        
            if(Risk_Amt.equals("0")){
                Risk_Amt="";
            }      
        return Risk_Amt;
    }

    public String getRsk18_Amt() {
     try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"06",6});
            if(result2!=null){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }
        } catch (SQLException ex) {
            log.error(ex);
        }        
            if(Risk_Amt.equals("0")){
                Risk_Amt="";
            }      
        return Risk_Amt;
    }

    public String getRsk19_Amt() {
     try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT02, new Object[]{Cmpgn_CD_T,"06",7});
            if(result2!=null){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }
        } catch (SQLException ex) {
            log.error(ex);
        }        
            if(Risk_Amt.equals("0")){
                Risk_Amt="";
            }      
        return Risk_Amt;
    }

    public String getRsk20_Amt() {
         try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"07",1});
            if(result2!=null ){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }            
        } catch (SQLException ex) {
            log.error(ex);
        }
            if(Risk_Amt.equals("0") || Risk_Amt.equals("")){
                Risk_Amt="";
                RSK20_MK="N";
            }else{
                RSK20_MK="Y";
            }  
        return Risk_Amt;
    }

    public String getRsk21_Amt() {
         try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"07",2});
            if(result2!=null ){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }            
        } catch (SQLException ex) {
            log.error(ex);
        }
            if(Risk_Amt.equals("0") || Risk_Amt.equals("")){
                Risk_Amt="";
                RSK21_MK="N";
            }else{
                RSK21_MK="Y";
            }  
        return Risk_Amt;
    }

    public String getRsk22_Amt() {
        try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"07",3});
            if(result2!=null ){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }            
        } catch (SQLException ex) {
            log.error(ex);
        }
            if(Risk_Amt.equals("0") || Risk_Amt.equals("")){
                Risk_Amt="";
                RSK22_MK="N";
            }else{
                RSK22_MK="Y";
            }  
        return Risk_Amt;
    }

    public String getRsk23_Amt() {
        try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"07",5});
            if(result2!=null ){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }            
        } catch (SQLException ex) {
            log.error(ex);
        }
            if(Risk_Amt.equals("0") || Risk_Amt.equals("")){
                Risk_Amt="";
                RSK23_MK="N";
            }else{
                RSK23_MK="Y";
            }  
        return Risk_Amt;
    }

    public String getRsk24_Amt() {
        try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"07",4});
            if(result2!=null ){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }            
        } catch (SQLException ex) {
            log.error(ex);
        }
            if(Risk_Amt.equals("0") || Risk_Amt.equals("")){
                Risk_Amt="";
                RSK24_MK="N";
            }else{
                RSK24_MK="Y";
            }  
        return Risk_Amt;
    }

    public String getRsk25_Amt() {
      try {
            Map result2 = QueryUtils.getInstance().querySingle(QUERY_RISK_AMT, new Object[]{Cmpgn_CD_T,"07",6});
            if(result2!=null ){
            Risk_Amt=result2.get("Risk_Amt").toString();
            }else{
            Risk_Amt="";
            }            
        } catch (SQLException ex) {
            log.error(ex);
        }
            if(Risk_Amt.equals("0") || Risk_Amt.equals("")){
                Risk_Amt="";
                RSK25_MK="N";
            }else{
                RSK25_MK="Y";
            }  
        return Risk_Amt;
    }

    public String getRsk01_MK() {
        return RSK01_MK;
    }

    public String getRsk02_MK() {
        return RSK02_MK;
    }

    public String getRsk05_MK() {
        return RSK05_MK;
    }

    public String getRsk06_MK() {
        return "";
    }

    public String getRsk07_MK() {
        return "";
    }

    public String getRsk08_MK() {
        return "";
    }

    public String getRsk13_MK() {
        return Risk13_MK;
    }

    public String getRsk20_MK() {
        return RSK20_MK;
    }

    public String getRsk21_MK() {
        return RSK21_MK;
    }

    public String getRsk22_MK() {
        return RSK22_MK;
    }

    public String getRsk23_MK() {
        return RSK23_MK;
    }

    public String getRsk24_MK() {
        return RSK24_MK;
    }

    public String getRsk25_MK() {
        return RSK25_MK;
    }

    public int getPer_Prm() {     
        //mode_prem
        JSONObject PolicyDetail=(JSONObject) policy.getJSONArray("PolicyDetail").get(SeqNo-1);
        return (int) Double.parseDouble(PolicyDetail.getString("mode_prem"));        
        //return  (int)Double.parseDouble(policy.get("total_prem").toString());
    }
    
    public String Seq_No() {
        return ("※※為確保您的權益請列印並親簽後，將要保書傳真到 02-21815084 /02-21815085，謝謝。");
    }
    
    public Object[] toObjectArray() {
        return new Object[]{this.getApply_Clause(), this.getApply_Typ(), this.getInsured_Item(), this.getService_VoiceFax_Desc(), this.getDataID(), this.getData_ID_VerNo(), this.getRecpt_No(), this.getChl_CD(), this.getChl_Nm(), this.getCmpgn_CD(), this.getCmpgn_Nm(), this.getHost_Proj_CD(), this.getDelegate_Nm(), this.getAgnt_CD(), this.getIssue_Brh_CD(), this.getSales_CD(), this.getData_ID(), this.getPaymnt_Mthd_CD(), this.getRecpt_No_Desc(), this.getService_Tel_Desc(), this.getService_Fax_Desc(), this.getIns_Eff_Dt_YY(), this.getIns_Eff_Dt_MM(), this.getIns_Eff_Dt_DD(), this.getIns_Eff_Dt_HH(), this.getIns_Exp_Dt_YY(), this.getIns_Exp_Dt_MM(), this.getIns_Exp_Dt_DD(), this.getIns_Exp_Dt_HH(), this.getIncome_Dt(), this.getApplyFm_TotPage(), this.getApplyList_MK(), this.getChl_Data_No(), this.getApplicant_Cust_No(), this.getTourArea_CD(), this.getTourPlace_Desc(), this.getTourCust_Cnt(), this.getTour_Days(), this.getTot_Prm(), this.getApplicant_Data_ID(), this.getApplicant_CustNo(), this.getApplicant_ID(), this.getApplicant_Sex(), this.getApplicant_Nm(), this.getApplicant_Bth(), this.getApplicant_Rel_CD(), this.getApplicant_Rel_Desc(), this.getApplicant_Insured_Rel_Desc(), this.getApplicant_Tel_O(), this.getApplicant_Tel_O_1(), this.getApplicant_Tel_O_2(), this.getApplicant_Tel_O_3(), this.getCust_Tel_H(), this.getApplicant_Tel_H_1(), this.getApplicant_Tel_H_2(), this.getApplicant_Mobile(), this.getApplicant_Fax(), this.getApplicant_EMail(), this.getApplicant_ZipCD(), this.getApplicant_ZipCD_1(), this.getApplicant_ZipCD_2(), this.getApplicant_ZipCD_3(), this.getApplicant_ZipCD_4(), this.getApplicant_ZipCD_5(), this.getApplicant_Adrs_ZipDesc(), this.getApplicant_Adrs_EZipDesc(), this.getApplicant_Adrs(), this.getPA_Data_ID(), this.getPA_Data_ID_VerNo(), this.getPA_Recpt_No(), this.getPA_Itm_SeqNo(), this.getHost_Pkge_CD(), this.getEnCert_MK(), this.getInsured_Cust_No(), this.getInsured_Sex(), this.getInsured_ID(), this.getInsured_Nm(), this.getInsured_Bth(), this.getInsured_Benef(), this.getInsured_Job(), this.getRsk01_Amt(), this.getRsk02_Amt(), this.getRsk05_Amt(), this.getRsk06_Amt(), this.getRsk07_Amt(), this.getRsk08_Amt(), this.getRsk09_Amt(), this.getRsk10_Amt(), this.getRsk11_Amt(), this.getRsk12_Amt(), this.getRsk13_Amt(), this.getRsk14_Amt(), this.getRsk15_Amt(), this.getRsk16_Amt(), this.getRsk17_Amt(), this.getRsk18_Amt(), this.getRsk19_Amt(), this.getRsk20_Amt(), this.getRsk21_Amt(), this.getRsk22_Amt(), this.getRsk23_Amt(), this.getRsk24_Amt(), this.getRsk25_Amt(), this.getRsk01_MK(), this.getRsk02_MK(), this.getRsk05_MK(), this.getRsk06_MK(), this.getRsk07_MK(), this.getRsk08_MK(), this.getRsk13_MK(), this.getRsk20_MK(), this.getRsk21_MK(), this.getRsk22_MK(), this.getRsk23_MK(), this.getRsk24_MK(), this.getRsk25_MK(), this.getPer_Prm(),this.Seq_No()};
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
