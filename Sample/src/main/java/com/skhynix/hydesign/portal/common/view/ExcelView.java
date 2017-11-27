package com.skhynix.hydesign.portal.common.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.InternalResourceView;

import com.skhynix.hydesign.portal.common.util.PortalUtil;

public class ExcelView extends InternalResourceView {

    private final Log log = LogFactory.getLog(this.getClass());

    /** The content type for an Excel response */
    //private static final String CONTENT_TYPE = "application/vnd.ms-excel";
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private String url;

    private boolean existFile = false;

    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    /**
     * Default Constructor. Sets the content type of the view to "application/vnd.ms-excel".
     */
    public ExcelView() {
        setContentType(CONTENT_TYPE);
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    public void setExistFile(boolean existFile) {
        this.existFile = existFile;
    }

    public boolean isExistFile() {
        return existFile;
    }

    @Override
    protected  void
                renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Determine which request handle to expose to the RequestDispatcher.
        HttpServletRequest requestToExpose = getRequestToExpose(request);

        // Expose the model object as request attributes.
        exposeModelAsRequestAttributes(model, requestToExpose);

        Map map = exposeRequestAttributesAsMap(request);
        String contentDisposition = "attachment; filename=";
        String templateName = (String)model.get("tmplt");
        URL excelTemplatePath = this.getClass().getResource("/excel/" + templateName);
        if (StringUtils.isNotEmpty((String)map.get("filename"))) {
            contentDisposition += URLEncoder.encode((String)map.get("filename"), "UTF-8");
        } else {
            contentDisposition += this.getResource().getFilename();
        }

        response.setHeader("Content-Disposition", contentDisposition);

        if (log.isDebugEnabled()) {
            log.debug("URL =" + url);
        }
        Workbook workbook;
        FileInputStream fis = null;
        ServletOutputStream out = null;
        try {
            if (resource != null) {
                fis = new FileInputStream(new File(excelTemplatePath.getPath()));
                workbook = new XLSTransformer().transformXLS(fis, map);
            } else {
                workbook = new HSSFWorkbook();
                logger.debug("Created Excel Workbook from scratch");
            }

            buildExcelDocument(model, workbook, request, response);

            // Set the content type.
            response.setContentType(getContentType());

            // Should we set the content length here?
            // response.setContentLength(workbook.getBytes().length);

            // Flush byte array to servlet output stream.
            out = response.getOutputStream();
            workbook.write(out);

            out.flush();
        } catch (Exception ex) {

        } finally {
            if (out != null) PortalUtil.safeCloseOut(out);
            if (fis != null) PortalUtil.safeCloseIn(fis);
        }
    }

    protected Map exposeRequestAttributesAsMap(HttpServletRequest request) throws Exception {
        Map map = new HashMap();

        for (Enumeration e = request.getAttributeNames(); e.hasMoreElements();) {
            String name = (String)e.nextElement();
            map.put(name, request.getAttribute(name));
        }
        return map;
    }

    /**
     * Subclasses must implement this method to create an Excel HSSFWorkbook document, given the model.
     * 
     * @param model the model Map
     * @param workbook the Excel workbook to complete
     * @param request in case we need locale etc. Shouldn't look at attributes.
     * @param response in case we need to set cookies. Shouldn't write to it.
     */
    protected void buildExcelDocument(Map model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        InputStream in = null;
        OutputStream out = null;
        /* TODO : 여기에 템플릿 명 */
        String templateName = (String)model.get("tmplt");
        String outputFileName = (String)model.get("filename");
        URL excelTemplatePath = this.getClass().getResource("/excel/" + templateName);
        try {
            in = getTemplateSource(excelTemplatePath);
            //response.setHeader("Content-Disposition", "attachment; filename=\"" + System.currentTimeMillis()+".xlsx\"");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + outputFileName + ".xlsx\"");
            XLSTransformer transformer = new XLSTransformer();
            workbook = transformer.transformXLS(in, model);
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();

        } catch (FileNotFoundException e) {
            logger.warn("The excel template file [" + excelTemplatePath + "] not found");
            response.setStatus(404);
            return;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            response.setStatus(404);
            return;
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Convenient method to obtain the cell in the given sheet, row and column. Creates the row and the cell if they
     * still doesn't already exist. Thus, the column can be passed as an int, the method making the needed downcasts.
     * 
     * @param sheet a sheet object. The first sheet is usually obtained by workbook.getSheetAt(0)
     * @param row thr row number
     * @param col the column number
     * @return the HSSFCell
     */
    protected HSSFCell getCell(HSSFSheet sheet, int row, int col) {
        HSSFRow sheetRow = sheet.getRow(row);
        if (sheetRow == null) {
            sheetRow = sheet.createRow(row);
        }
        HSSFCell cell = sheetRow.getCell((short)col);
        if (cell == null) {
            cell = sheetRow.createCell((short)col);
        }
        return cell;
    }

    /**
     * Convenient method to set a String as text content in a cell.
     * 
     * @param cell the cell in which the text must be put
     * @param text the text to put in the cell
     */
    protected void setText(HSSFCell cell, String text) {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(text);
    }

    private InputStream getTemplateSource(String url) throws Exception {
        return new FileInputStream(new java.io.File(url));
    }

    private InputStream getTemplateSource(URL url) throws Exception {
        System.out.println(url.getPath());
        return new FileInputStream(new File(url.getPath()));
    }

}

/*
 * public class ExcelView extends AbstractExcelView{ private static final String DEFAULT_CONTENT_TYPE =
 * "application/vnd.ms-excel"; private static final Log LOG = LogFactory.getLog(ExcelView.class); private String
 * excelTemplatePath; private boolean error; public ExcelView(){ setContentType(DEFAULT_CONTENT_TYPE); }
 * @Override protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest
 * rquest, HttpServletResponse response) throws Exception { InputStream in = null; try { in =
 * getTemplateSource(excelTemplatePath); response.setHeader("Content-Disposition", "attachment; filename=\"" +
 * System.currentTimeMillis()+".xls\""); XLSTransformer transformer = new XLSTransformer(); workbook =
 * transformer.transformXLS(in, map); OutputStream out = response.getOutputStream(); workbook.write(out); out.flush(); }
 * catch (FileNotFoundException e) { LOG.warn("The excel template file [" + excelTemplatePath+ "] not found");
 * response.setStatus(404); return; } finally { if (in != null) in.close(); } } private InputStream
 * getTemplateSource(String url) throws Exception { return new FileInputStream(new java.io.File(url)); } public String
 * getExcelTemplatePath() { return excelTemplatePath; } public void setExcelTemplatePath(String excelTemplatePath) {
 * this.excelTemplatePath = excelTemplatePath; } public boolean isError() { return error; } public void setError(boolean
 * error) { this.error = error; } }
 */

