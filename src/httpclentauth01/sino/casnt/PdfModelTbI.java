/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpclentauth01.sino.casnt;

import sun.awt.SunHints.Value;

/**
 *
 * @author louie.zheng
 */
public interface PdfModelTbI {
    

    public String L_INSLIFETYPE="1";//1.壽險險別
    public String L_INSCOMPANY="2";//2.公司代號
    public String L_INSNAME="12";//3.被保險人姓名
    public String L_INSID="10";//4.被保險人ID
    public String L_INSBIRTHYY="4";//5-1.被保險人生日年
    public String L_INSBIRTHMM="2";//5-2.被保險人生日月
    public String L_INSBIRTHDD="2";//5-3.被保險人生日日
    public String L_INSSEX="1";//6.被保險人性別
    public String L_INSDATAID="20";//7.保單號碼
    public String L_INSPOLICYTYPE="1";//8.保單分類    
    public String L_INSPROCATE="1";//9.險種分類
    public String L_INSPRODUCT="2";//10.險種(商品)  
    public String L_INSCLAIMDIE="10";//11.給付項目(身故)
    public String L_INSCLAIMDISABLEA="10";//12.給付項目(全殘)
    public String L_INSCLAIMDISABLEP="10";//13.給付項目(殘廢扶助金) 
    public String L_INSCLAIMSPEMONY="10";//14.給付項目(特定事故保險金)
    public String L_INSCLAIMFIRST="10";//15.給付項目(初次罹患)
    public String L_INSCLAIMMED="10";//16.給付項目(醫療限額)
    public String L_INSCLAIMMEDSELF="10";//17.給付項目(醫療限額 自負額)
    public String L_INSCLAIMDAY="10";//18.給付項目(日額)
    public String L_INSCLAIMSURGERYH="10";//19.給付項目(住院手術) 
    public String L_INSCLAIMSURGERYN="10";//20.給付項目(門診手術) 
    public String L_INSCLAIMCLINIC="10";//21.給付項目(門診)
    public String L_INSCLAIMCRITICAL="10";//22.給付項目(重大疾病(含特定傷病))
    public String L_INSCLAIMCRIBURN="10";//23.給付項目(重大燒燙傷)
    public String L_INSCLAIMCANCER="10";//24.給付項目(癌症療養)
    public String L_INSCLAIMRECOVER="10";//25.給付項目(出院療養)
    public String L_INSCLAIMDISABLE="10";//26.給付項目(失能)
    public String L_INSRECEIVEYY="4";//27-1.收件日期 年
    public String L_INSRECEIVEMM="2";//27-2.收件日期 月
    public String L_INSRECEIVEDD="2";//27-3.收件日期 日
    public String L_INSCONTEXPIRYY="4";//28-1.契約滿期日期 年
    public String L_INSCONTEXPIRMM="2";//28-2.契約滿期日期 月
    public String L_INSCONTEXPIRDD="2";//28-3.契約滿期日期 日
    public String L_INSPRM="10";//29.保費
    public String L_INSPAYTYP="1";//30.保費繳別
    public String L_INSSTATUS="2";//31.保單狀況
    public String L_INSSTATUSYY="4";//32-1.保單狀況生效日期 年
    public String L_INSSTATUSMM="2";//32-2.保單狀況生效日期 月
    public String L_INSSTATUSDD="2";//32-3.保單狀況生效日期 日
    public String L_INSAPPNAME="12";//33.要保人姓名
    public String L_INSAPPID="10";//34.要保人身分證號
    public String L_INSAPPBIRYY="4";//35-1.要保人出生日期 年
    public String L_INSAPPBIRMM="2";//35-2.要保人出生日期 月
    public String L_INSAPPBIRDD="2";//35-3.要保人出生日期 日
    public String L_INSREALTION="2";//36.要保人與被保險人關係
    
    

    
}
