package resources.com.util;

import java.io.File;

import resources.com.service.ReadProperties;

/**
 * @author 
 */
public class FileUtils {

    // 버퍼 사이즈를 정한다.
    private static final byte[] buf = new byte[1024];
    
    //파일 업로드 디렉토리
  	public static String FileUpLoadPath 	= ReadProperties.getProperty("FileUpLoad.default.path");

  	
  	/**
     * 업로드 Path를 체크하여 없으면 생성한다.
     *
     * @param filePath 생성할 디렉토리명
     * @throws Exception
     */
  	/*
    public static String createSavePath(String filePath) throws Exception {
    	//System.out.println("filePath:"+filePath);
    	
    	String sRootPath = filePath.substring(0,filePath.indexOf(FileUpLoadPath.toString().replaceAll("/", "\\\\")));
    	String sCheckFilePath = filePath.substring(filePath.indexOf(FileUpLoadPath.toString().replaceAll("/", "\\\\")));
    	
    	//System.out.println("sRootPath:"+sRootPath);
    	//System.out.println("sCheckFilePath:"+sCheckFilePath);
    	
    	String[] aCheckFilePath = sCheckFilePath.split("\\\\");
    	//System.out.println("aCheckFilePath.length:"+aCheckFilePath.length);
    	
    	String sCheckPath = sRootPath;
    	for(int i=1;i<aCheckFilePath.length;i++){
    		sCheckPath += "\\"+aCheckFilePath[i];
    		//System.out.println("sCheckPath:"+sCheckPath);
        	File cFilePath = new File(sCheckPath);

    		if (!cFilePath.isDirectory())
    			cFilePath.mkdir();    		
    	}

		return filePath;
    }
    */
  	/**
     * 업로드 Path를 체크하여 없으면 생성한다.
     *
     * @param filePath 생성할 디렉토리명
     * @throws Exception
     */
    public static String createSavePath(String filePath) throws Exception {
    	//System.out.println("filePath:"+filePath);

    	String[] aCheckFilePath = filePath.split("/");
    	//System.out.println("aCheckFilePath.length:"+aCheckFilePath.length);
    	
    	String sCheckPath = "";
    	
    	for(int i=1;i<aCheckFilePath.length;i++){
    		sCheckPath += "\\"+aCheckFilePath[i];
    		//System.out.println("sCheckPath:"+sCheckPath);
        	File cFilePath = new File(sCheckPath);

    		if (!cFilePath.isDirectory())
    			cFilePath.mkdir();    		
    	}

		return filePath;
    }
    
    /**
     * 업로드 Path를 체크하여 없으면 생성한다.
     *
     * @param filePath 생성할 디렉토리명
     * @throws Exception
     */
    public static String createPath(String filePath) throws Exception {
    	File cFilePath = new File(filePath);

		if (!cFilePath.isDirectory())
			cFilePath.mkdirs();
		
		return filePath;
    }
    
    public static String getFileExt(String fileName) throws Exception {
    	String str = fileName.toLowerCase();
    	int chk = str.lastIndexOf(".");
    	if(chk<0){
    		return "etc";
    	}
    	String ext = str.substring(chk+1);
    	return ext;
    }
    
    /**
     * 업로드 될 파일이 있는지 체크한 후 파일이 존재할 경우 rename 하여 파일명을 리턴한다.
     * 
     * @param filePath
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String checkFileRename(String filePath, String fileName) throws Exception {
    	File checkFile  	= new File(filePath, fileName);
    	File checkRenameFile  = null;
    	boolean bCheck	= false;
        if(checkFile.exists()){
        	System.err.println("+++++++++++++ 중복파일 정보 시작 +++++++++");
        	while(!bCheck){
        		System.err.println("filePath : "+filePath+", fileName : "+fileName);
        		String sRandomNum = Integer.toString((int)(Math.random()*(100000*10)));
        		fileName= CryptUtils.encryptMD5(sRandomNum,fileName);
        		System.err.println("filePath : "+filePath+", refileName : "+fileName);
        		checkRenameFile  	= new File(filePath, fileName);
        		if(!checkRenameFile.exists()){
        			System.err.println("checkRenameFile.exists() : "+checkRenameFile.exists());
        			bCheck = true;
        		}
        		//System.err.println("checkFile : "+checkFile);
        	}
        	System.err.println("+++++++++++++ 중복파일 정보 끝  +++++++++");
        }
    	return fileName;
    }
    
    /**
     * 
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void fileDelete(String filePath, String fileName) throws Exception {
    	File checkFile  	= new File(filePath, fileName);
    	if(checkFile.exists()){
    		checkFile.delete();
    	}
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
}