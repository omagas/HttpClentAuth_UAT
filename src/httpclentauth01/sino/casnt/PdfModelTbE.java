/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpclentauth01.sino.casnt;

import com.zurich.sds.sino.utils.Type;
/**
 *
 * @author louie.zheng
 */
public class PdfModelTbE implements PdfModelTbI {

    public String INSLIFETYPE;//1.���I�I�O
    public String INSCOMPANY;//2.���q�N��
    public String INSNAME;//3.�Q�O�I�H�m�W
    public String INSID;//4.�Q�O�I�HID
    public String INSBIRTHYY;//5-1.�Q�O�I�H�ͤ�~
    public String INSBIRTHMM;//5-2.�Q�O�I�H�ͤ��
    public String INSBIRTHDD;//5-3.�Q�O�I�H�ͤ��
    public String INSSEX;//6.�Q�O�I�H�ʧO
    public String INSDATAID;//7.�O�渹�X
    public String INSPOLICYTYPE;//8.�O�����    
    public String INSPROCATE;//9.�I�ؤ���
    public String INSPRODUCT;//10.�I��(�ӫ~)  
    public String INSCLAIMDIE;//11.���I����(���G)
    public String INSCLAIMDISABLEA;//12.���I����(����)
    public String INSCLAIMDISABLEP;//13.���I����(�ݼo�ߧU��) 
    public String INSCLAIMSPEMONY;//14.���I����(�S�w�ƬG�O�I��)
    public String INSCLAIMFIRST;//15.���I����(�즸���w)
    public String INSCLAIMMED;//16.���I����(�������B)
    public String INSCLAIMMEDSELF;//17.���I����(�������B �ۭt�B)
    public String INSCLAIMDAY;//18.���I����(���B)
    public String INSCLAIMSURGERYH;//19.���I����(��|��N) 
    public String INSCLAIMSURGERYN;//20.���I����(���E��N) 
    public String INSCLAIMCLINIC;//21.���I����(���E)
    public String INSCLAIMCRITICAL;//22.���I����(���j�e�f(�t�S�w�˯f))
    public String INSCLAIMCRIBURN;//23.���I����(���j�N�S��)
    public String INSCLAIMCANCER;//24.���I����(���g���i)
    public String INSCLAIMRECOVER;//25.���I����(�X�|���i)
    public String INSCLAIMDISABLE;//26.���I����(����)
    public String INSRECEIVEYY;//27-1.������ �~
    public String INSRECEIVEMM;//27-2.������ ��
    public String INSRECEIVEDD;//27-3.������ ��
    public String INSCONTEXPIRYY;//28-1.����������� �~
    public String INSCONTEXPIRMM;//28-2.����������� ��
    public String INSCONTEXPIRDD;//28-3.����������� ��
    public String INSPRM;//29.�O�O
    public String INSPAYTYP;//30.�O�Oú�O
    public String INSSTATUS;//31.�O�檬�p
    public String INSSTATUSYY;//32-1.�O�檬�p�ͮĤ�� �~
    public String INSSTATUSMM;//32-2.�O�檬�p�ͮĤ�� ��
    public String INSSTATUSDD;//32-3.�O�檬�p�ͮĤ�� ��
    public String INSAPPNAME;//33.�n�O�H�m�W
    public String INSAPPID;//34.�n�O�HID
    public String INSAPPBIRYY;//35-1.�n�O�H�X�ͤ�� �~
    public String INSAPPBIRMM;//35-2.�n�O�H�X�ͤ�� ��
    public String INSAPPBIRDD;//35-3.�n�O�H�X�ͤ�� ��
    public String INSREALTION;//36.�n�O�H�P�Q�O�I�H���Y 


    public String getINSAPPBIRDD() {
        
        return Type.NUMBER.fillData(this.INSAPPBIRDD, null, L_INSAPPBIRDD, null);
    }

    public String getINSAPPBIRMM() {
        return Type.NUMBER.fillData(this.INSAPPBIRMM, null, L_INSAPPBIRMM, null);
    }

    public String getINSAPPBIRYY() {
        return Type.NUMBER.fillData(this.INSAPPBIRYY, null, L_INSAPPBIRYY, null);
    }

    public String getINSAPPID() {
        return Type.STRING.fillData(this.INSAPPID, null, L_INSAPPID, null);
    }

    public String getINSAPPNAME() {
        
        return Type.STRING.fillData(this.INSAPPNAME, null, L_INSAPPNAME, null);
    }

