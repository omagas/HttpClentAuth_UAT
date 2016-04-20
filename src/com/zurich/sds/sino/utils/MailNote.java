/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zurich.sds.sino.utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.EmbeddedObject;
import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.RichTextItem;
import lotus.domino.Session;
//import sun.util.logging.resources.logging;
import org.apache.log4j.Logger;

/**
 *
 * @author louie.zheng
 */
public class MailNote {
private static Logger logger = Logger.getLogger(MailNote.class);
    
	private String host;
	private String hostPort;
	private String user; //撣唾?
	private String pwd; //撖?Ⅳ
	private String nsf;
	private String subject; //璅??
	private List<String> sendTo; //?嗡辣??	
        private List<String> copyTo;
	private List<String> replyTo;//???
	private String body;//?批捆

	private Database mailDatabase;
	private Document mailDoc;
	private Vector<String> attachList;
	private Session session;
    /**
     * @param args the command line arguments
     */

	public MailNote(){
		try {
			// ??roperties??身摰??
			Properties properties = PropertiesTool.getProperties("default.properties");
                        System.out.println((String)properties.get("host"));
			setHost((String)properties.get("host"));
			setHostPort((String)properties.get("port"));
			setUser((String)properties.get("userName"));
			setPwd((String)properties.get("passWord"));
			setNsf((String)properties.get("nsf"));

			
//			this.Host=NotesMailConstant.NOTESHOST;
//			this.HostPort=NotesMailConstant.NOTESHOSTPORT;
//			this.User=NotesMailConstant.NOTESUSER;
//			this.Pwd=NotesMailConstant.NOTESPWD;
//			this.Nsf=NotesMailConstant.NOTESNSF;
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}   

	public void getNotesConnect(){
		try {
			System.out.println("Email Host port ---->" + host + ":" + hostPort);
			System.out.println("Email User  Pwd ---->" + user + " " + pwd);
			System.out.println("Email Nsf       ---->" + nsf);

			String sior = NotesFactory.getIOR(host + ":" + hostPort);

			if(null == session || !session.isValid()){
				session = NotesFactory.createSessionWithIOR(sior, user, pwd);
				System.out.println("Email session   ---->" + session.isValid());
				mailDatabase = session.getDatabase(host, nsf);
				System.out.println("Email Database  ---->" + mailDatabase.isOpen());
			}

		} catch(Exception e){
			//log.debug("Exceptiont ---->" + e.toString());
			System.out.println("Exceptiont ---->" + e.toString());
		}
	}
        
	public String sendNotes(){
		String returnValue = null;
		try {
//			Session s;
//			log.debug("Email Host port ---->"+Host+":"+HostPort);
//			log.debug("Email User  Pwd ---->"+User+" " +Pwd);
//			log.debug("Email Nsf       ---->"+Nsf);
//
//			String sior =NotesFactory.getIOR(Host+":"+HostPort);
//			s = NotesFactory.createSessionWithIOR(sior,User,Pwd);
//
//			mailDatabase=s.getDatabase(Host,Nsf);
			getNotesConnect();

			mailDoc = mailDatabase.createDocument();

			mailDoc.replaceItemValue("Form", "Memo");
			mailDoc.replaceItemValue("Subject", subject);//"(???銝???芯?璆凋葉敹?"
			mailDoc.replaceItemValue("SendTo", sendTo);
			mailDoc.replaceItemValue("CopyTo", copyTo);
			mailDoc.replaceItemValue("ReplyTo", replyTo);

//			log.debug("mailDoc.getItemValue(\"SendTo\");" + mailDoc.getItemValue("SendTo"));
//			log.debug("mailDoc.getItemValue(\"CopyTo\");" + mailDoc.getItemValue("CopyTo"));
//			log.debug("mailDoc.getItemValue(\"ReplyTo\");" + mailDoc.getItemValue("ReplyTo"));


			/*
			 * ?仿?隞嗆窒?其????rtiBody??????mail??ody憭??
			 * (See attached file: xxx.xx)
			 * ???璅???典?xml?潛?憟賢?????????ody???摰寡???			 * ?箔?閫?迨???嚗?停?血?閮凋???tiAttach?蹂??暸?隞?			 * */
			RichTextItem rtiAttach = mailDoc.createRichTextItem("attach");
			List<String> loseFile = new ArrayList<String>();
			if (attachList != null) {
				for(String attach : attachList){
					File file = new File(attach);

					if(!file.exists()){
						loseFile.add(file.getName());
					}else{
						rtiAttach.embedObject(EmbeddedObject.EMBED_ATTACHMENT, null, attach, "attach");
					}
				}
			}

			RichTextItem rtiBody;
			rtiBody = mailDoc.createRichTextItem("body");
			rtiBody.appendText(body);

			if(loseFile.size() != 0){
				rtiBody.addNewLine();
				rtiBody.addNewLine();
				rtiBody.appendText("找不到" + loseFile.toString() + "檔案無法寄出");
			}

			mailDoc.save(true, true);

			mailDoc.send();

//			mailDatabase.recycle();
		} catch(Exception e){
			System.out.println("Exceptiont ---->" + e.toString());
			returnValue = e.toString();
		}
//		finally{
//			try {
//				if(mailDatabase!=null){
//					mailDatabase.recycle();
//				}
//				if(s!=null){
//					s.recycle();
//				}
//
//			} catch (NotesException e) {
//				e.printStackTrace();
//			}
//		}

		return returnValue;
	} 
        
	public void setHost(String host) {
		this.host = host;
	}

	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setNsf(String nsf) {
		this.nsf = nsf;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSendTo(List<String> sendTo) {
		this.sendTo = sendTo;
	}

	public void setCopyTo(List<String> copyTo) {
		this.copyTo = copyTo;
	}

	public void setReplyTo(List<String> replyTo) {
		this.replyTo = replyTo;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setAttachList(Vector<String> attachList) {
		this.attachList = attachList;
	}        
        
    public void mailServer(Boolean flag,String orderSeq) {
        // TODO code application logic here
		Vector<String> userV = new Vector<String>();
                logger.info("Notes測試MailServer");
//		List userA=new ArrayList();

		userV.add("louie.zheng@zurich.com");
                userV.add("sunny.wu@zurich.com");
                userV.add("sandy.laio@zurich.com");
                userV.add("ian.yang@zurich.com");
                //userV.add("omaga1130@gmail.com");

//		userV.add("triumph.developer@zurich.com");
//		userV.add("steven.wang@zurich.com");

		MailNote NM = new MailNote();

		NM.setHost("192.168.128.31");
		NM.setHostPort("63148");
                NM.setUser("sds twz");
		NM.setPwd("zurich");
		NM.setNsf("mail/twzst9.nsf");
                
//		NM.setUser("twztd");
//		NM.setPwd("triumph");
//		NM.setNsf("mail/twztd.nsf");                
		
//                NM.setUser("twzzt");
//		NM.setPwd("97uNEchU");
//		NM.setNsf("mail/twzzt.nsf");
                
                if(flag){     
 		NM.setSubject("(測試勿理會)永豐PDF轉檔成功:"+orderSeq);
		NM.setSendTo(userV);
		NM.setBody("PDF轉檔成功:"+orderSeq);
                Vector<String> VA=new Vector();
                VA.addElement("./Upload_tmp/"+orderSeq+".pdf");
                NM.setAttachList(VA);
                
                }else{
		NM.setSubject("(測試勿理會)永豐PDF轉檔失敗:"+orderSeq);
		NM.setSendTo(userV);
		NM.setBody("PDF轉檔失敗，請洽相關人員處理:"+orderSeq);                  
                }
                
                
		String sendNotes = NM.sendNotes();



		if(sendNotes==null || sendNotes.equals(null)){
			System.out.println("Send Mail Success");
		}else{
			System.out.println(sendNotes);
		}        
        
    }

    
}
