import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import resources.com.util.CrawlUtil;

public class JavaWebCrawler {

	public static String getCurrentData(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        return sdf.format(new Date());
	}
	
	
	public static String fncReplaceSulWar(String line){

		boolean bExistImg = false;
		boolean bExistIframe = false;
		
		line = line.replaceAll("[$]", "\\\\\\$"); 
		line = line.replaceAll("<br />", "\n");
		line = line.replaceAll("</div>", "\n");
		line = line.replaceAll("&nbsp;", " ");
		line = line.replaceAll("\\[email&#", "");
		line = line.replaceAll("160;protected\\]", "");

		
		//line = line.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		String tmpSLine = "";
		String tmpELine = "";
		if(line.indexOf("<script")>=0){
			tmpSLine = line.substring(0,line.indexOf("<script"));
			tmpELine = line.substring(line.lastIndexOf("</script>")+9);
			line = tmpSLine + tmpELine;
		}
		
		if(line.indexOf("<img")>=0){
			bExistImg = true;
			line = line.replaceAll("<img", "&lt;img");
			line = line.replaceAll("/>", "/&gt;");
		}
		
		int nStart = 0;
		int nEnd1 = 0;
		int nEnd2 = 0;
		String tempBeforeLine = "";
		String tempAfterLine = "";
		
		if(line.indexOf("<iframe")>=0){
			
			bExistIframe = true;
			
			nStart = line.indexOf("<iframe");
			
			String sTemp = line.substring(nStart);
			
			nEnd1 = sTemp.indexOf("</iframe>");
			nEnd2 = sTemp.indexOf("/>");
			
			if(sTemp.substring(1).indexOf("<iframe")-1>nEnd1 || sTemp.substring(1).indexOf("<iframe")-1>nEnd2){
				bExistIframe = false;
			}
			
			if(bExistIframe){
				if(nEnd1>nEnd2){
					sTemp = sTemp.substring(0, sTemp.indexOf("</iframe>")+"</iframe>".length());
				}else{
					sTemp = sTemp.substring(0, sTemp.indexOf("/>")+"/>".length());
				}
				tempBeforeLine = sTemp;
				
				sTemp = sTemp.replaceAll("<", "&lt;");
				sTemp = sTemp.replaceAll(">", "&gt;");
				tempAfterLine = sTemp;
				
				line = line.replaceAll(tempBeforeLine, tempAfterLine);
			}
		}
		
		line = line.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		
		
		if(bExistIframe){
			line = line.replaceAll(tempAfterLine, tempBeforeLine);
		}
		
		if(bExistImg){
			line = line.replaceAll("&lt;img", "<img");
			line = line.replaceAll("/&gt;", "/>");
		}
		
		
        return line;
	}

    /**
     * @param args
     */
	/*
	//문화일보 소설 내용 수집
    public static void main(String[] args) {

		System.out.println(" Start Date : " + getCurrentData());
		
	    String result = "";
	    
	    StringBuffer sb = new StringBuffer();
	    
	    try {
	        URL url = new URL("http://www.munhwa.com/news/view.html?no=2002041601011702226002");

	        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "EUC-KR"));
	        boolean bStart = false;
		    String line = "";
		    while((line=br.readLine()) != null){
		    	if(line.indexOf("<div id=\"NewsAdContent\">")>0){
		    		bStart = true;
		    	}else{
		    		if(bStart){
			    		if(line.indexOf("<script")>=0){
			    			bStart = false;
			    		}
			    		
			    		if(bStart){
			    			line = line.trim().replaceAll("<br />", "");
			    			sb.append(line.trim() +"\n");
			    		}
			    	}
		    	}

		    }

	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    System.out.println("==================================================");
	    System.out.println(sb.toString());
	    System.out.println("==================================================");
	    
        System.out.println(" End Date : " + getCurrentData());
    }	
    */
	
