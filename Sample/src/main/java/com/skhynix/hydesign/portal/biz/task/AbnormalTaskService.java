package com.skhynix.hydesign.portal.biz.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhynix.hydesign.portal.biz.status.abnormal.monitor.MonitorDao;
import com.skhynix.hydesign.portal.biz.status.abnormal.monitor.MonitorService;
import com.skhynix.hydesign.portal.biz.status.abnormal.scenario.ScenarioDao;
import com.skhynix.hydesign.portal.biz.status.abnormal.status.AbStatusService;
import com.skhynix.hydesign.portal.common.constants.PortalConstants;
import com.skhynix.hydesign.portal.common.util.PortalUtil;

/**
 * 통계 > 이상징후 > 이상징후 취합 Task
 * 
 * @author sonys75
 * @version 1.0
 * @since
 * @created 2017. 08. 30.
 */
@Service
public class AbnormalTaskService {

    /**
     * logger
     */
/*성능개선안
    private final Logger logger = LoggerFactory.getLogger(getClass());
*/
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
     * Configuration
     */
    @Autowired
    private Configuration configuration;
    
	/**
     * 시나리오 DAO
     */
    @Autowired
    private ScenarioDao scenarioDao;
    
    /**
     * 모니터링 서비스
     */
    @Autowired
    private MonitorService monitorService;
    
    /**
     * 모니터링 DAO
     */
    @Autowired
    private MonitorDao monitorDao;

    /**
     * 모니터링 서비스
     */
    @Autowired
    private AbStatusService statusService;
    
    /**
     * MailTask DAO
     */
    @Autowired
    private MailTaskDao mailTaskDao;
 
    /**
     * Default Constructor
     */
    public AbnormalTaskService() {
        // Default Constructor
    }

    public static String taskExecute = "N";
    
    /**
     * 이상징후 모니터링 결과 실행
     * 
     * @return 등록된 row 개수
     */
    @SuppressWarnings("unchecked")
	public void startAbnormalHist() {
    	//logger.info("\n===== Abnormal Hist Start =====");
    	long startTime = System.currentTimeMillis(); 
    	
    	List<Object> scenarioList = scenarioDao.scenarioList(new HashMap<String, Object>());
    	
    	int nCnt = 0;
    	for(int i=0;i<scenarioList.size();i++){
    		Map<String, Object> pMap = (Map<String, Object>) scenarioList.get(i);

    		if("ON".equals(pMap.get("SCENARIO_ENABLE_FG"))){
	    		List<Object> abnormalList = monitorDao.selectAbnormalHist(pMap);

	    		for(int j=0;j<abnormalList.size();j++){
	    			Map<String, Object> resultMap = (Map<String, Object>) abnormalList.get(j);
	    			
	    			nCnt += monitorDao.insertAbnormalHist(resultMap);
	    		}
    		}
    	}
    	
    	long endTime = System.currentTimeMillis();
    	
    	//logger.info("\nAbnormal Execute Time : "+Float.toString((float)(endTime - startTime)/1000) +" sec"
    	//		   +"\nAbnormal Insert Count : "+nCnt);
    	
    	//logger.info("\n===== Abnormal Hist End =====");
    }
    
