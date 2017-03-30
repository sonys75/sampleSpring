package crawl.crn.nws.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import crawl.mng.cde.service.CodeManageService;
import crawl.vo.VoCodeInfo;
import resources.com.service.MessageAccessor;
import resources.com.util.StringUtils;

@Service("rssNewsCollecting")
public class NewsScheduling {

	@Autowired
	CodeManageService codeService;
	
	@Autowired
	NewsService newsService;

	public Logger logger;
	public StringBuffer sbDebugLog	=	new StringBuffer();
	
	public void collectNews() throws Exception {
		logger = Logger.getLogger(this.getClass());
		sbDebugLog.setLength(0);
		
		try{
			String sSeverName = MessageAccessor.getMessage("ServerName");

			//서버이름이 있을경우에만 db에 인서트 한다.
			//if(sSeverName.indexOf("localhost") < 0){
				HashMap<String, String> hMap = new HashMap<String,String>();
				hMap.put("codePart", "RSS");
				
				List<VoCodeInfo> codeList = (List<VoCodeInfo>) this.codeService.selectListCodeInfoByCodePart(hMap);
				
				for(int i = 0; i<codeList.size(); i++){
					
					String rssNews = StringUtils.getNull(codeList.get(i).getCode());
					String rssUrl = StringUtils.getNull(codeList.get(i).getCode_desc());
					if(!"".equals(rssUrl)){
						this.getNews(rssNews, rssUrl);
					}
				}
			//}
		}catch(Exception e){
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
   			logger.error("EXCEPTION 위치 : kr.co.evansoft.cmm.rss.service.collectNews");
   			logger.error("EXCEPTION 내용 : \n" + e.toString());
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
   		}finally{
   			logger.debug("=========================== DEBUG START ============================");
   			logger.debug("DEBUG 위치 : kr.co.evansoft.cmm.rss.service.collectNews" + sbDebugLog.toString());
   			logger.debug("=========================== DEBUG END    ===========================");
   			sbDebugLog.setLength(0);
   		}	
	}
	
	/**
	 * 뉴스제공사와 뉴스RSS 정보를 가지고 뉴스를 수집한다.
	 * 
	 * @param rssNews
	 * @param rssUrl
	 * @throws Exception
	 */
	public void getNews(String rssNews, String rssUrl) throws Exception {
		logger = Logger.getLogger(this.getClass());
		sbDebugLog.setLength(0);

		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
			String toDays = formatter.format(new Date());

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(rssUrl);
	
			doc.getDocumentElement().normalize();
	
			NodeList itemNodeList = doc.getElementsByTagName("item");
	
			//System.out.println("RSS count :"  + itemNodeList.getLength());
			HashMap<String, String> hMap = new HashMap<String,String>();

			for(int i =0; i < itemNodeList.getLength(); i++){
				hMap = new HashMap<String,String>();
				hMap.put("provideCd", rssNews);
				hMap.put("newsTime", toDays);
				hMap.put("seq", Integer.toString(i+1));

				Node itemNode = itemNodeList.item(i);
	
				if(itemNode.getNodeType() == Node.ELEMENT_NODE){
					Element itemElement = (Element)itemNode;
					/*
					NodeList guidNodeList = itemElement.getElementsByTagName("guid"); 
					Element guidElement = (Element)guidNodeList.item(0); 
					NodeList childGuidNodeList = guidElement.getChildNodes(); 
					//System.out.printf("[guid : %s]\n", ((Node)childGuidNodeList.item(0)).getNodeValue()); 
					*/
					NodeList titleNodeList = itemElement.getElementsByTagName("title"); 
					Element titleElement = (Element)titleNodeList.item(0); 
					NodeList childTitleNodeList = titleElement.getChildNodes(); 
					//voNews.setSubject(((Node)childTitleNodeList.item(0)).getNodeValue());
					//System.out.printf("[title : %s]\n", ((Node)childTitleNodeList.item(0)).getNodeValue()); 
					hMap.put("subject", ((Node)childTitleNodeList.item(0)).getNodeValue() );
					
					NodeList linkNodeList = itemElement.getElementsByTagName("link"); 
					Element linkElement = (Element) linkNodeList.item(0);
					NodeList childLinkNodeList = linkElement.getChildNodes(); 
					//voNews.setLink(((Node)childLinkNodeList.item(0)).getNodeValue());
					//System.out.printf("[link : %s]\n",((Node)childLinkNodeList.item(0)).getNodeValue());
					hMap.put("link", ((Node)childLinkNodeList.item(0)).getNodeValue() );
					
					/*
					NodeList descriptionNodeList = itemElement.getElementsByTagName("description"); 
					Element descriptionElement = (Element) descriptionNodeList.item(0);
					NodeList childDescriptionNodeList = descriptionElement.getChildNodes(); 
					System.out.printf("[description : %s]\n", ((Node)childDescriptionNodeList.item(0)).getNodeValue());
					*/
					NodeList pubDate = itemElement.getElementsByTagName("pubDate");
					Element pubDateElement = (Element)pubDate.item(0);
					NodeList childPubDateNodeList = pubDateElement.getChildNodes(); 
					//voNews.setPubdate(((Node)childPubDateNodeList.item(0)).getNodeValue());
					//System.out.printf("[pub-date : %s]\n", ((Node)childPubDateNodeList.item(0)).getNodeValue());
					hMap.put("pubDate", ((Node)childPubDateNodeList.item(0)).getNodeValue() );
	
					NodeList createDate = itemElement.getElementsByTagName("category");
					Element createElement = (Element)createDate.item(0);
					NodeList childCreateNodeList = createElement.getChildNodes(); 
					//voNews.setCategory(((Node)childCreateNodeList.item(0)).getNodeValue());
					//System.out.printf("[category : %s]\n", ((Node)childCreateNodeList.item(0)).getNodeValue());
					hMap.put("category", ((Node)childCreateNodeList.item(0)).getNodeValue() );
				}
				
				this.newsService.insertNewsRss(hMap);
			}
			
			//3일전 뉴스 수집정보를 삭제한다.
			this.newsService.deleteNewsRss();
		}catch(Exception e){
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
   			logger.error("EXCEPTION 위치 : kr.co.evansoft.cmm.rss.service.getNews");
   			logger.error("EXCEPTION 내용 : \n" + e.toString());
   			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
   		}finally{
   			logger.debug("=========================== DEBUG START ============================");
   			logger.debug("DEBUG 위치 : kr.co.evansoft.cmm.rss.service.getNews" + sbDebugLog.toString());
   			logger.debug("=========================== DEBUG END    ===========================");
   			sbDebugLog.setLength(0);
   		}	
	}
}