    public String getINSBIRTHDD() {
        
        return Type.NUMBER.fillData(this.INSBIRTHDD, null, L_INSBIRTHDD, null);
    }

    public String getINSBIRTHMM() {
        
        return Type.NUMBER.fillData(this.INSBIRTHMM, null, L_INSBIRTHMM, null);
    }

    public String getINSBIRTHYY() {
        
        return Type.NUMBER.fillData(this.INSBIRTHYY, null, L_INSBIRTHYY, null);
    }

    public String getINSCLAIMCANCER() {
        
        return Type.NUMBER.fillData(this.INSCLAIMCANCER, null, L_INSCLAIMCANCER, null);
    }

    public String getINSCLAIMCLINIC() {
        
        return Type.NUMBER.fillData(this.INSCLAIMCLINIC, null, L_INSCLAIMCLINIC, null);
        
    }

    public String getINSCLAIMCRIBURN() {
        
        return Type.NUMBER.fillData(this.INSCLAIMCRIBURN, null, L_INSCLAIMCRIBURN, null);
    }

    public String getINSCLAIMCRITICAL() {
        
        return Type.NUMBER.fillData(this.INSCLAIMCRITICAL, null, L_INSCLAIMCRITICAL, null);
    }

    public String getINSCLAIMDAY() {
        
        return Type.NUMBER.fillData(this.INSCLAIMDAY, null, L_INSCLAIMDAY, null);
    }

    public String getINSCLAIMDIE() {
        
        return Type.NUMBER.fillData(this.INSCLAIMDIE, null, L_INSCLAIMDIE, null);
    }

    public String getINSCLAIMDISABLE() {
        
        return Type.NUMBER.fillData(this.INSCLAIMDISABLE, null, L_INSCLAIMDISABLE, null);
    }

    public String getINSCLAIMDISABLEA() {
        
        return Type.NUMBER.fillData(this.INSCLAIMDISABLEA, null, L_INSCLAIMDISABLEA, null);
    }

    public String getINSCLAIMDISABLEP() {
        
        return Type.NUMBER.fillData(this.INSCLAIMDISABLEP, null, L_INSCLAIMDISABLEP, null);
    }

    public String getINSCLAIMFIRST() {
       
        return Type.NUMBER.fillData(this.INSCLAIMFIRST, null, L_INSCLAIMFIRST, null);
    }

    public String getINSCLAIMMED() {
        
        return Type.NUMBER.fillData(this.INSCLAIMMED, null, L_INSCLAIMMED, null);
    }

    public String getINSCLAIMMEDSELF() {
        
        return Type.NUMBER.fillData(this.INSCLAIMMEDSELF, null, L_INSCLAIMMEDSELF, null);
    }

    public String getINSCLAIMRECOVER() {
        
        return Type.NUMBER.fillData(this.INSCLAIMRECOVER, null, L_INSCLAIMRECOVER, null);
    }

    public String getINSCLAIMSPEMONY() {
        
        return Type.NUMBER.fillData(this.INSCLAIMSPEMONY, null, L_INSCLAIMSPEMONY, null);
    }

    public String getINSCLAIMSURGERYH() {
        
        return Type.NUMBER.fillData(this.INSCLAIMSURGERYH, null, L_INSCLAIMSURGERYH, null);        
    }

    public String getINSCLAIMSURGERYN() {
        
        return Type.NUMBER.fillData(this.INSCLAIMSURGERYN, null, L_INSCLAIMSURGERYN, null);  
    }

    public String getINSCOMPANY() {
        
        return Type.STRING.fillData(this.INSCOMPANY, null, L_INSCOMPANY, null);  
    }

    public String getINSCONTEXPIRDD() {
        
        return Type.NUMBER.fillData(this.INSCONTEXPIRDD, null, L_INSCONTEXPIRDD, null); 
    }

    public String getINSCONTEXPIRMM() {
        
        return Type.NUMBER.fillData(this.INSCONTEXPIRMM, null, L_INSCONTEXPIRMM, null); 
    }

    public String getINSCONTEXPIRYY() {
        
        return Type.NUMBER.fillData(this.INSCONTEXPIRYY, null, L_INSCONTEXPIRYY, null); 
    }

    public String getINSDATAID() {
        
        return Type.STRING.fillData(this.INSDATAID, null, L_INSDATAID, null); 
    }

    public String getINSID() {
        
        return Type.STRING.fillData(this.INSID, null, L_INSID, null); 
    }

