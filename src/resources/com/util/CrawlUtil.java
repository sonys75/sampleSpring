package resources.com.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 컨텐츠 수집관련 유틸리티
 */
public class CrawlUtil {
	
	
    /**
     * format에 따른 현재 시각
     * 
     * @param format
     * @return
     */
    public static String getDateByFormat(String format) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    
	/**
	 * 이미지 사이즈 변환 함수
	 * 
	 * 1. img tag의 src 부분만 추출
	 * 2. 추출된 src를 img tag 로 다시 변환후 리턴
	 * 
	 * @param sHtml
	 * @param sStyleSize
	 * @return
	 */
	public static String convertImgTagSize(String sHtml, String sStyleSize) {
		sHtml = sHtml.replaceAll("\\[", "").trim();
		sHtml = sHtml.replaceAll("\\]", "").trim();
		Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

		List<String> result = new ArrayList<String>();
		Matcher matcher = nonValidPattern.matcher(sHtml);

		while (matcher.find()) {
			result.add(matcher.group(1));
			sHtml = sHtml.replaceAll(matcher.group(0), "<p><img src=\""+matcher.group(1).trim()+"\" style=\""+sStyleSize+"\"/></p>");
		}

		return sHtml;
	}
	
	/**
	 * IFRAME 사이즈 변환 함수
	 * 
	 * 1. iframe tag의 src 부분만 추출
	 * 2. 추출된 src를 iframe tag 로 다시 변환후 리턴
	 * 
	 * @param sHtml
	 * @param sStyleSize
	 * @return
	 */
	public static String convertIFrameTagSize(String sHtml, String sStyleSize) {
		Pattern nonValidPattern = Pattern.compile("<iframe[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*></iframe>");

		List<String> result = new ArrayList<String>();
		Matcher matcher = nonValidPattern.matcher(sHtml);

		while (matcher.find()) {
			result.add(matcher.group(1));
			sHtml = sHtml.replaceAll(matcher.group(0), "<p><iframe src=\""+matcher.group(1).trim()+"\" style=\""+sStyleSize+"\" frameborder=\"0\"></iframe></p>");
		}

		return sHtml;
	}
	
	/**
	 * html 의 <!-- --> 주석을 제거한다.
	 * 
	 * @param sHtml
	 * @param bLineRemove	줄바꿈 기호 삭제여부
	 * @return
	 */
	public static String getRemoveHtmlBlock(String sHtml, boolean bLineRemove) {
		sHtml = sHtml.replaceAll("<!--.*-->", "");
		sHtml = sHtml.trim();
		if(bLineRemove)
			sHtml = sHtml.replaceAll("\n", "");
		return sHtml;
	}
	
    /**
     * a tag 로 이루어진 태그만 추출한다. 
     * 
     * @param sHtml
     * @return
     */
	public static List<String> getATagLink(String sHtml) {
		Pattern pattern = Pattern.compile("<a [^>]*>.*</a>");
		
		List<String> result = new ArrayList<String>();

		Matcher matcher = pattern.matcher(sHtml);

		while (matcher.find()) {
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
	
	/*************************************************************************************
	 썰워 목록 전용 함수 시작
	*************************************************************************************/
    /**
     * a href 로 이루어진 태그만 추출한다. 
     * 
     * @param aLink
     * @return
     */
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
	
}
