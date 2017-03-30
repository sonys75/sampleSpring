package crawl.cmm.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.AbstractView;

import resources.com.service.ReadProperties;
import resources.com.service.SessionMessage;
import resources.com.util.FileUtils;
import resources.com.util.ReqUtils;
import resources.com.util.StringUtils;

@Controller
public class FileDownloadController extends AbstractView {
 
	public Logger logger; //2. main class 안에 선언한다.
	public StringBuffer sbDebugLog	=	new StringBuffer();

	//파일 업로드 디렉토리
	public String DefaultFileUpLoadPath 	= ReadProperties.getProperty("FileUpLoad.default.path");
		
    /**
     * 브라우저 구분 얻기.
     * 
     * @param request
     * @return
     */
    private String getBrowser(HttpServletRequest request) {
    	if(request==null) return "Firefox";
    	
        String header = request.getHeader("User-Agent");

        if (header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if (header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            return "Opera";
        } else if (header.indexOf("Trident") > -1) { //MS IE 11
        	return "IE11";
        }
        return "Firefox";
    }
    
    /**
     * Disposition 지정하기.
     * 
     * @param filename
     * @param request
     * @param response
     * @throws Exception
     */
    private void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String browser = getBrowser(request);

    	String dispositionPrefix = "attachment; filename=";
    	String encodedFilename = null;

    	if (browser.equals("MSIE") || browser.equals("IE11")) {
    		encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "\\ ");//"%20"
    	} else if (browser.equals("Firefox")) {
    		encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
    	} else if (browser.equals("Opera")) {
    		encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
    	} else if (browser.equals("Chrome")) {
    		StringBuffer sb = new StringBuffer();
    		for (int i = 0; i < filename.length(); i++) {
    			char c = filename.charAt(i);
    			if (c > '~') {
    				sb.append(URLEncoder.encode("" + c, "UTF-8"));
    			} else {
    				sb.append(c);
    			}
    		}
    		encodedFilename = sb.toString();
    	} else {
    		//throw new RuntimeException("Not supported browser");
    		throw new IOException("Not supported browser");
    	}

    	response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