    public String getINSLIFETYPE() {

        return Type.STRING.fillData(this.INSLIFETYPE, null, L_INSLIFETYPE, null); 
    }

    public String getINSNAME() {
        
        return Type.STRING.fillData(this.INSNAME, null, L_INSNAME, null); 
    }

    public String getINSPAYTYP() {
        
        return Type.STRING.fillData(this.INSPAYTYP, null, L_INSPAYTYP, null); 
    }

    public String getINSPOLICYTYPE() {
        
        return Type.STRING.fillData(this.INSPOLICYTYPE, null, L_INSPOLICYTYPE, null); 
    }

    public String getINSPRM() {
        
        return Type.NUMBER.fillData(this.INSPRM, null, L_INSPRM, null);
    }

    public String getINSPROCATE() {
        
        return Type.STRING.fillData(this.INSPROCATE, null, L_INSPROCATE, null);
    }

    public String getINSPRODUCT() {
        
        return Type.STRING.fillData(this.INSPRODUCT, null, L_INSPRODUCT, null);
    }

    public String getINSREALTION() {
        
        return Type.STRING.fillData(this.INSREALTION, null, L_INSREALTION, null);
    }

    public String getINSRECEIVEDD() {
        
        return Type.NUMBER.fillData(this.INSRECEIVEDD, null, L_INSRECEIVEDD, null);
    }

    public String getINSRECEIVEMM() {
        
        return Type.NUMBER.fillData(this.INSRECEIVEMM, null, L_INSRECEIVEMM, null);
    }

    public String getINSRECEIVEYY() {
        
        return Type.NUMBER.fillData(this.INSRECEIVEYY, null, L_INSRECEIVEYY, null);
    }

    public String getINSSEX() {
        
        return Type.STRING.fillData(this.INSSEX, null, L_INSSEX, null);
    }

    public String getINSSTATUS() {
        
        return Type.STRING.fillData(this.INSSTATUS, null, L_INSSTATUS, null);
    }

    public String getINSSTATUSDD() {
        
        return Type.NUMBER.fillData(this.INSSTATUSDD, null, L_INSSTATUSDD, null);
    }

    public String getINSSTATUSMM() {
        
        return Type.NUMBER.fillData(this.INSSTATUSMM, null, L_INSSTATUSMM, null);
    }

    public String getINSSTATUSYY() {
        
        return Type.NUMBER.fillData(this.INSSTATUSYY, null, L_INSSTATUSYY, null);
    }
    
    
    
    

    public void setINSAPPBIRDD(String INSAPPBIRDD) {
        this.INSAPPBIRDD = INSAPPBIRDD;
    }

    public void setINSAPPBIRMM(String INSAPPBIRMM) {
        this.INSAPPBIRMM = INSAPPBIRMM;
    }

    public void setINSAPPBIRYY(String INSAPPBIRYY) {
        this.INSAPPBIRYY = INSAPPBIRYY;
    }

    public void setINSAPPID(String INSAPPID) {
        this.INSAPPID = INSAPPID;
    }

    public void setINSAPPNAME(String INSAPPNAME) {
        this.INSAPPNAME = INSAPPNAME;
    }

    public void setINSBIRTHDD(String INSBIRTHDD) {
        this.INSBIRTHDD = INSBIRTHDD;
    }

    public void setINSBIRTHMM(String INSBIRTHMM) {
        this.INSBIRTHMM = INSBIRTHMM;
    }

    public void setINSBIRTHYY(String INSBIRTHYY) {
        this.INSBIRTHYY = INSBIRTHYY;
    }

    public void setINSCLAIMCANCER(String INSCLAIMCANCER) {
        this.INSCLAIMCANCER = INSCLAIMCANCER;
    }

    public void setINSCLAIMCLINIC(String INSCLAIMCLINIC) {
        this.INSCLAIMCLINIC = INSCLAIMCLINIC;
    }

    public void setINSCLAIMCRIBURN(String INSCLAIMCRIBURN) {
        this.INSCLAIMCRIBURN = INSCLAIMCRIBURN;
    }

    public void setINSCLAIMCRITICAL(String INSCLAIMCRITICAL) {
        this.INSCLAIMCRITICAL = INSCLAIMCRITICAL;
    }

    public void setINSCLAIMDAY(String INSCLAIMDAY) {
        this.INSCLAIMDAY = INSCLAIMDAY;
    }

    public void setINSCLAIMDIE(String INSCLAIMDIE) {
        this.INSCLAIMDIE = INSCLAIMDIE;
    }

