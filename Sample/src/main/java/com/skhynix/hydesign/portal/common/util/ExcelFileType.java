/**
 * 
 * 파 일 명 : com.skhynix.farms.common.util.ExcelFileType.java<BR>
 * 파일설명 : ExcilFile을 읽어 확장자를 비교하는 java 파일<BR>
 * <BR>
 * 작 성 자 : <BR>
 * 작 성 일 : 2017. 6. 14<BR>
 * 변경이력	: 날짜 ,수정자 ,수정내용<BR> 
 * 
 ***********************************************<BR>
 * 본 프로그램 소스는 하이닉스의 사전 승인없이 *<BR>
 * 임의로 복제, 복사, 배포할 수 없음.          *<BR>
 ***********************************************<BR>
 *
 */
package com.skhynix.hydesign.portal.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
 
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class ExcelFileType {
    /**
     * 
     * 엑셀파일을 읽어서 Workbook 객체에 리턴한다.
     * XLS와 XLSX 확장자를 비교한다.
     * 
     * @param filePath
     * @return
     * 
     */
    public static Workbook getWorkbook(String filePath) {
        
        /*
         * FileInputStream은 파일의 경로에 있는 파일을
         * 읽어서 Byte로 가져온다.
         * 
         * 파일이 존재하지 않는다면은
         * RuntimeException이 발생된다.
         */
        FileInputStream fis = null;
        try {
        	
            fis = new FileInputStream(filePath);            
            Workbook wb = null;
            
            /*
             * 파일의 확장자를 체크해서 .XLS 라면 HSSFWorkbook에
             * .XLSX라면 XSSFWorkbook에 각각 초기화 한다.
             */
            if(filePath.toUpperCase(Locale.ENGLISH).endsWith(".XLS")) {
                    wb = new HSSFWorkbook(fis);
            }
            else if(filePath.toUpperCase(Locale.ENGLISH).endsWith(".XLSX")) {
                    wb = new XSSFWorkbook(fis);
            }
            
            return wb;
            
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
        	if(fis != null)
        		safeClose(fis);
        }
        
    }
    
    public static void safeClose(FileInputStream fis) {
	  if (fis != null) {
	    try {
	    	fis.close();
	    } catch (IOException e) {
    	    e.printStackTrace();
	    }
	  }
    }
}