    	if ("Opera".equals(browser)){
    		response.setContentType("application/octet-stream;charset=UTF-8");
    	}
    }

    
    /**
	 * 서버에 WebContent 아래에 저장된 물리적 파일을 다운로드한다.
	 * */
    /*
	public void directDownLoad(String FilePath, String FileName, String OriginalFileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		// 서버상 경로 
		String sServerFilePath = request.getSession().getServletContext().getRealPath(FilePath);
		
		// 물리적 저장된 파일 이름
		String sFileName = FileName;

		// 서버에 저장된 파일 찾기 
		File serverFile = new File(sServerFilePath, sFileName);

		// 로컬에 저장될 파일이름 가져오기
		File saveFile = serverFile.getAbsoluteFile();
		
		response.setContentType(getContentType());
		response.setContentLength((int) saveFile.length());

		String userAgent = request.getHeader("User-Agent");
		
		boolean ie = userAgent.indexOf("MSIE") > -1;
		
		String localSaveFileName = null;
		if(OriginalFileName==null || "".equals(OriginalFileName)){
			if (ie)   localSaveFileName = URLEncoder.encode(saveFile.getName(), "UTF-8");  
			else      localSaveFileName = new String(saveFile.getName().getBytes("UTF-8"),  "ISO-8859-1");
		}else{
			if (ie)   localSaveFileName = URLEncoder.encode(OriginalFileName, "UTF-8");  
			else      localSaveFileName = new String(OriginalFileName.getBytes("UTF-8"),  "ISO-8859-1");
		}

		response.setHeader("Content-Disposition", "attachment; filename=\""	+ localSaveFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		OutputStream out = response.getOutputStream();
		
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(serverFile);
			FileCopyUtils.copy(fis, out);
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (fis != null)
			try { fis.close(); } 
			catch (IOException ex) { }
		}
		out.flush();
	}
	*/
	/**
	 * 모델에 파일정보를 리턴하는 메소드
	 * 
	 */
	@Override
	public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 실제 업로드경로 
		String serverPath = (String) model.get("downLoadFilePath");
		
		// 다운로드 받을 파일
		String downloadFileName = (String) model.get("downLoadFileName");

		// 파일 찾기 
		File inModelFile = new File( serverPath, downloadFileName);

		// 파일 가져오기
		File file = inModelFile.getAbsoluteFile();

		response.setContentType(getContentType());
		
		boolean bFileChk = file.isFile();
		
		if(bFileChk){
			response.setContentLength((int) file.length());
	 
			setDisposition(StringUtils.getNull(model.get("originFileName")), request,  response);
			response.setHeader("Content-Transfer-Encoding", "binary");

		}else{
			BufferedWriter out = new BufferedWriter(new FileWriter(serverPath+"/"+downloadFileName+".txt"));

			out.newLine();
			out.write("요청하신 " + StringUtils.getNull(model.get("originFileName")));
			out.newLine();
			out.write("파일이 존재하지 않습니다.");
			out.newLine();
			out.newLine();
			out.write("관리자에게 문의하여 주시기 바랍니다.");
			out.flush();
			out.close();
			
			// 파일 찾기 
			inModelFile = new File( serverPath, downloadFileName+".txt");

			// 파일 가져오기
			file = inModelFile.getAbsoluteFile();
			
			response.setContentLength((int) file.length());
			setDisposition("파일을 찾을 수 없습니다.txt", request,  response);
			response.setHeader("Content-Transfer-Encoding", "binary");
		}

		FileInputStream fis = null;
		
		OutputStream outStream = response.getOutputStream();

		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, outStream);
		} catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.getMessage());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
		} finally {
			if (fis != null)
			try { fis.close(); } 
			catch (IOException ex) { }
		}
		outStream.flush();
		outStream.close();
		
		if(!bFileChk){
			file.delete();
		}
	}

	/** 원본 파일명 세팅 메소드추가 **/
	public void setModelUsedByDownLoad(ModelMap model,String filePath,String fileName,String originFileName ){
		model.addAttribute("downLoadFilePath", filePath);
		model.addAttribute("downLoadFileName", fileName);
		model.addAttribute("originFileName", originFileName);
	}

   /**
     * FileName과 게시판 파트번호를 던저 이미지를 다운로드 하게 한다.
     * 
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping({"/nvl/imgView.do"})
    public String commonImgView(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	logger = Logger.getLogger(this.getClass());
		sbDebugLog.setLength(0);

		//파라미터 설정
		ReqUtils req = new ReqUtils(request);
		sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");

		//에러시 리턴할 URL
		String retURL = "";
				
		String sFilePath = "";
		String sOrgFileNm = "";
		String sSaveFileNm = "";

	    try{		
	    	//검색 정보 세팅
			HashMap<String, String> hMap = new HashMap<String,String>();
			
			
			hMap.put("part", StringUtils.getNull(req.getString("part")));
			hMap.put("seq", StringUtils.getNull(req.getString("seq")));
			hMap.put("fileName", StringUtils.getNull(req.getString("fileName")));
 
			sFilePath = DefaultFileUpLoadPath+"/"+hMap.get("part")+"/"+FileUtils.splitSeqDir(Integer.parseInt(hMap.get("seq")));
			sOrgFileNm = hMap.get("fileName");
			sSaveFileNm = hMap.get("fileName");

			sbDebugLog.append("\n DEBUG sFilePath : " + sFilePath );
			sbDebugLog.append("\n DEBUG sOrgFileNm : " + sOrgFileNm );
			sbDebugLog.append("\n DEBUG sSaveFileNm : " + sSaveFileNm );
			setModelUsedByDownLoad(model, sFilePath, sSaveFileNm, sOrgFileNm);
			renderMergedOutputModel(model, request, response);
 
			return "blank";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception",retURL);
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
		
    }
    
}