    public void setINSCLAIMDISABLE(String INSCLAIMDISABLE) {
        this.INSCLAIMDISABLE = INSCLAIMDISABLE;
    }

    public void setINSCLAIMDISABLEA(String INSCLAIMDISABLEA) {
        this.INSCLAIMDISABLEA = INSCLAIMDISABLEA;
    }

    public void setINSCLAIMDISABLEP(String INSCLAIMDISABLEP) {
        this.INSCLAIMDISABLEP = INSCLAIMDISABLEP;
    }

    public void setINSCLAIMFIRST(String INSCLAIMFIRST) {
        this.INSCLAIMFIRST = INSCLAIMFIRST;
    }

    public void setINSCLAIMMED(String INSCLAIMMED) {
        this.INSCLAIMMED = INSCLAIMMED;
    }

    public void setINSCLAIMMEDSELF(String INSCLAIMMEDSELF) {
        this.INSCLAIMMEDSELF = INSCLAIMMEDSELF;
    }

    public void setINSCLAIMRECOVER(String INSCLAIMRECOVER) {
        this.INSCLAIMRECOVER = INSCLAIMRECOVER;
    }

    public void setINSCLAIMSPEMONY(String INSCLAIMSPEMONY) {
        this.INSCLAIMSPEMONY = INSCLAIMSPEMONY;
    }

    public void setINSCLAIMSURGERYH(String INSCLAIMSURGERYH) {
        this.INSCLAIMSURGERYH = INSCLAIMSURGERYH;
    }

    public void setINSCLAIMSURGERYN(String INSCLAIMSURGERYN) {
        this.INSCLAIMSURGERYN = INSCLAIMSURGERYN;
    }

    public void setINSCOMPANY(String INSCOMPANY) {
        this.INSCOMPANY = INSCOMPANY;
    }

    public void setINSCONTEXPIRDD(String INSCONTEXPIRDD) {
        this.INSCONTEXPIRDD = INSCONTEXPIRDD;
    }

    public void setINSCONTEXPIRMM(String INSCONTEXPIRMM) {
        this.INSCONTEXPIRMM = INSCONTEXPIRMM;
    }

    public void setINSCONTEXPIRYY(String INSCONTEXPIRYY) {
        this.INSCONTEXPIRYY = INSCONTEXPIRYY;
    }

    public void setINSDATAID(String INSDATAID) {
        this.INSDATAID = INSDATAID;
    }

    public void setINSID(String INSID) {
        this.INSID = INSID;
    }

    public void setINSLIFETYPE(String INSLIFETYPE) {
        this.INSLIFETYPE = INSLIFETYPE;
    }

    public void setINSNAME(String INSNAME) {
        this.INSNAME = INSNAME;
    }

    public void setINSPAYTYP(String INSPAYTYP) {
        this.INSPAYTYP = INSPAYTYP;
    }

    public void setINSPOLICYTYPE(String INSPOLICYTYPE) {
        this.INSPOLICYTYPE = INSPOLICYTYPE;
    }

    public void setINSPRM(String INSPRM) {
        this.INSPRM = INSPRM;
    }

    public void setINSPROCATE(String INSPROCATE) {
        this.INSPROCATE = INSPROCATE;
    }

    public void setINSPRODUCT(String INSPRODUCT) {
        this.INSPRODUCT = INSPRODUCT;
    }

    public void setINSREALTION(String INSREALTION) {
        this.INSREALTION = INSREALTION;
    }

    public void setINSRECEIVEDD(String INSRECEIVEDD) {
        this.INSRECEIVEDD = INSRECEIVEDD;
    }

    public void setINSRECEIVEMM(String INSRECEIVEMM) {
        this.INSRECEIVEMM = INSRECEIVEMM;
    }

    public void setINSRECEIVEYY(String INSRECEIVEYY) {
        this.INSRECEIVEYY = INSRECEIVEYY;
    }

    public void setINSSEX(String INSSEX) {
        this.INSSEX = INSSEX;
    }

    public void setINSSTATUS(String INSSTATUS) {
        this.INSSTATUS = INSSTATUS;
    }

    public void setINSSTATUSDD(String INSSTATUSDD) {
        this.INSSTATUSDD = INSSTATUSDD;
    }

    public void setINSSTATUSMM(String INSSTATUSMM) {
        this.INSSTATUSMM = INSSTATUSMM;
    }

    public void setINSSTATUSYY(String INSSTATUSYY) {
        this.INSSTATUSYY = INSSTATUSYY;
    }

    
    
    
}