	/*
	//문화일보 소설 목록 수집
    public static void main(String[] args) {

		System.out.println(" Start Date : " + getCurrentData());
		
	    String result = "";
	    
	    StringBuffer sb = new StringBuffer();
	    
	    try {
	        URL url = new URL("http://www.munhwa.com/news/series.html?secode=677&page=119");
	 
	        System.out.println("url=[" + url + "]");
	        System.out.println("protocol=[" + url.getProtocol() + "]");
	        System.out.println("host=[" + url.getHost() + "]");
	        System.out.println("content=[" + url.getContent() + "]");
	 
	        
	        InputStreamReader is = new InputStreamReader(url.openStream(), "EUC-KR");
	        int ch;
	        while ((ch = is.read()) != -1) {
	            //System.out.print((char) ch);
	            result += (char) ch;
	        }
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "EUC-KR"));

		    String line = "";
		    while((line=br.readLine()) != null){
		    	if(line.indexOf("class=\"d14b_333\"")>0){
		    		line = line.trim().replaceAll("<td style=\"padding:4 0 0 3\"><a href=\"", "");
		    		line = line.trim().replaceAll("</a> <font color=\"#999999\">\\[", "^");
		    		line = line.trim().replaceAll("]</font></td>", "");
		    		line = line.trim().replaceAll("\" class=\"d14b_333\">", "^");
		    		String[] aParam = line.split("\\^");
		    		
		    		sb.append(aParam[0] +" - "+ aParam[1] +" - "+ aParam[2] +"\n");
		    		
		    		HashMap<String, String> hMap = new HashMap<String,String>();
					hMap.put("link", aParam[0]);
					hMap.put("subject", aParam[1]);
					hMap.put("pubDate", aParam[2]);
					
					//this.insertNovel(hMap);
		    	}
		    		
		    }

	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    System.out.println("==================================================");
	    System.out.println(sb.toString());
	    System.out.println("==================================================");
	    
        System.out.println(" End Date : " + getCurrentData());
    }
	*/
	
	/**
	 * html 의 <!-- --> 주석을 제거한다.
	 * 
	 * @param contentHtml
	 * @param bLineRemove	줄바꿈 기호 삭제여부
	 * @return
	 */
	public static String getRemoveHtmlBlock(String contentHtml, boolean bLineRemove) {
		//String eqtnFtn = "/*-- 주석1 --*/ /**-- 주석2 --**/  주석아님1 /** 주석3 **/  /* \n 주석 \n 주석 */  주석아님2";
		 //eqtnFtn = eq﻿tnFtn.replaceAll("/\\*(?:.|[\\n\\r])*?\\*/", "");
		 //eqtnFtn = eqtnFtn.replaceAll("/\\*.*\\*/", ""); //엔터를 찾지못해서 문제
		 //eqtnFtn = eqtnFtn.replaceAll("/\\*(.|[\r\n])*\\*/", ""); // 주석과 주석 사이에 있는 문자열까지 찾아서 문제
		 //eqtnFtn = eqtnFtn.replaceAll("/\\*([^*]|[\r\n])*\\*/", ""); // 하나의 주석에 다수의 '*'가 있는경우를 인지못해서 문제
		 //eqtnFtn = eqtnFtn.replaceAll("/\\*([^*]|[\r\n]|(\\*([^/]|[\r\n])))*\\*/", ""); // '주석아님1'을 찾지 못해서 문제
		 //eqtnFtn = eqtnFtn.replaceAll("/\\*([^*]|[\r\n]|(\\*+([^*/]|[\r\n])))*\\*/", ""); // 주석들이 다시 튀어나와서 문제
		 //드뎌해결 1
		 //eqtnFtn = eqtnFtn.replaceAll("/\\*([^*]|[\r\n]|(\\*+([^*/]|[\r\n])))*\\*+/", ""); // 마지막에 '+' 넣어서 해결
		 //또다른해결 2(Non-greedy Matching)
		
		//contentHtml = contentHtml.replaceAll("<!---->", "");
		//contentHtml = contentHtml.replaceAll("<!--\\*(?:.|[\\n\\r])*?\\*-->", "");
		//contentHtml = contentHtml.replaceAll("/\\*(?:.|[\\n\\r])*?\\*/", "");// *. --> *?
		//contentHtml = contentHtml.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

		contentHtml = contentHtml.replaceAll("<!--.*-->", "");
		contentHtml = contentHtml.trim();
		
		if(bLineRemove)
			contentHtml = contentHtml.replaceAll("\n", "");
		
		return contentHtml;
	}
	
    /**
     * a tag 로 이루어진 태그만 추출한다. 
     * 
     * @param contentHtml
     * @return
     */
	public static List<String> getATagLink(String contentHtml) {
		Pattern nonValidPattern = Pattern.compile("<a [^>]*>.*</a>");
		
		List<String> result = new ArrayList<String>();

		Matcher matcher = nonValidPattern.matcher(contentHtml);

		while (matcher.find()) {
			System.out.println(matcher.group());
			result.add(matcher.group());
		}

		return result;
	}
	
