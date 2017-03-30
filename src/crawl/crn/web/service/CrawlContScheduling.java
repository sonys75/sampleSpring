package crawl.crn.web.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crawl.vo.VoCrawler;
import resources.com.util.CrawlUtil;
import resources.com.util.StringUtils;

@Service("crawlCont")
public class CrawlContScheduling {

	@Autowired
	CrawlerService crawlerService;

	public static String sPart = "";
	public static int nDupleCnt = 0;

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
						
						sbDebugLog.append("\n DEBUG PARAM part : "+ hMapParam.get("part"));
						sbDebugLog.append("\n DEBUG PARAM title : "+ hMapParam.get("title"));
						sbDebugLog.append("\n DEBUG PARAM crawlUrl : "+ hMapParam.get("crawlUrl"));
						sbDebugLog.append("\n DEBUG PARAM crawlPart : "+ hMapParam.get("crawlPart"));
						
						sPart = hMapParam.get("part");
						nDupleCnt = 0;
						startCrawler(hMapParam);
					}
					//컨텐츠 수집이 끝난 페이지에 이미지가 있는지 찾는다.
					this.crawlerService.updateCrawlTmpByImgFind(hMap);
					
					//이미지가 없는 파일을 Y로 바꾼다.
					this.crawlerService.updateCrawlTmpByImgNotFind(hMap);
					
					//컨텐츠 수집이 끝난 페이지에 주석을 삭제한다.
					this.crawlerService.updateCrawlTmpByBlockFind(hMap);
					
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
			//part 별로 10건씩 가져와서 수집한다.
			hMap.put("startRow", "1");
			hMap.put("endRow", "100");

			boolean bLoop = true;
			while(bLoop){
				
				List<VoCrawler> crawlList = (List<VoCrawler>) this.crawlerService.selectListCrawl(hMap);
				
				if(crawlList.size()>0){
					for(int i=0;i<crawlList.size();i++){
						hMap.put("seq", crawlList.get(i).getSeq());
						hMap.put("crawlUrl", crawlList.get(i).getLink());
						
						if("SSULWAR".equals(hMap.get("crawlPart"))){
							bLoop = crawlerContentSsulWar(hMap);
						}
						
						if(!bLoop) break;
					}
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

	
	
	public boolean crawlerContentSsulWar(final HashMap<String, String> hMap) throws Exception {
		Logger logger = Logger.getLogger(this.getClass());
		StringBuffer sbDebugLog	=	new StringBuffer();
		sbDebugLog.setLength(0);
		
		boolean bLoop = true;
		
		StringBuffer sb = new StringBuffer();
		
	    try {
	    	//sbDebugLog.append("0");
	    	
	    	URL targetUrl = new URL(hMap.get("crawlUrl"));
	    	 
	        BufferedReader br = new BufferedReader(new InputStreamReader(targetUrl.openStream(), "UTF-8"));
	        
	        String line = "";
	        
		    while((line=br.readLine()) != null){
		    	line = line.trim();
 
		    	if(line.length()>2){
		    		sb.append(line+"\n");
		    	}
		    }

		    //주석을 없앤 html을 가져온다.
		    String sFullStr = sb.toString();
		    String sRead = "0";
		    String sDate = CrawlUtil.getDateByFormat("yyyy-MM-dd");
		    String sContent  = "";

		    if(sFullStr.indexOf("<div id=\"vmin\" class=\"cate fr\">") >=0 && sFullStr.indexOf("<article>")>=0){
			    String sGetDateBlock = sFullStr.substring(sFullStr.indexOf("<div id=\"vmin\" class=\"cate fr\">"));
			    
			    sGetDateBlock = sGetDateBlock.substring(0, sGetDateBlock.indexOf("</div>")+"</div>".length());
			    
			    //sbDebugLog.append("4");
			    
			    sGetDateBlock = sGetDateBlock.replaceAll("\n", "").trim();
			    sGetDateBlock = sGetDateBlock.replaceAll("<!--", "").trim();
			    sGetDateBlock = sGetDateBlock.replaceAll("-->", "").trim();
			    sGetDateBlock = sGetDateBlock.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
			    sGetDateBlock = sGetDateBlock.replaceAll("&nbsp;", "").trim();
			    if(sGetDateBlock.indexOf("명 읽음")>=0){
			    	sRead = sGetDateBlock.substring(0,sGetDateBlock.indexOf("명 읽음"));
			    	sGetDateBlock = sGetDateBlock.substring(sGetDateBlock.indexOf("명 읽음")+"명 읽음".length()).trim();
			    }
			    //sbDebugLog.append("5");
			    sDate = sGetDateBlock.substring(0,sGetDateBlock.indexOf(" "));
		    
		    
			    //sbDebugLog.append("6");
			    String sContentBlock = sFullStr.substring(sFullStr.indexOf("<article>"));//</article>
			    //sbDebugLog.append("6-1");
			    sContentBlock = sContentBlock.substring(0, sContentBlock.indexOf("</article>")+"</article>".length());
			    //sbDebugLog.append("6-2");
			    sContentBlock = sContentBlock.substring(sContentBlock.indexOf("<div class=\"document_"), sContentBlock.indexOf("<!--AfterDocument"));
	
			    //sbDebugLog.append("7");
			    sContent = CrawlUtil.fncReplaceSulWar(sContentBlock);
			    //sbDebugLog.append("8");
			    sContent = CrawlUtil.convertImgTagSize(sContent,"width:100%");
			    //sbDebugLog.append("9");
			    sContent = CrawlUtil.convertIFrameTagSize(sContent,"width:100%");
			    //sbDebugLog.append("10");
			    
			    /*
			    System.out.println("==================================================");
			    System.out.println(sRead);
			    System.out.println(sDate);
			    //System.out.println(sContentBlock);
			    System.out.println(sContent);
			    System.out.println("==================================================");
			    */
			    try{
			    	Integer.parseInt(sRead);
			    }catch(Exception ex){
			    	sRead = "0";
			    }
			    
			    
			    hMap.put("content", sContent);
			    hMap.put("readCnt", sRead);
			    hMap.put("pubDate", sDate);
			    hMap.put("contYn", "Y");
			    this.crawlerService.updateCrawlTmp(hMap);
		    }else{
		    	sbDebugLog.append("targetUrl : "+targetUrl);
		    	System.out.println("==================================================");
			    System.out.println("targetUrl : "+targetUrl);
			    System.out.println("==================================================");
			    hMap.put("content", "");
			    hMap.put("readCnt", "0");
			    hMap.put("pubDate", "");
			    hMap.put("contYn", "E");
			    this.crawlerService.updateCrawlTmp(hMap);
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

}
