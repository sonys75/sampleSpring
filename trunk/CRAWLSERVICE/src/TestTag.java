import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

public class TestTag {
	public static void main(String[] args) {
/*
		System.out.println(splitSeq(1));
		System.out.println(splitSeq(10));
		System.out.println(splitSeq(11));
		System.out.println(splitSeq(100));
		System.out.println(splitSeq(111));
		System.out.println(splitSeq(1000));
		System.out.println(splitSeq(10000));
		System.out.println(splitSeq(100000));
		System.out.println(splitSeq(1000000));
		*/
		
		for(int i=1;i<100000;i++){
			System.out.println(splitSeqDir(i));
			i=i+4;
		}
		
		
		HttpRequest request = new HttpRequest() {
			
			@Override
			public HttpHeaders getHeaders() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public URI getURI() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public HttpMethod getMethod() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		/*
	    try {
	    //https://s22.postimg.org/t4ikwoqox/Honeycam_2016_11_13_18_47_17.gif
		    URL url = new URL("http://www.ssulwar.com/dev/files/attach/images/110/337/618/5d98e3850eee7844bcda9f5f91ea61f4.JPG");
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

		    FileOutputStream fos = new FileOutputStream("C:/data/google_favicon_128.jpg");
		    fos.write(response);
		    fos.close();		

	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

		List<String> list = null;
		String str = "<img src=\"http://www.ssulwar.com/dev/files/attach/images/110/337/618/5d98e3850eee7844bcda9f5f91ea61f4.JPG\" alt=\"2.JPG\" width=\"850\" height=\"660\" style=\"\" />";

		System.out.println(getImgReplace(str));

		list = getImgSrc(str);

		for (String imgUrl : list) {
			System.out.println(imgUrl);
		}
		
		System.out.println("========================================================");
		String imgtag = null;
		Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
		Pattern pattern_h = Pattern.compile("height=('|\")?([0-9]{1,5})('|\")?");
		Pattern pattern_w = Pattern.compile("width=('|\")?([0-9]{1,5})('|\")?");
		*//*************************************
		 * 정규표현식으로 <img 태그 뽑기
		 *****************************************//*
		Matcher match = pattern.matcher(str);

		if (match.find()) {
			imgtag = match.group(0);
			str = imgtag.replaceAll(imgtag, imgtag + "test");
		}
		System.out.println(str);
		System.out.println(imgtag);

		Matcher m = pattern_h.matcher(imgtag);

		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "height='100'");
		}
		m.appendTail(sb);
		imgtag = sb.toString();
		System.out.println(imgtag);

		m = pattern_w.matcher(imgtag);
		sb.setLength(0);
		while (m.find()) {
			m.appendReplacement(sb, "width='100'");
		}
		m.appendTail(sb);
		imgtag = sb.toString();
		System.out.println(imgtag);
		*//***********************************************************************************************************//*
		*/
	}
	
	
    /**
     * 시퀀스가 넘어오면 500단위로 잘라 디렉토리 구분을 해준다.
     * 
     * @param seq
     * @return
     */
    public static String splitSeqDir(int seq) {
        //System.out.println("== "+Math.round((seq / 1000)));
        
        //String priceStr = String.valueOf(Math.round((seq / 500)))+"/"+String.valueOf(Math.round(seq % 500 )) +"/"+ seq;
    	String priceStr = "";
    	
    	long lTmpSeq = Math.round((seq / 500));
    	long lTmpSub = 0;
    	String sRndSeq = "";
    	
    	if(lTmpSeq>999){
    		while(lTmpSeq>999){
    			lTmpSub = Math.round(lTmpSeq % 500 );
    			lTmpSeq = Math.round((lTmpSeq / 500));
    			if(lTmpSeq<1000){
    				sRndSeq += String.valueOf(lTmpSeq)+"/"+String.valueOf(lTmpSub);
    			}
    		}
    	}
    	
    	if(!"".equals(sRndSeq)){
    		//System.out.println("== "+sRndSeq);
    		priceStr = sRndSeq+"/"+String.valueOf(Math.round(seq % 500 ));
    	}else{
    		priceStr = String.valueOf(Math.round((seq / 500)))+"/"+String.valueOf(Math.round(seq % 500 ));
    	}

        return priceStr;
    }
	
	/**
	 * 이미지 사이즈 변환 함수
	 * 
	 * 1. img tag의 src 부분만 추출
	 * 2. 추출된 src를 img tag 로 다시 변환후 리턴
	 * 
	 * @param contentHtml
	 * @return
	 */
	public static String getImgReplace(String contentHtml) {
		Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

		List<String> result = new ArrayList<String>();
		Matcher matcher = nonValidPattern.matcher(contentHtml);

		while (matcher.find()) {
			result.add(matcher.group(1));
			contentHtml = contentHtml.replaceAll(matcher.group(0), "<img src=\""+matcher.group(1).trim()+"\" style=\"width:100%\"/>");
		}

		return contentHtml;
	}

	public static List<String> getImgSrc(String str) {
		Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

		List<String> result = new ArrayList<String>();
		Matcher matcher = nonValidPattern.matcher(str);

		while (matcher.find()) {
			result.add(matcher.group(1));
			System.out.println(matcher.group(1).trim());
			//reStr = m.replaceAll("<img $1 src="/ekp/upload/attach/mig/' + stripExt + '_$2.gif"')
		}

		return result;
	}
}