	/**
     * a href의 값만 추출한다. 
     * 
     * @param contentHtml
     * @return
     */
	public static String getATagHref(String contentHtml) {
		Pattern pattern = Pattern.compile("<a[^>]*href=[\"']?([^>\"']+)[\"']?[^>]*>");
		String retrunStr = "";
		Matcher matcher = pattern.matcher(contentHtml);

		while (matcher.find()) {
			retrunStr = matcher.group(1);
		}

		return retrunStr;
	}
	/*
	// 썰워스트 게시판 목록 수집
    public static void main(String[] args) {

		System.out.println(" Start Date : " + getCurrentData());

	    StringBuffer sb = new StringBuffer();
	    
	    try {
	        URL url = new URL("http://www.ssulwar.com/index.php?mid=talk&page=1");
	 
	        System.out.println("url=[" + url + "]");
	        System.out.println("protocol=[" + url.getProtocol() + "]");
	        System.out.println("host=[" + url.getHost() + "]");
	        System.out.println("content=[" + url.getContent() + "]");

	        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

	        boolean bStart = false;
		    String line = "";
		    
		    while((line=br.readLine()) != null){
		    	
		    	line = getRemoveHtmlBlock(line.trim(), true);
		    	
		    	if(line.length()>0)
		    		System.out.println(line);
		    	
		    	//실제 테이블 시작
		    	if(line.indexOf("<table class=\"bd_lst bd_tb_lst bd_tb\">")>=0){
		    		bStart = true;
		    		//sb.append(line +"\n");
		    	}
		    	
		    	if(bStart){
		    		//System.out.println(line.length());
		    		if(line.length()>0)
		    			sb.append(line +"\n");
		    		
		    		if(line.indexOf("</table>")>=0){
		    			bStart = false;
		    			break;
		    		}
		    		
		    	}
		    }

	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    System.out.println("==================================================");
	    System.out.println(sb.toString());
	    System.out.println("==================================================");
	    //System.out.println(getATagLink(sb.toString())             );
	    //System.out.println("==================================================");
	    List<String> getLink = getATagLink(sb.toString());

	    boolean bInsert = false;
	    
	    if(getLink.size()>0){
	    	bInsert = getSsulWarLink(getLink);
	    }
	    
	    System.out.println("==================================================");
	    System.out.println("bInsert : "+bInsert);
	    System.out.println("==================================================");
	    
        System.out.println(" End Date : " + getCurrentData());
    }
   */

