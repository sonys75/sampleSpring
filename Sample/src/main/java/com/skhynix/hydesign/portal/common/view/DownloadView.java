package com.skhynix.hydesign.portal.common.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * 파일 다운로드 뷰
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
public class DownloadView extends AbstractView {

    /**
     * Default Constructor
     */
    public DownloadView() {
        setContentType("application/octet-stream; charset=utf-8");
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map,
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        File file = (File)model.get("file");
        String fileName = (String)model.get("fileName");

        response.setContentType(getContentType());
        response.setContentLength((int)file.length());

        String userAgent = request.getHeader("User-Agent");

        if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) {
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "utf-8")
                                                                                            .replaceAll("\\+", "%20")
                                                      + "\";");
        } else {
            response.setHeader("Content-Disposition",
                               "attachment; fileName=\"" + new String(fileName.getBytes("utf-8"), "8859_1") + "\";");
        }

        response.setHeader("Content-Transfer-Encoding", "binary");

        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
            out.flush();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            if (fis != null) {
                fis.close();
            }

            if (out != null) {
                out.close();
            }
        }
    }
}
