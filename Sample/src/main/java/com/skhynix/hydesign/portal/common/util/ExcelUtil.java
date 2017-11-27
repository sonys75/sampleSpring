package com.skhynix.hydesign.portal.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class ExcelUtil {

	public Workbook createExcel(String templateFileName, List<?> list) throws InvalidFormatException{
		XLSTransformer transformer = new XLSTransformer();
		URL url = this.getClass().getClassLoader().getResource("");
		String srcFilePath = url.getPath() + templateFileName;
		Map<String,Object> beanParams = new HashMap<String,Object>();
		beanParams.put("list", list);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(srcFilePath);
			return transformer.transformXLS(fis, beanParams);
			//transformer.transformXLS(srcFilePath, beanParams, destFilePath);
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis != null) fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return null;
	}

}
