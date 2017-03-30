package resources.com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import resources.com.service.ReadProperties;

public class FileDownLoad extends AbstractView {
	
	public String DefaultFileUpLoadPath 	= ReadProperties.getProperty("FileUpLoad.default.path");
	
	public FileDownLoad() {  
		setContentType("application/octet_stream; charset=UTF-8"); 
	}

	/**
	 * 모델에 파일정보를 리턴하는 메소드
	 * 
	 */
	@Override
	public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		// 업로드경로
		String path = (String) model.get("getFileUploadDrive");
		
		// 실제 업로드경로 
		String serverPath = (String) model.get("downLoadFilePath");
		
		// 다운로드 받을 파일
		String downloadFileName = (String) model.get("downLoadFileName");

		// 파일 찾기 
		File inModelFile = new File( serverPath, downloadFileName);

		// 파일 가져오기
		File file = inModelFile.getAbsoluteFile();
		
		response.setContentType(getContentType());
		response.setContentLength((int) file.length());
		
		String userAgent = request.getHeader("User-Agent");
		
		boolean ie = userAgent.indexOf("MSIE") > -1;

		String fileName = null;
		
		//업로드 된 원본 파일명으로 다운로드 메소드추가
		if("".equals(StringUtils.getNull(model.get("originFileName")))){
			if (ie)   fileName = URLEncoder.encode(file.getName(), "UTF-8");  
			else      fileName = new String(file.getName().getBytes("UTF-8"),  "ISO-8859-1");
		}else{
			if (ie)   fileName = URLEncoder.encode((String) model.get("originFileName"), "UTF-8");  
			else      fileName = new String(((String) model.get("originFileName")).getBytes("UTF-8"),  "ISO-8859-1");
		}

		response.setHeader("Content-Disposition", "attachment; filename=\""	+ fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		OutputStream out = response.getOutputStream();
		
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(file);
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

	/** 원본 파일명 세팅 메소드추가 **/
	public void setModelUsedByDownLoad(ModelMap model,String filePath,String fileName,String originFileName ){
		
		model.addAttribute("getFileUploadDrive" , DefaultFileUpLoadPath);
		model.addAttribute("downLoadFilePath", filePath);
		model.addAttribute("downLoadFileName", fileName);
		model.addAttribute("originFileName", originFileName);
	}
	
	/**
	 * 서버에 WebContent 아래에 저장된 물리적 파일을 다운로드한다.
	 * */
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
}