	// 썰워스트 이미지 게시판 목록 수집
    public static void main(String[] args) {

		System.out.println(" Start Date : " + getCurrentData());

	    StringBuffer sb = new StringBuffer();
	    
	    try {
	        URL url = new URL("http://www.ssulwar.com/index.php?mid=girlemt&page=1");
	 
	        System.out.println("url=[" + url + "]");
	        System.out.println("protocol=[" + url.getProtocol() + "]");
	        System.out.println("host=[" + url.getHost() + "]");
	        System.out.println("content=[" + url.getContent() + "]");

	        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

	        boolean bStart = false;
		    String line = "";
		    
		    while((line=br.readLine()) != null){
		    	
		    	line = getRemoveHtmlBlock(line.trim(), true);
		    	
		    	//if(line.length()>0)
		    	//	System.out.println(line);
		    	
		    	//실제 테이블 시작
		    	if(line.indexOf("<div id=\"nd2\" class=\"tabs_content\">")>=0){
		    		bStart = true;
		    		//sb.append(line +"\n");
		    	}
		    	
		    	if(bStart){
		    		//System.out.println(line.length());
		    		if(line.length()>0)
		    			sb.append(line +"\n");
		    		
		    		if(line.indexOf("</div>")>=0){
		    			bStart = false;
		    			break;
		    		}
		    		
		    	}
		    }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    String sFullStr = sb.toString();
	    sFullStr = sFullStr.replaceAll("\n", "");
	    sFullStr = sFullStr.replaceAll("</a>", "</a>\n");
	    System.out.println("==================================================");
	    System.out.println(sFullStr);
	    System.out.println("==================================================");
	    //System.out.println(getATagLink(sb.toString())             );
	    //System.out.println("==================================================");
	    List<String> getLink = getATagLink(sFullStr);

	    boolean bInsert = false;
	    
	    if(getLink.size()>0){
	    	bInsert = getSsulWarLink(getLink);
	    }
	    
	    System.out.println("==================================================");
	    System.out.println("bInsert : "+bInsert);
	    System.out.println("==================================================");
	    
        System.out.println(" End Date : " + getCurrentData());
    }

    /**
     * 썰워 목록 전용 함수
     * a href 로 이루어진 태그만 추출한다. 
     * 
     * @param aLink
     * @return
     */
	public static boolean getSsulWarLink(List<String> aLink) {
		boolean bInsert = false;
		
		for(int i=0;i<aLink.size();i++){
			String sLinkTag = aLink.get(i);
			String sLink = "";
			String sSubject = "";
			
			if(sLinkTag.indexOf("href=\"#")>=0){
				//통과
			}else if(sLinkTag.indexOf("index.php")>=0){
				//통과
			}else{
				sLink = getATagHref(sLinkTag);
				sSubject = sLinkTag.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				sSubject = sSubject.replaceAll("&nbsp;", "");
				bInsert = true;
				
				System.out.println("sLink : "+sLink);
				System.out.println("sSubject : "+sSubject);
			}
		}

		return bInsert;
	}
    
	/*
	// 썰워스트 게시판 내용 수집
    public static void main(String[] args) {

		System.out.println(" Start Date : " + getCurrentData());
	    
	    StringBuffer sb = new StringBuffer();
	    
	    try {
	    	//http://www.ssulwar.com/624144
	    	//http://www.ssulwar.com/623528
	    	//http://www.ssulwar.com/56471
	    	//http://www.ssulwar.com/623669
	    	//http://www.ssulwar.com/428644
	        URL url = new URL("http://www.ssulwar.com/428644");

	        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

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
		    String sGetDateBlock = sFullStr.substring(sFullStr.indexOf("<div id=\"vmin\" class=\"cate fr\">"));
		    sGetDateBlock = sGetDateBlock.substring(0, sGetDateBlock.indexOf("</div>")+"</div>".length());
		    sGetDateBlock = sGetDateBlock.replaceAll("\n", "").trim();
		    sGetDateBlock = sGetDateBlock.replaceAll("<!--", "").trim();
		    sGetDateBlock = sGetDateBlock.replaceAll("-->", "").trim();
		    sGetDateBlock = sGetDateBlock.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		    sGetDateBlock = sGetDateBlock.replaceAll("&nbsp;", "").trim();
		    if(sGetDateBlock.indexOf("명 읽음")>=0){
		    	sRead = sGetDateBlock.substring(0,sGetDateBlock.indexOf("명 읽음"));
		    	sGetDateBlock = sGetDateBlock.substring(sGetDateBlock.indexOf("명 읽음")+"명 읽음".length()).trim();
		    }
		    sDate = sGetDateBlock.substring(0,sGetDateBlock.indexOf(" "));
		    
		    //sFullStr = sb.toString();
		    String sContentBlock = sFullStr.substring(sFullStr.indexOf("<article>"));//</article>
		    sContentBlock = sContentBlock.substring(0, sContentBlock.indexOf("</article>")+"</article>".length());
		    sContentBlock = sContentBlock.substring(sContentBlock.indexOf("<div class=\"document_"), sContentBlock.indexOf("<!--AfterDocument"));
		    sContent = CrawlUtil.convertImgTagSize(fncReplaceSulWar(sContentBlock),"width:100%");
		    sContent = CrawlUtil.convertIFrameTagSize(fncReplaceSulWar(sContent),"width:100%");
		    
		    
		    System.out.println("==================================================");
		    System.out.println(sRead);
		    System.out.println(sDate);
		    //System.out.println(sContentBlock);
		    System.out.println(sContent);
		    System.out.println("==================================================");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    //System.out.println("==================================================");
	    //System.out.println(StringUtil.getImgReplace(sb.toString()));
	    //System.out.println(getRemoveHtmlBlock(sb.toString(), true));
	    //System.out.println(sb.toString());
	    //System.out.println("==================================================");
	    
        System.out.println(" End Date : " + getCurrentData());
    }
    */
    
    
}
