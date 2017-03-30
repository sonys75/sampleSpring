package crawl.crn.web.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crawl.vo.VoCrawler;
import resources.com.service.ReadProperties;
import resources.com.util.FileUtils;
import resources.com.util.StringUtils;

@Service("crawlImg")
public class CrawlImgScheduling {

	@Autowired
	CrawlerService crawlerService;

	public static String sPart = "";
	public static int nDupleCnt = 0;
	public String DefaultFileUpLoadPath 	= ReadProperties.getProperty("FileUpLoad.default.path");
	public String FileUpLoadPath 			= "";

	/**
	 * 웹페이지 스케줄 수집 시작
	 * 
	 * @throws Exception
	 */
	public void crawlingStart() throws Exception {
		Logger logger = Logger.getLogger(this.getClass());
		StringBuffer sbDebugLog	=	new StringBuffer();
		sbDebugLog.setLength(0);
		
		try{
			//String sSeverName = MessageAccessor.getMessage("ServerName");

			//서버이름이 있을경우에만 db에 인서트 한다.
			//if(sSeverName.indexOf("sonys75") > -1){
				HashMap<String, String> hMap = new HashMap<String,String>();
				
				VoCrawler crawlStatus = this.crawlerService.selectCrawlStatus(hMap);
				
				if("N".equals(crawlStatus.getCrawling_yn())){
					hMap.put("crawlYn", "Y");
					hMap.put("crawlingYn", "Y");
					
					this.crawlerService.updateCrawlStatus(hMap);
					
					
					List<VoCrawler> crawlList = (List<VoCrawler>) this.crawlerService.selectListCrawlPartByCrawlYn(hMap);

					for(int i = 0; i<crawlList.size(); i++){
						
						HashMap<String, String> hMapParam = new HashMap<String,String>();
						hMapParam.put("part", StringUtils.getNull(crawlList.get(i).getPart()));
						hMapParam.put("title", StringUtils.getNull(crawlList.get(i).getTitle()));
						hMapParam.put("crawlUrl", StringUtils.getNull(crawlList.get(i).getCrawl_url()));
						hMapParam.put("crawlPart", StringUtils.getNull(crawlList.get(i).getCrawl_part()));
						
						//sbDebugLog.append("\n DEBUG PARAM part : "+ hMapParam.get("part"));
						sbDebugLog.append("\n DEBUG PARAM title : "+ hMapParam.get("title"));
						//sbDebugLog.append("\n DEBUG PARAM crawlUrl : "+ hMapParam.get("crawlUrl"));
						//sbDebugLog.append("\n DEBUG PARAM crawlPart : "+ hMapParam.get("crawlPart"));
						
						sPart = hMapParam.get("part");
						nDupleCnt = 0;
						startCrawler(hMapParam);
						
						//임시 서비스 활성화
						this.crawlerService.insertCrawlCont(hMapParam);
						
						
						//분류별 커밋데이터 업데이트
						this.crawlerService.updateCrawlCont(hMapParam);
					}

					hMap.put("crawlingYn", "N");
					
					this.crawlerService.updateCrawlStatus(hMap);
				}else{
					sbDebugLog.append("\n웹페이지 수집이 이미 실행중입니다.");
				}
			//}
		}catch(Exception e){
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
   			logger.error("EXCEPTION 위치 : crawlingStart");
   			logger.error("EXCEPTION 내용 : \n" + e.toString());
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
   		}finally{
   			logger.debug("=========================== DEBUG START ============================");
   			logger.debug("DEBUG 위치 : crawlingStart" + sbDebugLog.toString());
   			logger.debug("=========================== DEBUG END    ===========================");
   			sbDebugLog.setLength(0);
   		}	
	}
	
	/**
	 * 게시판 정보 수집 시작
	 * 
	 * @param hMap
	 * @throws Exception
	 */
	public void startCrawler(HashMap<String, String> hMap) throws Exception {
		Logger logger = Logger.getLogger(this.getClass());
		StringBuffer sbDebugLog	=	new StringBuffer();
		sbDebugLog.setLength(0);
	
		try{
			//part 별로 100건씩 가져와서 수집한다.
			hMap.put("startRow", "1");
			hMap.put("endRow", "100");

			boolean bLoop = true;
			while(bLoop){
				
				List<VoCrawler> crawlList = (List<VoCrawler>) this.crawlerService.selectListCrawlImg(hMap);
				
				if(crawlList.size()>0){
					for(int i=0;i<crawlList.size();i++){
						hMap.put("part", StringUtils.getNull(crawlList.get(i).getPart()));
						hMap.put("seq", StringUtils.getNull(crawlList.get(i).getSeq()));
						hMap.put("link", StringUtils.getNull(crawlList.get(i).getLink()));
						hMap.put("subject", StringUtils.getNull(crawlList.get(i).getSubject()));
						hMap.put("content", StringUtils.getNull(crawlList.get(i).getContent()));

						//sbDebugLog.append("\n DEBUG PARAM part : "+ hMap.get("part"));
						//sbDebugLog.append("\n DEBUG PARAM seq : "+ hMap.get("seq"));
						//sbDebugLog.append("\n DEBUG PARAM link : "+ hMap.get("link"));
						//sbDebugLog.append("\n DEBUG PARAM subject : "+ hMap.get("subject"));
						//sbDebugLog.append("\n DEBUG PARAM content : "+ hMap.get("content"));

						crawlerContentImage(hMap);
					}
					bLoop = false;
				}else{
					bLoop = false;
				}
			}

		}catch(Exception e){
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
   			logger.error("EXCEPTION 위치 : startCrawler");
   			logger.error("EXCEPTION 내용 : \n" + e.toString());
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
   		}finally{
   			logger.debug("=========================== DEBUG START ============================");
   			logger.debug("DEBUG 위치 : startCrawler" + sbDebugLog.toString());
   			logger.debug("=========================== DEBUG END    ===========================");
   			sbDebugLog.setLength(0);
   		}	
	}