    /**
     * 이상징후 모니터링 메일 발송 실행
     * 
     * @return 등록된 row 개수
     */
    @SuppressWarnings("unchecked")
	public void startAbnormalMail() {
    	//logger.info("===== Abnormal Mail Start =====");
    	long startTime = System.currentTimeMillis(); 
	    //이미지 생성 카운트
    	int nProcessCnt = 0;
    	
    	//메일 카운트
    	int nMailCnt = 0;
    	
    	//전체 카운트
    	int nTotalCnt = 0;
    	
    	//메일 발송시 이용할 서버주소
    	String sServerUrl = configuration.getString("portal.abnormal.mail.send.server");
    	
    	//운영모드여부
    	boolean bRealServer = false;
    	if(sServerUrl.indexOf("design.")>0){
    		bRealServer = true;
    	}
    	
    	StringBuffer sbLog = new StringBuffer();
    	
    	try{
	    	//메일 이미지 업로드 디렉토리
	    	String sImageUploadPath = configuration.getString("portal.file.upload.path") + "/abnormalTemp";
	
	    	//업로드 디렉토리가 없을 경우 디렉토리 생성
	    	File fDir = new File(sImageUploadPath);
	    	if(!fDir.isDirectory()){
	    		fDir.mkdirs();
	    	}
	    	
	    	//PhantomJS 의 서버쪽 위치
	    	String phantomRoot = this.getClass().getResource("/email/abnormal").toString();
	    	
	    	if(sServerUrl.indexOf("localhost")>=0){
	    		phantomRoot = phantomRoot.substring(phantomRoot.indexOf("/")+1);
	    	}else{
	    		phantomRoot = phantomRoot.substring(phantomRoot.indexOf(":")+1);
	    	}
	    	
		    List<Object> mailList = monitorService.selectOrgList(new HashMap<String, Object>());
		    
		    if(mailList != null)
		    	nTotalCnt = mailList.size();

	    	for(int i=0;i<mailList.size();i++){
	    		sbLog.setLength(0);
	    		
	    		long startSubTime = System.currentTimeMillis(); 
	    		Map<String, Object> pMap = (Map<String, Object>) mailList.get(i);
	    		
	    		String sPart = "";
	    		String sFile = "";
	    		String[] cmdArray = new String[9];

	    		if(sServerUrl.indexOf("localhost")>=0){
	    			cmdArray[0] = phantomRoot + "windows/phantomjs"; // 로컬용
	    			cmdArray[1] = phantomRoot + "makeAbnormalReport.js"; // 로컬용
					cmdArray[2] = sServerUrl; // abnormalMail.jsp 의 서버 URL, 도메인
					cmdArray[3] = (String) pMap.get("PART");	//부서 프로젝트 구분
					cmdArray[4] = (String) pMap.get("ORG_CD"); 	//프로젝트 ID
					cmdArray[5] = sImageUploadPath+"/"+(String) pMap.get("PART")+"_"+(String) pMap.get("ORG_CD")+"_"+Long.toString(System.currentTimeMillis()) +".png"; //저장할 이미지명
					cmdArray[6] = "";
					cmdArray[7] = "";
					cmdArray[8] = "";
					sFile = cmdArray[5];
	    		}else{
	    			cmdArray[0] = phantomRoot + "linux/runPhantomjs.sh"; // 개발, 운영용
	    			cmdArray[1] = phantomRoot + "linux"; // 개발, 운영용
	    			cmdArray[2] = phantomRoot + "makeAbnormalReport.js"; // 로컬용
					cmdArray[3] = sServerUrl; // abnormalMail.jsp 의 서버 URL, 도메인
					cmdArray[4] = (String) pMap.get("PART");	//부서 프로젝트 구분
					cmdArray[5] = (String) pMap.get("ORG_CD"); 	//프로젝트 ID
					cmdArray[6] = sImageUploadPath+"/"+(String) pMap.get("PART")+"_"+(String) pMap.get("ORG_CD")+"_"+Long.toString(System.currentTimeMillis()) +".png"; //저장할 이미지명
					cmdArray[7] = "";
					cmdArray[8] = "";
					sFile = cmdArray[6];
	    		}

	    		sbLog.append("\nsImageUploadPath : "+sImageUploadPath);
	    		sbLog.append("\nphantomRoot : "+phantomRoot);
	    		sbLog.append("\nnTotalCnt : "+nTotalCnt);
	    		sbLog.append("\ncmdArray[0] : "+cmdArray[0]);
	    		sbLog.append("\ncmdArray[1] : "+cmdArray[1]);
	    		sbLog.append("\ncmdArray[2] : "+cmdArray[2]);
	    		sbLog.append("\ncmdArray[3] : "+cmdArray[3]);
	    		sbLog.append("\ncmdArray[4] : "+cmdArray[4]);
	    		sbLog.append("\ncmdArray[5] : "+cmdArray[5]);
	    		sbLog.append("\ncmdArray[6] : "+cmdArray[6]);
	    		sbLog.append("\ncmdArray[7] : "+cmdArray[7]);
	    		sbLog.append("\ncmdArray[8] : "+cmdArray[8]);
	    		
				File file = new File(sFile);
				
				try {
					Process process = Runtime.getRuntime().exec(cmdArray);

					sbLog.append("\nProcess exitValue : "+ process.hashCode());
					
					nProcessCnt++;

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						logger.error("InterruptedException : "+e.toString());
						if(file.exists()){
			        		file.delete();
			        	}
						continue;
					}
				} catch (IOException e) {
					logger.error("IOException : "+e.toString());
					if(file.exists()){
		        		file.delete();
		        	}
					continue;
				}
				
				if("ORG".equals(pMap.get("PART").toString())){
					sPart = "부서";
				}else if("PRJ".equals(pMap.get("PART").toString())){
					sPart = "프로젝트";
				}
				
				String sToMail = (String)pMap.get("EMAIL_ADDR");
				
				String sSubject = "";
				
				if(!bRealServer){
					sSubject += "[테스트] ";
				}
				
				String sStartDay = PortalUtil.getDateFormat(-6, "yyyy-MM-dd");
				String sEndDay = PortalUtil.getDateFormat(0, "yyyy-MM-dd");
				
				sSubject += pMap.get("ORG_NM") +" "+sPart+"의 이상징후 알림 메일입니다.";
				String sContent = "<div><a href=\""+sServerUrl+"/status/abnormal/status/list?mail=Y&part="+pMap.get("PART")+"&prjid="+pMap.get("ORG_CD")+"&startDt="+sStartDay+"&endDt="+sEndDay+"\" target=\"_blank\"><img src=\"cid:image\"></a></div>";
	
				sbLog.append("\nAbnormal Mail Link : "+sServerUrl+"/status/abnormal/status/list?mail=Y&part="+pMap.get("PART")+"&prjid="+pMap.get("ORG_CD")+"&startDt="+sStartDay+"&endDt="+sEndDay);
				
				if(!bRealServer)
				//메일발송
				if(nMailCnt<3)
					
				try {
					//프로젝트 PL 및 부서장 메일발송
					//String sResult = this.sendEmail(null,sToMail,sSubject,sContent,sFile);
					
					//관리자 메일링 그룹 메일발송
					String sResult = this.sendEmailList(sSubject,sContent,sFile);
					
					if("SS".equals(sResult)){
						nMailCnt++;
					}
				} catch (UnsupportedEncodingException e) {
					logger.error(e.toString());
				} catch (IOException e) {
					logger.error(e.toString());
				}
				
				//파일삭제
	        	if(file.exists()){
	        		file.delete();
	        	}
	        	
	        	sbLog.append("\n------------------------------------------------");
	        	sbLog.append("\nAbnormal Mail Sub Execute Time : "+Float.toString((float)(System.currentTimeMillis() - startSubTime)/1000) +" sec"
				 		    +"\nAbnormal Mail Count : "+ nProcessCnt +"/"+ nTotalCnt
						    +"\nAbnormal Mail Address : "+ sToMail
						    +"\nAbnormal Mail Title : "+ sSubject
						    +"\nAbnormal Mail Attach File : "+ sFile);
	        	
	        	logger.debug("\n"+ sbLog.toString());
	        	sbLog.setLength(0);
	        	
	        	//logger.info("Abnormal Mail Task (Send / Process / Total) : ( "+ nMailCnt +" / "+ nProcessCnt +" / "+ nTotalCnt +" ) ");
	    	}
	    } catch (Exception e) {
	    	logger.error(e.toString());
		}
    	
    	long endTime = System.currentTimeMillis();
	    
    	//logger.info("\nAbnormal Mail Execute Time : "+Float.toString((float)(endTime - startTime)/1000) +" sec"
		//		   +"\nAbnormal Mail Send Count : "+nMailCnt
		//		   +"\nAbnormal Mail Image Create Count : "+nProcessCnt);
    	
