/**
 * 
 * 파 일 명 : com.skhynix.farms.common.util.ExcelReadOption.java<BR>
 * 파일설명 : Excel 파일을 읽을 때 옵션을 설정하는 java 파일<BR>
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

import java.util.ArrayList;
import java.util.List;
 
public class ExcelReadOption {
    /**
     * 엑셀파일의 경로
     */
    private String filePath;
    private boolean dvtYn;
    private String sheetName;
    
    
    /**
     * 추출할 컬럼 명
     */
    private List<String> outputColumns;
    private List<String> outputTitleColumns;
    
    /**
     * 추출을 시작할 행 번호
     */
    private int startRow;
    private int lastRow;
        
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public List<String> getOutputTitleColumns() {
        
        List<String> temp = new ArrayList<String>();
        temp.addAll(outputTitleColumns);
        
        return temp;
    }
    public void setOutputTitleColumns(List<String> outputTitleColumns) {
        
        List<String> temp = new ArrayList<String>();
        temp.addAll(outputTitleColumns);
        
        this.outputTitleColumns = temp;
    }
    
    public void setOutputTitleColumns(String ... outputTitleColumns) {
        
        this.outputTitleColumns = new ArrayList<String>();        
        
        for(String item : outputTitleColumns) {
            this.outputTitleColumns.add(item);
        }
    }
    
    
    public List<String> getOutputColumns() {
        
        List<String> temp = new ArrayList<String>();
        temp.addAll(outputColumns);
        
        return temp;
    }
    public void setOutputColumns(List<String> outputColumns) {
        
        List<String> temp = new ArrayList<String>();
        temp.addAll(outputColumns);
        
        this.outputColumns = temp;
    }
    
    public void setOutputColumns(String ... outputColumns) {
        
        this.outputColumns = new ArrayList<String>();
        
        for(String ouputColumn : outputColumns) {
            this.outputColumns.add(ouputColumn);
        }
    }
    
    public int getStartRow() {
        return startRow;
    }
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }
    
	public int getLastRow() {
		return lastRow;
	}
	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}
	
	public boolean isDvtYn() {
		return dvtYn;
	}
	public void setDvtYn(boolean dvtYn) {
		this.dvtYn = dvtYn;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
}