	public boolean crawlerContentImage(final HashMap<String, String> hMap) throws Exception {
		Logger logger = Logger.getLogger(this.getClass());
		StringBuffer sbDebugLog	=	new StringBuffer();
		sbDebugLog.setLength(0);
		
		boolean bLoop = true;
		
		StringBuffer sb = new StringBuffer();
		
	    try {
	    	List<String> getLink = getImgSrc(hMap.get("content"));
	    	if(getLink.size()>0){
	    		//sbDebugLog.append("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
	    		//sbDebugLog.append("\n DEBUG PARAM part : "+ hMap.get("part"));
				//sbDebugLog.append("\n DEBUG PARAM seq : "+ hMap.get("seq"));
				//sbDebugLog.append("\n DEBUG PARAM link : "+ hMap.get("link"));
				//sbDebugLog.append("\n DEBUG PARAM subject : "+ hMap.get("subject"));
				
				for(int i=0;i<getLink.size();i++){

		    		//sbDebugLog.append("\n DEBUG PARAM image : "+ getLink.get(i));
		    		
		    		String sImgUrl = getLink.get(i);
		    		hMap.put("imgUrl", sImgUrl);	
		    		hMap.put("filePath", DefaultFileUpLoadPath);
		    		
		    		if(sImgUrl.indexOf("http")>=0){
		    			String retVal = saveImage(hMap);
		    			//sbDebugLog.append("\n DEBUG PARAM save image : "+ retVal);
		    			
		    			if(!"".equals(retVal)){
		    				retVal="/nvl/imgView.do?part="+hMap.get("part")+"&seq="+hMap.get("seq")+"&fileName="+retVal;
		    				//sbDebugLog.append("\n DEBUG PARAM save image : "+ retVal);
		    				hMap.put("content", hMap.get("content").replaceAll(sImgUrl, retVal));
		    			}
		    		}
		    	}
				
				hMap.put("imgYn", "Y");	
				
				//이미지 정보를 업데이트 한다.
				this.crawlerService.updateCrawlTmpImg(hMap);
				
				//sbDebugLog.append("\n DEBUG PARAM content : "+ hMap.get("content"));
		    	//sbDebugLog.append("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
		    }
	    } catch (Exception e) {
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
   			logger.error("EXCEPTION 위치 : crawlerContentSsulWar");
   			logger.error("EXCEPTION 내용 : \n" + e.toString());
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");	    	
	    	bLoop = false;
	    }finally{
   			logger.debug("=========================== DEBUG START ============================");
   			logger.debug("DEBUG 위치 : crawlerContentSsulWar " + sbDebugLog.toString());
   			logger.debug("=========================== DEBUG END    ===========================");
   			sbDebugLog.setLength(0);
   		}	

	    return bLoop;
 	}
	
	public static String saveImage(HashMap<String, String> hMap ) {
		
		String retFilePath = "";
		String sPart = "";
		String sSeq = "";
		String sFileName = "";
		try {
			
			sPart = hMap.get("part");
			sSeq = hMap.get("seq");
			sFileName = hMap.get("imgUrl").substring(hMap.get("imgUrl").lastIndexOf("/")+1);
			
		    URL url = new URL(hMap.get("imgUrl"));
		    InputStream in = new BufferedInputStream(url.openStream());
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    byte[] buf = new byte[1024];
		    int n = 0;
		    while (-1 != (n = in.read(buf))) {
		        out.write(buf, 0, n);
		    }
		    out.close();
		    in.close();
		    byte[] response = out.toByteArray();


		    sSeq = FileUtils.splitSeqDir(Integer.parseInt(sSeq));
		    
		    retFilePath = hMap.get("filePath") +"/"+ sPart+"/"+ sSeq;
		    
		    //System.out.println("== "+retFilePath);
		    
		    //업로드 디렉토리 생성
			FileUtils.createPath(retFilePath);

			//파일명을 가지고 MD5로 변환하여 저장할 파일명을 가져온다.
		
			//sFileName = FileUtils.checkFileRename(retFilePath,CryptUtils.encryptMD5("", hMap.get("sFileName")));

		    FileOutputStream fos = new FileOutputStream(retFilePath+"/"+sFileName);
		    fos.write(response);
		    fos.close();
		    
		    retFilePath = retFilePath+"/"+sFileName;
	    } catch (Exception e) {
	    	System.out.println("== "+e.toString());
	    	retFilePath = "";
	    	sFileName = "";
	    }

		return sFileName;
	}	
 
	public static List<String> getImgSrc(String str) {
		Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

		List<String> result = new ArrayList<String>();
		Matcher matcher = nonValidPattern.matcher(str);

		while (matcher.find()) {
			result.add(matcher.group(1));
			//System.out.println(matcher.group(1).trim());
			//reStr = m.replaceAll("<img $1 src="/ekp/upload/attach/mig/' + stripExt + '_$2.gif"')
		}

		return result;
	}
}