    	//logger.info("===== Abnormal Mail End =====");

    	//이상징후 집중감지 대상 현황 메일 발송 실행
    	startAbnormalTargetMail();
    }
    
    /**
     * 이상징후 대시보드 메일 발송 실행
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public void startDashBoardMail() {
    	
    	if(AbnormalTaskService.taskExecute.equals("Y")){
    		//logger.info("===== Abnormal Dashboard Mail Task Already Execute =====");
    		return;
    	}
    	
    	AbnormalTaskService.taskExecute = "Y";
    	
    	//logger.info("===== Abnormal Dashboard Mail Start =====");

    	long startTime = System.currentTimeMillis(); 
	    //이미지 생성 카운트
    	int nProcessCnt = 0;
    	
    	//메일 카운트
    	int nMailCnt = 0;
    	
    	//전체 카운트
    	int nTotalCnt = 0;
    	
    	//메일 발송시 이용할 서버주소
    	String sServerUrl = configuration.getString("portal.abnormal.mail.send.server");
    	
    	//운영모드여부
    	boolean bRealServer = false;
    	if(sServerUrl.indexOf("design.")>0){
    		bRealServer = true;
    	}
    	
    	StringBuffer sbLog = new StringBuffer();
    	
    	try{
	    	//메일 이미지 업로드 디렉토리
	    	String sImageUploadPath = configuration.getString("portal.file.upload.path") + "/abnormalTemp";
	
	    	//업로드 디렉토리가 없을 경우 디렉토리 생성
	    	File fDir = new File(sImageUploadPath);
	    	if(!fDir.isDirectory()){
	    		fDir.mkdirs();
	    	}
	    	
	    	//PhantomJS 의 서버쪽 위치
	    	String phantomRoot = this.getClass().getResource("/email/abnormal").toString();
	    	
	    	if(sServerUrl.indexOf("localhost")>=0){
	    		phantomRoot = phantomRoot.substring(phantomRoot.indexOf("/")+1);
	    	}else{
	    		phantomRoot = phantomRoot.substring(phantomRoot.indexOf(":")+1);
	    	}
	    	
	    	//전송할 메일 정보
	    	List<Object> mailInfoList = statusService.selectDashMailTask(new HashMap<String, Object>());
	    	
	    	if(mailInfoList.size()<=0){
	    		//logger.info("\nAbnormal Dashboard Mail is Nothing!");
	    	}

	    	for(int i=0;i<mailInfoList.size();i++){
	    		sbLog.setLength(0);
	    		long startSubTime = System.currentTimeMillis(); 
	    		
	    		Map<String, Object> pMap = (Map<String, Object>) mailInfoList.get(i);
	    		
	    		nTotalCnt += Integer.parseInt(pMap.get("CNT").toString());

	    		String sPart = (String) pMap.get("CONTENT_PART");
	    		String sPrjID = (String) pMap.get("PRJID");
	    		String sFile = "";
	    		String sStartDay = (String)pMap.get("START_DT");
				String sEndDay = (String)pMap.get("END_DT");
				
	    		String[] cmdArray = new String[9];

	    		if(sServerUrl.indexOf("localhost")>=0){
	    			cmdArray[0] = phantomRoot + "windows/phantomjs"; // 로컬용
	    			cmdArray[1] = phantomRoot + "makeAbnormalReport.js"; // 로컬용
					cmdArray[2] = sServerUrl; // abnormalMail.jsp 의 서버 URL, 도메인
					cmdArray[3] = sPart;	//부서 프로젝트 구분
					cmdArray[4] = sPrjID; 	//프로젝트 ID
					cmdArray[5] = sImageUploadPath+"/"+sPart+"_"+sPrjID+"_"+Long.toString(System.currentTimeMillis()) +".png"; //저장할 이미지명
					cmdArray[6] = sStartDay;
					cmdArray[7] = sEndDay;
					cmdArray[8] = "";
					sFile = cmdArray[5];
	    		}else{
	    			cmdArray[0] = phantomRoot + "linux/runPhantomjs.sh"; // 개발, 운영용
	    			cmdArray[1] = phantomRoot + "linux"; // 개발, 운영용
	    			cmdArray[2] = phantomRoot + "makeAbnormalReport.js"; // 로컬용
					cmdArray[3] = sServerUrl; // abnormalMail.jsp 의 서버 URL, 도메인
					cmdArray[4] = sPart;	//부서 프로젝트 구분
					cmdArray[5] = sPrjID; 	//프로젝트 ID
					cmdArray[6] = sImageUploadPath+"/"+sPart+"_"+sPrjID+"_"+Long.toString(System.currentTimeMillis()) +".png"; //저장할 이미지명
					cmdArray[7] = sStartDay;
					cmdArray[8] = sEndDay;
					sFile = cmdArray[6];
	    		}

	    		sbLog.append("\nsImageUploadPath : "+sImageUploadPath);
	    		sbLog.append("\nphantomRoot : "+phantomRoot);
	    		sbLog.append("\nnTotalCnt : "+nTotalCnt);
	    		sbLog.append("\ncmdArray[0] : "+cmdArray[0]);
	    		sbLog.append("\ncmdArray[1] : "+cmdArray[1]);
	    		sbLog.append("\ncmdArray[2] : "+cmdArray[2]);
	    		sbLog.append("\ncmdArray[3] : "+cmdArray[3]);
	    		sbLog.append("\ncmdArray[4] : "+cmdArray[4]);
	    		sbLog.append("\ncmdArray[5] : "+cmdArray[5]);
	    		sbLog.append("\ncmdArray[6] : "+cmdArray[6]);
	    		sbLog.append("\ncmdArray[7] : "+cmdArray[7]);
	    		sbLog.append("\ncmdArray[8] : "+cmdArray[8]);
				
				File file = new File(sFile);
				
				try {
					Process process = Runtime.getRuntime().exec(cmdArray);
					sbLog.append("\nProcess hashCode : "+ process.hashCode());
					nProcessCnt++;
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						logger.error("InterruptedException : "+e.toString());
						if(file.exists()){
			        		file.delete();
			        	}
						continue;
					}
				} catch (IOException e) {
					logger.error("IOException : "+e.toString());
					if(file.exists()){
		        		file.delete();
		        	}
					continue;
				}
 
				String sFromMail = (String)pMap.get("EMAIL_ADDR");
				String sSubject = ""; 
				
				if(!bRealServer){
					sSubject += "[테스트] ";
				}

				String sContent = "<div><a href=\""+sServerUrl+"/status/abnormal/status/list?mail=Y&part="+sPart+"&prjid="+sPrjID+"&startDt="+sStartDay+"&endDt="+sEndDay+"\" target=\"_blank\"><img src=\"cid:image\"></a></div>";
				
				sbLog.append("\nAbnormal Mail Link : "+sServerUrl+"/status/abnormal/status/list?mail=Y&part="+sPart+"&prjid="+sPrjID+"&startDt="+sStartDay+"&endDt="+sEndDay);
				sSubject += (String)pMap.get("MAIL_SUBJECT");
				
				sbLog.append("\n------------------------------------------------");
	        	sbLog.append("\nAbnormal Dashboard Mail From Address : "+ sFromMail
					    	+"\nAbnormal Dashboard Mail Title : "+ sSubject
					    	+"\nAbnormal Dashboard Mail Attach File : "+ sFile);
	        	
		    	List<Object> mailReceiveList = statusService.selectDashMailReceiver(pMap.get("MAIL_ID").toString());
	    	
		    	if(mailReceiveList.size()<=0){
		    		sbLog.append("\nAbnormal Dashboard Mail Receiver is Nothing!");
		    	}
		    	
				//전송할 수신자 정보
				for(int j=0;j<mailReceiveList.size();j++){
					Map<String, Object> receiveMap = (Map<String, Object>) mailReceiveList.get(j);
					String sToMail = (String)receiveMap.get("EMAIL_ADDR");
					receiveMap.put("mailId",receiveMap.get("MAIL_ID"));
					receiveMap.put("mailSeq",receiveMap.get("MAIL_SEQ"));
					
					//메일발송
					receiveMap.put("sendResult","F");
					try {
						String sResult = this.sendEmail(sFromMail,sToMail,sSubject,sContent,sFile);
						
						if("SS".equals(sResult)){
							receiveMap.put("sendResult","Y");
							nMailCnt++;
							sbLog.append("\nAbnormal Dashboard Mail To Address : ["+nMailCnt+"] "+ sToMail);
						}
					} catch (UnsupportedEncodingException e) {
						logger.error(e.toString());
					} catch (IOException e) {
						logger.error(e.toString());
					}
					
					statusService.updateMailSendResult(receiveMap);
				}
				sbLog.append("\n------------------------------------------------");
				//나머지 에러로 바꿈
				pMap.put("mailId", pMap.get("MAIL_ID"));
				statusService.updateMailSendError(pMap);
				
				//파일삭제
	        	if(file.exists()){
	        		file.delete();
	        	}

	        	sbLog.append("\nAbnormal Dashboard Mail Sub Execute Time : "+Float.toString((float)(System.currentTimeMillis() - startSubTime)/1000) +" sec");

	        	logger.debug("\n"+ sbLog.toString());
	        	sbLog.setLength(0);
	    	}

	    } catch (Exception e) {
	    	logger.error(e.toString());
		}

    	//logger.info("\nAbnormal Dashboard Mail Execute Time : "+Float.toString((float)(System.currentTimeMillis() - startTime)/1000) +" sec"
		//		   +"\nAbnormal Mail Task (Send / Process / Total) : ( "+ nMailCnt +" / "+ nProcessCnt +" / "+ nTotalCnt +" ) ");
    	
    	//logger.info("===== Abnormal Dashboard Mail End =====");
    	
    	AbnormalTaskService.taskExecute = "N";
    }

    /**
     * 이상징후 집중감지 대상 현황 메일 발송 실행
     * 
     * @return
     */
    
	public void startAbnormalTargetMail() {
    	StringBuffer sbLog = new StringBuffer();
    	
    	//logger.info("===== Abnormal Target Mail Start =====");

    	sbLog.append("\n------------------------------------------------");
    	
    	long startTime = System.currentTimeMillis(); 

    	//메일 카운트
    	int nMailCnt = 0;
    	
    	//전체 카운트
    	int nTotalCnt = 0;
    	
    	//메일 발송시 이용할 서버주소
    	String sServerUrl = configuration.getString("portal.abnormal.mail.send.server");
    	String sMenuUrl = "/status/abnormal/management/concenDetect";
    	//운영모드여부
    	boolean bRealServer = false;
    	if(sServerUrl.indexOf("design.")>0){
    		bRealServer = true;
    	}
    	sbLog.append("\nTarget Mail RealServer : "+ bRealServer);
    	
    	Map<String, Object> pMap = new HashMap<String, Object>();
    	Map<String, Object> tmpMap = new HashMap<String, Object>();
    	
    	//검색 시작일, 종료일
    	String sStartDay = PortalUtil.getDateFormat(-6, "yyyy-MM-dd");
		String sEndDay = PortalUtil.getDateFormat(0, "yyyy-MM-dd");
    	
		pMap.put("startDt", sStartDay);
		pMap.put("endDt", sEndDay);
		
		//검색 주차
		tmpMap = monitorService.selectMonthWeek(pMap);
		
		pMap.put("monWeek", tmpMap.get("MON_WEEK"));
		pMap.put("searchTerm", tmpMap.get("SEARCH_TERM"));
		
		sbLog.append("\nTarget Mail startDt : "+ sStartDay);
		sbLog.append("\nTarget Mail endDt : "+ sEndDay);
		sbLog.append("\nTarget Mail monWeek : "+ pMap.get("monWeek"));
		sbLog.append("\nTarget Mail searchTerm : "+ pMap.get("searchTerm"));

		StringBuffer emailContents = new StringBuffer();
        BufferedReader br = null;

        try {
			ClassPathResource emailTemplate = new ClassPathResource("/email/vwsmail/mail_abnormal.html");
		
			br = new BufferedReader(new InputStreamReader(emailTemplate.getInputStream(), "UTF-8"));
	        String line;
	        while ((line = br.readLine()) != null) {
	            emailContents.append(line);
	        }
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
        /*
		//기간별 검색된 집중감지 대상자가 속한 부서 및 프로젝트 목록
		List<Object> mailReceiveList = monitorService.selectTargetOrgList(pMap);
		
		nTotalCnt = mailReceiveList.size();
		
		if(mailReceiveList.size()<=0){
    		sbLog.append("\nTarget Mail Receiver is Nothing!");
    	}
	
		//메일 전송할 수신자 정보
		for(int i=0;i<mailReceiveList.size();i++){
			sbLog.append("\n▽▽▽▽▽▽▽▽▽▽▽▽");
			String sSubject = "";
			String sMailDetailSubject = ""; 
			String sContent = emailContents.toString();
			
			if(!bRealServer){
				sSubject += "[테스트] ";
			}
			
			Map<String, Object> receiveMap = (Map<String, Object>) mailReceiveList.get(i);
			String sPart = (String)receiveMap.get("PART");
			String sPrjid = (String)receiveMap.get("ORG_CD");
			String sToMail = (String)receiveMap.get("EMAIL_ADDR");
			String sOrgNm = (String)receiveMap.get("ORG_NM");
			
			sbLog.append("\nTarget Mail part : "+ sPart);
			sbLog.append("\nTarget Mail prjid : "+ sPrjid);
			sbLog.append("\nTarget Mail Receiver : "+ sToMail);
			pMap.put("part", sPart);
			pMap.put("prjid", sPrjid);
			
			sContent = sContent.replace("${mainUrl}", sServerUrl);
			sContent = sContent.replace("${menuUrl}", sMenuUrl);
			
			if("ORG".equals(sPart)){
				sSubject += sOrgNm +" 부서의 집중감지 대상 감지 현황 알림 메일입니다.";
				sMailDetailSubject = "<strong>"+ pMap.get("monWeek")+" "+ sOrgNm +" 부서의 집중감지 대상 감지 현황입니다. ("+pMap.get("searchTerm")+")</strong><br>";
				
			}else if("PRJ".equals(sPart)){
				sSubject += sOrgNm +" 프로젝트의 집중감지 대상 감지 현황 알림 메일입니다.";
				sMailDetailSubject = "<strong>"+ pMap.get("monWeek")+" "+ sOrgNm +" 프로젝트의 집중감지 대상 감지 현황입니다. ("+pMap.get("searchTerm")+")</strong><br>";
			}
			
			sMailDetailSubject += "※ 상세보기를 클릭하면 이상징후 집중감지 화면으로 이동합니다.";
			sContent = sContent.replace("${emailDtlTitle}", sMailDetailSubject);

			StringBuffer sbDetailList = selectTargetDetailList(pMap);
			sContent = sContent.replace("${emailDtlDesc}", sbDetailList.toString());
			
			if(!bRealServer)
			//메일발송
			if(nMailCnt<3)
				
			try {
				String sResult = this.sendEmail(null,sToMail,sSubject,sContent,null);
				if("SS".equals(sResult)){
					nMailCnt++;
				}
			} catch (UnsupportedEncodingException e) {
				logger.error(e.toString());
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}
		
		if(!bRealServer)
		//메일발송
		if(nMailCnt<3)
			
		try {
			String sResult = this.sendEmail(null,sToMail,sSubject,sContent,null);
			if("SS".equals(sResult)){
				nMailCnt++;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error(e.toString());
		}
		
		*/

        String sSubject = "";
		String sMailDetailSubject = ""; 
		String sContent = emailContents.toString();
		
		if(!bRealServer){
			sSubject += "[테스트] ";
		}

		sContent = sContent.replace("${mainUrl}", sServerUrl);
		sContent = sContent.replace("${menuUrl}", sMenuUrl);
		
		sSubject += pMap.get("monWeek") +" 집중감지 대상 감지 현황 알림 메일입니다.";
		sMailDetailSubject = "<strong>"+ pMap.get("monWeek")+" 집중감지 대상 감지 현황입니다. ("+pMap.get("searchTerm")+")</strong><br>";
		
		sMailDetailSubject += "※ 상세보기를 클릭하면 이상징후 집중감지 화면으로 이동합니다.";
		sContent = sContent.replace("${emailDtlTitle}", sMailDetailSubject);

		StringBuffer sbDetailList = selectAllTargetMailing(pMap);
		sContent = sContent.replace("${emailDtlDesc}", sbDetailList.toString());
		
		//메일발송
		try {
			String sResult = this.sendEmailList(sSubject,sContent,null);
			if("SS".equals(sResult)){
				nMailCnt++;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error(e.toString());
		}

		sbLog.append("\n------------------------------------------------");
		logger.debug("\n"+ sbLog.toString());
    	sbLog.setLength(0);
    	
    	//logger.info("\nAbnormal Target Mail Execute Time : "+Float.toString((float)(System.currentTimeMillis() - startTime)/1000) +" sec"
		//		   +"\nAbnormal Target Mail (Send / Total) : ( "+ nMailCnt +" / "+ nTotalCnt +" ) ");
    	
    	//logger.info("===== Abnormal Target Mail End =====");
    	
    }
    
    //집중감지 대상자 전체 조회
    @SuppressWarnings("unchecked")
	private StringBuffer selectAllTargetMailing(Map<String, Object> pMap) {

        //기간별 집중감지 대상자 목록 검색
    	List<Object> targetList = monitorService.selectTargetMailing(pMap);

        StringBuffer sbHtml = new StringBuffer();

        String fontFamily = "\"Malgun Gothic\"";

        sbHtml.append("<table style='width:100%; margin:0; padding:0; border-collapse:collapse; border-spacing:0; border-cellpadding:0; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; vertical-align:top; text-align:center;'>");
        sbHtml.append("   <thead>");
        sbHtml.append("       <tr>");
        sbHtml.append("           <th width=\"10%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-left:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>No</th>");
        sbHtml.append("           <th width=\"15%\" style='border:1px solid #C3C3C3; background:#D3DDE2; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>구분</th>");
        sbHtml.append("           <th               style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>사용자</th>");
        sbHtml.append("           <th width=\"12%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>접속건수</th>");
        sbHtml.append("           <th width=\"12%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>전송건수</th>");
        sbHtml.append("           <th width=\"12%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>다운로드</th>");
        sbHtml.append("           <th width=\"20%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>최종 감지 일시</th>");
        sbHtml.append("       </tr>");
        sbHtml.append("   </thead>");
        sbHtml.append("    <tbody>");
        
        for (int i = 0; i < targetList.size(); i++) {

            Map<String, Object> kMap = (Map<String, Object>) targetList.get(i);

            sbHtml.append("       <tr>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; border-left:0; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; color:#333;'>"
	                    + kMap.get("RNUM")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; color:#333;'>"
	                    + kMap.get("TARGET_NM")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + kMap.get("EMP_NM")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + PortalUtil.NVL(kMap.get("CONNECT_CNT"), "0")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + PortalUtil.NVL(kMap.get("TRANS_CNT"), "0")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + PortalUtil.NVL(kMap.get("DOWN_CNT"), "0")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:10px; border-right:0; color:#333;'>"
	                    + kMap.get("INS_DTTM")
	                    + "</td>");
            sbHtml.append("       </tr>");
        }

        if(targetList.size() == 0) {

        	sbHtml.append("       <tr>");
        	sbHtml.append("           <td colspan='7' style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + "No Results were found."
	                    + "</td>");
        	sbHtml.append("       </tr>");
        }
        
        sbHtml.append("   </tbody>");
        sbHtml.append("</table>");
        
        return sbHtml;
    }
    
    //부서별 집중감지 대상자 조회
    @SuppressWarnings("unchecked")
	private StringBuffer selectTargetDetailList(Map<String, Object> pMap) {

        //부서별 집중감지 대상자 조회
    	List<Object> targetList = monitorService.selectTargetList(pMap);

        StringBuffer sbHtml = new StringBuffer();

        String fontFamily = "\"Malgun Gothic\"";

        sbHtml.append("<table style='width:100%; margin:0; padding:0; border-collapse:collapse; border-spacing:0; border-cellpadding:0; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; vertical-align:top; text-align:center;'>");
        sbHtml.append("   <thead>");
        sbHtml.append("       <tr>");
        sbHtml.append("           <th width=\"10%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-left:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>No</th>");
        sbHtml.append("           <th width=\"15%\" style='border:1px solid #C3C3C3; background:#D3DDE2; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>구분</th>");
        sbHtml.append("           <th               style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>사용자</th>");
        sbHtml.append("           <th width=\"12%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>접속건수</th>");
        sbHtml.append("           <th width=\"12%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>전송건수</th>");
        sbHtml.append("           <th width=\"12%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>다운로드</th>");
        sbHtml.append("           <th width=\"20%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>최종 감지 일시</th>");
        sbHtml.append("       </tr>");
        sbHtml.append("   </thead>");
        sbHtml.append("    <tbody>");
        
        for (int i = 0; i < targetList.size(); i++) {

            Map<String, Object> kMap = (Map<String, Object>) targetList.get(i);

            sbHtml.append("       <tr>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; border-left:0; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; color:#333;'>"
	                    + kMap.get("RNUM")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; color:#333;'>"
	                    + kMap.get("TARGET_NM")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + kMap.get("EMP_NM")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + PortalUtil.NVL(kMap.get("CONNECT_CNT"), "0")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + PortalUtil.NVL(kMap.get("TRANS_CNT"), "0")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + PortalUtil.NVL(kMap.get("DOWN_CNT"), "0")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:10px; border-right:0; color:#333;'>"
	                    + kMap.get("INS_DTTM")
	                    + "</td>");
            sbHtml.append("       </tr>");
        }

        if(targetList.size() == 0) {

        	sbHtml.append("       <tr>");
        	sbHtml.append("           <td colspan='7' style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + "No Results were found."
	                    + "</td>");
        	sbHtml.append("       </tr>");
        }
        
        sbHtml.append("   </tbody>");
        sbHtml.append("</table>");
        
        return sbHtml;
    }
    
    /**
     * 이상징후 권한변경 메일 발송 실행
     *
     * @return
     */
    @SuppressWarnings("unchecked")
	public void startAbnormalTargetChangeMail(Map<String, Object> pMap) {
    	//메일 발송시 이용할 서버주소
    	String sServerUrl = configuration.getString("portal.abnormal.mail.send.server");
    	
    	//운영모드여부
    	boolean bRealServer = false;
    	if(sServerUrl.indexOf("design.")>0){
    		bRealServer = true;
    	}
    	
    	StringBuffer emailContents = new StringBuffer();
        BufferedReader br = null;

        try {
			ClassPathResource emailTemplate = new ClassPathResource("/email/vwsmail/mail_abnormal_target.html");
		
			br = new BufferedReader(new InputStreamReader(emailTemplate.getInputStream(), "UTF-8"));
	        String line;
	        while ((line = br.readLine()) != null) {
	            emailContents.append(line);
	        }
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
        
        String sSubject = "";
		String sContent = emailContents.toString();
		
		if(!bRealServer){
			sSubject += "[테스트] ";
		}
        
		sSubject += "권한 변경 알림 메일입니다.";
		
    	//이상징후 권한변경 메일 발송
    	//inform 등록
    	if(pMap.containsKey("memberIdList") && !(checkEmpty(pMap.get("memberIdList"))).isEmpty()) {
			ArrayList<HashMap<String, Object>> memberIdList = (ArrayList<HashMap<String, Object>>) convtJsonToList(checkEmpty(pMap.get("memberIdList")));
			if (memberIdList != null) {
				for (HashMap<String, Object> fMap : memberIdList) {
					Map<String, Object> tmpMap = monitorService.selectTargetMailInfo(fMap);
					
					if(tmpMap != null && !tmpMap.get("EMAIL_ADDR").toString().equals("")){
						fMap.put("sndEmpNo", pMap.get("sessEmpNo"));
						fMap.put("rcvEmpNo", (String)tmpMap.get("EMP_NO"));
						String sToMail = (String)tmpMap.get("EMAIL_ADDR");

						sContent = sContent.replace("${mainUrl}", sServerUrl);
						sContent = sContent.replace("${emailDtlTitle}", "<strong>권한 변경 알림</strong>");

						//집중감지 대상자 권한 조회
				    	List<Object> targetList = monitorService.selectTargetAuthInfo(fMap);
				    	
						StringBuffer sbDetailList = selectTargetAuthList(targetList);
						
						sContent = sContent.replace("${emailDtlDesc}", sbDetailList.toString());
						
						try {
							this.sendEmail(null,sToMail,sSubject,sContent,null);
						} catch (UnsupportedEncodingException e) {
							logger.error(e.toString());
						} catch (IOException e) {
							logger.error(e.toString());
						}
						
						String sInformTitle = "[권한변경]"+(String)tmpMap.get("EMP_NM") +"님의 ";
						
						if(targetList.size()==0){
							sInformTitle += "차단권한이 해제되었습니다.";
						}else{
							for (int i = 0; i < targetList.size(); i++) {
								Map<String, Object> kMap = (Map<String, Object>) targetList.get(i);
								
								if(i > 0){
									sInformTitle += "/";
								}
								sInformTitle += kMap.get("BLOCK_TYPE_NM");
							}
							sInformTitle += " 권한이 회수되었습니다.";
						}

						fMap.put("informTitie", sInformTitle);
						int informSq = mailTaskDao.selectWvsInformSq();
						
						fMap.put("informSq", informSq);
						fMap.put("templateSq", "16");
						fMap.put("LINK_URL", "#");
						
			            mailTaskDao.insertVwsInformMsg(fMap);

					}
				}
			}
		}
    }
    
    //집중감지 대상자 권한 조회
    @SuppressWarnings("unchecked")
    private StringBuffer selectTargetAuthList(List<Object> targetList) {

        StringBuffer sbHtml = new StringBuffer();

        String fontFamily = "\"Malgun Gothic\"";

        sbHtml.append("<table style='width:100%; margin:0; padding:0; border-collapse:collapse; border-spacing:0; border-cellpadding:0; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; vertical-align:top; text-align:center;'>");
        sbHtml.append("   <thead>");
        sbHtml.append("       <tr>");
        sbHtml.append("           <th width=\"10%\" style='border:1px solid #C3C3C3; background:#D3DDE2; border-left:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>No</th>");
        sbHtml.append("           <th               style='border:1px solid #C3C3C3; background:#D3DDE2; border-right:0; margin:0; padding:3px 4px; font-family:" + fontFamily + ", arial, dotum, gulim; font-size:12px; line-height:normal;'>차단권한</th>");
        sbHtml.append("       </tr>");
        sbHtml.append("   </thead>");
        sbHtml.append("    <tbody>");
        
        for (int i = 0; i < targetList.size(); i++) {

            Map<String, Object> kMap = (Map<String, Object>) targetList.get(i);

            sbHtml.append("       <tr>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; border-left:0; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; color:#333;'>"
	                    + kMap.get("RNUM")
	                    + "</td>");
            sbHtml.append("           <td style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
                    + ", arial, dotum, gulim; font-size:12px; color:#333;'>"
                    + kMap.get("BLOCK_TYPE_NM")
                    + "</td>");
            sbHtml.append("       </tr>");
        }

        if(targetList.size() == 0) {

        	sbHtml.append("       <tr>");
        	sbHtml.append("           <td colspan='2' style='margin:0; padding:3px 4px; border:1px solid #C3C3C3; font-family:" + fontFamily
	                    + ", arial, dotum, gulim; font-size:12px; border-right:0; color:#333;'>"
	                    + "차단된 권한이 없습니다."
	                    + "</td>");
        	sbHtml.append("       </tr>");
        }
        
        sbHtml.append("   </tbody>");
        sbHtml.append("</table>");
        
        return sbHtml;
    }
    
    /**
     * 
     * 관리자 메일링 그룹 메일발송
     * 
     * @param subject
     * @param contents
     * @param attachFile
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public String sendEmailList(String subject
    		               , String contents
    		               , String attachFile) throws UnsupportedEncodingException, IOException { 
    	
    	List<Object> mailList = monitorService.selectMailingGroup(new HashMap<String, Object>());
    	for(int i=0;i<mailList.size();i++){
    		Map<String, Object> pMap = (Map<String, Object>) mailList.get(i);
    		String sToMail = (String)pMap.get("EMAIL_ADDR");
    		String sResult = this.sendEmail(null,sToMail,subject,contents,attachFile);
    		if(!"SS".equals(sResult)){
    			try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					continue;
				}
			}
    	}
    	return PortalConstants.Response.SUCCESS;
    }
    /**
     * 
     * 메일발송
     * 
     * @param toAddrStr
     * @param subject
     * @param contents
     * @param attachFile
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public String sendEmail( String fromAddrStr
    		               , String toAddrStr
    		               , String subject
    		               , String contents
    		               , String attachFile) throws UnsupportedEncodingException, IOException {    	
		
    	//메일 발송시 이용할 서버주소
    	String sServerUrl = configuration.getString("portal.abnormal.mail.send.server");
    	
    	//운영모드여부
    	boolean bRealServer = false;
    	if(sServerUrl.indexOf("design.")>0){
    		bRealServer = true;
    	}
    	
    	logger.debug("sendEmail [toAddrStr] : "+toAddrStr);
		
    	try {
	        // Transport
	        Properties properties = new Properties();
	        properties.put("mail.transport.protocol", configuration.getString("portal.email.protocol"));
	        properties.put("mail.smtp.host", configuration.getString("portal.email.host"));
	        properties.put("mail.smtp.port", configuration.getString("portal.email.port"));
	        
	        Session mailSession = Session.getDefaultInstance(properties, null);
	        MimeMessage mimeMessage = new MimeMessage(mailSession);
	        
	        //발신인 설정
	        InternetAddress fromAddr;
	        if(fromAddrStr == null){
		        fromAddr = new InternetAddress(configuration.getString("portal.email.designportal.username"));
	        }else{
	        	fromAddr = new InternetAddress(fromAddrStr);
	        }
	        mimeMessage.setFrom(fromAddr);
	        
	        if(toAddrStr == null) return PortalConstants.Response.NOT_EXIST;

	        if(!bRealServer){
		    	//Test용 
		    	toAddrStr = "skhy.x0104436@partner.sk.com";
		    	InternetAddress[] ccAddrArr = new InternetAddress[2];
		    	ccAddrArr[0] = new InternetAddress("skhy.x0006252@partner.sk.com");
		    	ccAddrArr[1] = new InternetAddress("skhy.x0100803@partner.sk.com");
	        }
	        
	        //수신인 설정
	    	mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddrStr));
	    	
	    	//참조인 설정
	    	//mimeMessage.setRecipients(Message.RecipientType.CC, ccAddrArr);
	    	//mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress("skhy.x0100803@partner.sk.com"));
	    	
	    	//숨은참조 설정
	    	//mimeMessage.setRecipients(Message.RecipientType.BCC, ccAddrArr);
	    	//mimeMessage.setRecipient(Message.RecipientType.BCC, new InternetAddress("skhy.x0100803@partner.sk.com"));
	    	
	    	//제목
	    	mimeMessage.setSubject(subject);
	    	
	    	//발송일 설정
	    	mimeMessage.setSentDate(new Date());
	    	
	    	//첨부메일일 경우
	    	if(attachFile!=null){
		        Multipart messageMultiPart = new MimeMultipart();    
		        MimeBodyPart messageBodyPart = new MimeBodyPart();

		    	DataSource fds = null;
	
	        	messageBodyPart.setContent(contents, "text/html; charset=utf-8");
		        messageMultiPart.addBodyPart(messageBodyPart);
	
		        //파일첨부
		        File file = null;
	        	BodyPart attachBodyPart = null;
	        	attachBodyPart = new MimeBodyPart();
	        	
	        	String fileNm = "";
	        	
	    		fileNm = attachFile.substring(attachFile.lastIndexOf("/")+1);
	
	    		file = new File(attachFile);
	            fds = new FileDataSource(file);
	            attachBodyPart.setDataHandler(new DataHandler(fds));
	            attachBodyPart.setFileName(fileNm);
	            
	            attachBodyPart.setHeader("Content-ID","<image>");
	        	
	        	messageMultiPart.addBodyPart(attachBodyPart);
		        
		        mimeMessage.setContent(messageMultiPart);
		        
		        if(!bRealServer)
		        logger.debug("sendEmail Reverse [toAddrStr] : "+toAddrStr);
		        logger.debug("sendEmail [fromAddr] : "+fromAddr);
		        logger.debug("sendEmail [subject] : "+subject);
		        logger.debug("sendEmail [contents] : "+contents);
	        	logger.debug("sendEmail [attachFile] : "+attachFile);
	        	logger.debug("sendEmail [fileNm] : "+fileNm);
	    	}
	    	//첨부메일이 아닐 경우
	    	else{
	    		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	    		message.setText(contents, true);
	    		//메일 이미지 설정
	    		Resource imgTopHeader = new ClassPathResource("/email/vwsmail/images/img_top_header.png");
	            message.addInline("img_top_header", imgTopHeader);
	            Resource ptnContent = new ClassPathResource("/email/vwsmail/images/ptn_content.png");
	            message.addInline("ptn_content", ptnContent);
	            Resource imgTopLogo = new ClassPathResource("/email/vwsmail/images/img_logo.png");
	            message.addInline("img_logo", imgTopLogo);
	            Resource icoContents = new ClassPathResource("/email/vwsmail/images/ico_view_alarm.png");
                message.addInline("ico_view_alarm", icoContents);
                
                if(contents.indexOf("btn_detail")>0){
	                Resource btndetail = new ClassPathResource("/email/vwsmail/images/btn_detail.png");
	                message.addInline("btn_detail", btndetail);
                }
                
                if(!bRealServer)
		        logger.debug("sendEmail Reverse [toAddrStr] : "+toAddrStr);
		        logger.debug("sendEmail [fromAddr] : "+fromAddr);
		        logger.debug("sendEmail [subject] : "+subject);
		        logger.debug("sendEmail [contents] : "+contents);
	    	}
        	
	    	Transport.send(mimeMessage);
	        return PortalConstants.Response.SUCCESS;
	    } catch (MessagingException e) {
	    	logger.error("MessagingException"+e.toString());
	    	return PortalConstants.Response.FAIL;
	    } finally {
	    	
	    }
    }
    
    
    public String checkEmpty(Object str) {
    	return checkEmpty(str, "");
    }
    
    public String checkEmpty(Object str, String defaultStr) {

    	return str == null || "".equals(str) ? defaultStr : (String) str;
    } 
    
    @SuppressWarnings("rawtypes")
	public List convtJsonToList(String jsonStr) {
        List retList = null;
        ObjectMapper oMapper = new ObjectMapper();
        
        try {
            retList = oMapper.readValue(jsonStr, ArrayList.class);
        } catch (JsonMappingException jme) {
            //log.error(jme);
        } catch (JsonParseException jpe) {
            //log.error(jpe);
        } catch (IOException ie) {
            //log.error(ie);
        } 
        
        return retList;
    }
}
