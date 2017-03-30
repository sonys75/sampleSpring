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

@Service("crawlList")
public class CrawlListScheduling {

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
					
					hMap.put("crawlingYn", "N");
					
					this.crawlerService.updateCrawlStatus(hMap);
				}else{
					System.out.println("이미 실행중...");
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
			String sCrawlUrl = StringUtils.getNull(hMap.get("crawlUrl"));

			int nPage = 1;

			String tmpUrl = sCrawlUrl;
 
			boolean bLoop = true;
			while(bLoop){
				tmpUrl = sCrawlUrl + nPage;

				//sbDebugLog.append("\n tmpUrl : "+ tmpUrl);
				System.out.println(" tmpUrl : "+ tmpUrl);
				
				if("SSULWAR".equals(hMap.get("crawlPart"))){
					bLoop = crawlerListSsulWar(tmpUrl);
					//bLoop=false;
					if(!bLoop) break;
				}else{
					bLoop=false;
					if(!bLoop) break;
				}
 
				nPage++;
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

	
	
	public boolean crawlerListSsulWar(final String crawlUrl) throws Exception {
		Logger logger = Logger.getLogger(this.getClass());
		StringBuffer sbDebugLog	=	new StringBuffer();
		sbDebugLog.setLength(0);
		
		boolean bLoop = true;
		
		StringBuffer sb = new StringBuffer();
		
	    try {
	        URL targetUrl = new URL(crawlUrl);
 
	        BufferedReader br = new BufferedReader(new InputStreamReader(targetUrl.openStream(), "UTF-8"));
	        
	        boolean bStart = false;
		    String line = "";
		    
		    while((line=br.readLine()) != null){
		    	line = CrawlUtil.getRemoveHtmlBlock(line.trim(), true);
		    	
		    	//실제 테이블 시작 
		    	if(line.indexOf("<table class=\"bd_lst bd_tb_lst bd_tb\">")>=0){
		    		bStart = true;
		    	}
		    	
		    	if(bStart){
		    		if(line.length()>0)
		    			sb.append(line +"\n");
		    		
		    		if(line.indexOf("</table>")>=0){
		    			bStart = false;
		    			break;
		    		}
		    	}
		    }

		    List<String> getLink = CrawlUtil.getATagLink(sb.toString());
		    if(getLink.size()>0){
		    	bLoop = getSsulWarLink(getLink);
		    }
		    
	    } catch (Exception e) {
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
   			logger.error("EXCEPTION 위치 : crawlerListSsulWar");
   			logger.error("EXCEPTION 내용 : \n" + e.toString());
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");	    	
	    	bLoop = false;
	    }finally{
   			logger.debug("=========================== DEBUG START ============================");
   			logger.debug("DEBUG 위치 : crawlerListSsulWar " + sbDebugLog.toString());
   			logger.debug("=========================== DEBUG END    ===========================");
   			sbDebugLog.setLength(0);
   		}	

	    return bLoop;
 	}
	
	/**
     * 썰워 목록 전용 함수
     * a href 로 이루어진 태그만 추출한다. 
     * 
     * @param aLink
     * @return
     */
	public boolean getSsulWarLink(List<String> aLink) {
		boolean bInsert = false;
		try {
			for(int i=0;i<aLink.size();i++){
				String sLinkTag = aLink.get(i);
				String sLink = "";
				String sSubject = "";
				
				if(sLinkTag.indexOf("href=\"#")>=0){
					//통과
				}else if(sLinkTag.indexOf("index.php")>=0){
					//통과
				}else{
					sLink = CrawlUtil.getATagHref(sLinkTag);
					sSubject = sLinkTag.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
					sSubject = sSubject.replaceAll("&nbsp;", "");
					bInsert = true;
					
					HashMap<String, String> hMap = new HashMap<String,String>();
					hMap.put("part", sPart);
					hMap.put("link", sLink);
					hMap.put("subject", sSubject);
					//해당 링크가 존재하는지 검색한다.
					
					int cnt = this.crawlerService.selectCntCrawlTmpByLink(hMap);
					//System.out.println("sLink cnt : "+ cnt);
					if(cnt==0){
						//aListCrawlLink.add(hMap);
						this.crawlerService.insertCrawlTmp(hMap);
						
						if(nDupleCnt >0){
							nDupleCnt = 0;
						}
					}else{
						//게시판 수집을 하다 새로운 글이 생겼을 경우 false가 떨어짐.
						//따라서 3건 정도는 강제적으로 더 체크할 수 있도록 해야 함.
						nDupleCnt++;
						if(nDupleCnt>=3){
							bInsert = false;
							break;
						}
					}
					
					//System.out.println("sLink : "+sLink);
					//System.out.println("sSubject : "+sSubject);
				}
			}
		} catch (Exception e) {

		}
		return bInsert;
	}
}
