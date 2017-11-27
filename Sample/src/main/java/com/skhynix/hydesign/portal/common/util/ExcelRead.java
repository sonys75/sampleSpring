/**
 * 
 * 파 일 명 : com.skhynix.farms.common.util.ExcelRead.java<BR>
 * 파일설명 : 엑셀 파일을 읽어오는 java 파일을 만든다. service에서 호출<BR>
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
 
 
 
public class ExcelRead {
	 public static Map<String, Object> read(ExcelReadOption excelReadOption) {
		 Map<String, Object> returnMap = new HashMap<String, Object>();
		 
		// 엑셀 파일 자체
		// 엑셀파일을 읽어 들인다.
		// FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
		Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
		/**
		 * 엑셀 파일에서 첫번째 시트를 가지고 온다.
		 */
		Sheet sheet = wb.getSheetAt(0);

		// System.out.println("Sheet 이름: "+ wb.getSheetName(0));
		// System.out.println("데이터가 있는 Sheet의 수 :" + wb.getNumberOfSheets());
		/**
		 * sheet에서 유효한(데이터가 있는) 행의 개수를 가져온다.
		 */
		int numOfRows = 0;
		int numOfCells = 0;

		// 특정 row 또는 모든 row 읽기 위한 처리
		int lastRow = excelReadOption.getLastRow();
		if (lastRow == 0) {
			numOfRows = sheet.getPhysicalNumberOfRows(); // 마지막 데이터 row까지 읽을 때
		} else {
			numOfRows = lastRow; // 특정 row까지만 읽고 싶을 때
		}

		// System.out.println("sheet에서 유효한(데이터가 있는) 행의 개수 :" + numOfRows);

		Row row = null;
		Cell cell = null;

		String cellName = "";
		/**
		 * 각 row마다의 값을 저장할 맵 객체 저장되는 형식은 다음과 같다. put("A", "이름"); put("B",
		 * "게임명");
		 */
		Map<String, String> map = null;
		/*
		 * 각 Row를 리스트에 담는다. 하나의 Row를 하나의 Map으로 표현되며 List에는 모든 Row가 포함될 것이다.
		 */
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

		row = sheet.getRow(0);
		int cellCount = row.getLastCellNum();

		/**
		 * 각 Row만큼 반복을 한다.
		 */
		for (int rowIndex = excelReadOption.getStartRow() - 1; rowIndex < numOfRows; rowIndex++) {
			/*
			 * 워크북에서 가져온 시트에서 rowIndex에 해당하는 Row를 가져온다. 하나의 Row는 여러개의 Cell을 가진다.
			 */
			row = sheet.getRow(rowIndex);

			if (row != null) {
				/*
				 * 가져온 Row의 Cell의 개수를 구한다.
				 */
				// numOfCells = row.getPhysicalNumberOfCells();
				// numOfCells = row.getLastCellNum();
				numOfCells = excelReadOption.getOutputTitleColumns().size();

				// System.out.println("row index :" + rowIndex);
				// System.out.println("마지막 열 개수 :" + numOfCells);

				/*
				 * 엑셀 시트 컬럼명 및 컬럼 매핑 추출 console 에서 copy해서 ExcelColNames에 넣어주기
				 * RequestService excelUpload 부분에 rma_com으로만 조회하면 컬럼 자동 생성 / /*
				 * //row 추출 ========================================== for(int
				 * cellIndex = 0; cellIndex < row.getPhysicalNumberOfCells();
				 * cellIndex++) {
				 * 
				 * cell = row.getCell(cellIndex); Cell nameCell =
				 * sheet.getRow(0).getCell(cellIndex); //컬렄명있는 cell String
				 * nameVal = ExcelCellRef.getValue(nameCell); int nameIndex =
				 * excelReadOption.getOutputTitleColumns().indexOf(nameVal);
				 * 
				 * if(cellIndex == 0){ //컬럼명이 특이해서 예외처리 cellName =
				 * "serverGubun"; }else if(nameIndex == -1){ //컬럼명이 미리 세팅한 리스트에
				 * 없으면 //cellName = "ERROR_" + Integer.toString(rowIndex) + "_"
				 * + Integer.toString(cellIndex); cellName = "ERROR_" +
				 * Integer.toString(cellIndex); }else{ cellName =
				 * excelReadOption.getOutputColumns().get(nameIndex); }
				 * System.out.println("temp.add(" + cellIndex + ",\"" + cellName
				 * + "\");" ); }
				 * 
				 * System.out.println( "=================================");
				 * //title추출 ========================================== for(int
				 * cellIndex = 0; cellIndex < row.getPhysicalNumberOfCells();
				 * cellIndex++) {
				 * 
				 * cell = row.getCell(cellIndex); Cell nameCell =
				 * sheet.getRow(0).getCell(cellIndex); //컬렄명있는 cell String
				 * nameVal = ExcelCellRef.getValue(nameCell); int nameIndex =
				 * excelReadOption.getOutputTitleColumns().indexOf(nameVal);
				 * 
				 * if(cellIndex == 0){ //컬럼명이 특이해서 예외처리 cellName =
				 * "serverGubun"; }else if(nameIndex == -1){ //컬럼명이 미리 세팅한 리스트에
				 * 없으면 //cellName = "ERROR_" + Integer.toString(rowIndex) + "_"
				 * + Integer.toString(cellIndex); cellName = "ERROR_" +
				 * Integer.toString(cellIndex); }else{ cellName =
				 * excelReadOption.getOutputColumns().get(nameIndex); }
				 * System.out.println("temp.add(" + cellIndex + ",\"" + nameVal
				 * + "\");" ); }
				 */

				/*
				 * 데이터를 담을 맵 객체 초기화
				 */
				map = new LinkedHashMap<String, String>();
				/*
				 * cell의 수 만큼 반복한다.
				 */
				for (int cellIndex = 0; cellIndex < numOfCells; cellIndex++) {
					/*
					 * Row에서 CellIndex에 해당하는 Cell을 가져온다.
					 */
					cell = row.getCell(cellIndex);
					/*
					 * 현재 Cell의 이름 세팅 이름의 예 : seq, reqEmpNo...
					 */
					Cell nameCell = sheet.getRow(0).getCell(cellIndex); // 컬럼명있는
																		// cell
					String nameVal = ExcelCellRef.getValue(nameCell);
					int nameIndex = excelReadOption.getOutputTitleColumns().indexOf(nameVal);

					if (cellIndex == 0) { // 컬럼명이 특이해서 예외처리
						cellName = "serverGubun";
					} else if (nameIndex == -1) { // 컬럼명이 미리 세팅한 리스트에 없으면
						// cellName = "ERROR_" + Integer.toString(rowIndex) +
						// "_" + Integer.toString(cellIndex);
						cellName = "ERROR_" + Integer.toString(cellIndex);
					} else {
						cellName = excelReadOption.getOutputColumns().get(nameIndex);
					}
					// cellName = ExcelCellRef.getName(cell, cellIndex);
					// cellName = Integer.toString(cellIndex); //cell index로 이름

					/*
					 * 추출 대상 컬럼인지 확인한다 추출 대상 컬럼이 아니라면, for로 다시 올라간다
					 */
					// 엑셀에서 제목 추출을 위해 주석처리
					// if( lastRow == 0 &&
					// !excelReadOption.getOutputColumns().contains(cellName) )
					// {
					// continue;
					// }

					/*
					 * map객체의 Cell의 이름을 키(Key)로 데이터를 담는다.
					 */
					String cellVal = ExcelCellRef.getValue(cell);
					// 공백제거
					cellVal = cellVal.trim();

					map.put(cellName, cellVal);
					// System.out.println(cellName + " : " +
					// ExcelCellRef.getValue(cell));
				}
				/*
				 * 만들어진 Map객체를 List로 넣는다.
				 */
				dataList.add(map);
			}

		}
		returnMap.put("dataList", dataList);
		returnMap.put("cellCount", cellCount);
		
		return returnMap;

	}
     
         
        
		/**
		 * 엑셀 rows 읽어오기
		 * @param startRow : 데이터가 시작되는 엑셀 시트의 row 번호
		 * @param lastRow  : 마지막 데이터가 있는 엑셀 시트의 row 번호, 모든 데이터 읽고 싶으면 0 세팅
		 * @return
		 */
		public Map<String, Object> excelReadRows(File destFile, ExcelReadOption excelReadOption, int startRow, int lastRow) throws Exception{
			//엑셀 파일 위치 세팅 
	        excelReadOption.setFilePath(destFile.getAbsolutePath());
	        
	        //rows 읽기
	        excelReadOption.setStartRow(startRow); //데이터가 시작되는 엑셀 시트의 row 번호
	        excelReadOption.setLastRow(lastRow);  //마지막 데이터가 있는 엑셀 시트의 row 번호, 모든 데이터 읽고 싶으면 0 세팅
	        
	        Map<String, Object> returnMap = ExcelRead.read(excelReadOption);
	        
	        return returnMap;
		}
        
      
}
