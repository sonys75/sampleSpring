/**
 * 
 * 파 일 명 : com.skhynix.farms.common.util.ExcelCellRef.java<BR>
 * 파일설명 : Cell의 이름과 값을 가져오는 java 파일<BR>
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


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;
 
public class ExcelCellRef {
    /**
     * Cell에 해당하는 Column Name을 가젼온다(A,B,C..)
     * 만약 Cell이 Null이라면 int cellIndex의 값으로
     * Column Name을 가져온다.
     * @param cell
     * @param cellIndex
     * @return
     */
    public static String getName(Cell cell, int cellIndex) {
        int cellNum = 0;
        if(cell != null) {
            cellNum = cell.getColumnIndex();
        }
        else {
            cellNum = cellIndex;
        }
        
        return CellReference.convertNumToColString(cellNum);
    }
    
    public static String getValue(Cell cell) {
        String value = "";
        
        if(cell == null) {
            value = "";
        }
        else {
        	switch(cell.getCellType()) {
	            case Cell.CELL_TYPE_FORMULA :
	                value = cell.getCellFormula();
	                break;
	            
	            case Cell.CELL_TYPE_NUMERIC :
	                value = (int)cell.getNumericCellValue() + "";
	                
	                if( DateUtil.isCellDateFormatted(cell)) {
	    				Date date = (Date) cell.getDateCellValue();
	    				value = new SimpleDateFormat("yyyy.MM.dd").format(date);
	    			}
	    			else{	    				
	                	value = (int)cell.getNumericCellValue() + "";
	    			}
	                
	                break;
	                
	            case Cell.CELL_TYPE_STRING :
	                value = cell.getStringCellValue();
	                break;
	            
	            case Cell.CELL_TYPE_BOOLEAN :
	                value = cell.getBooleanCellValue() + "";
	                break;
	           
	            case Cell.CELL_TYPE_BLANK :
	                value = "";
	                break;
	            
	            case Cell.CELL_TYPE_ERROR :
	                value = cell.getErrorCellValue() + "";
	                break;
	            default:
	                value = cell.getStringCellValue();
        	}
        }
        
        return value;
    }
 
}
