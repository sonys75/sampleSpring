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

import com.skhynix.hydesign.portal.common.util.PortalUtil;

/**
 * 이미지 보기 뷰
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
public class ImageView extends AbstractView {

    /**
     * Default Constructor
     */
    public ImageView() {
        setContentType("image/jpeg; charset=utf-8");
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

        response.setContentType(getContentType());
        response.setContentLength((int)file.length());

        String userAgent = request.getHeader("User-Agent");

        if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) {
            response.setHeader("Content-Disposition",
                               "attachment; fileName=\"" + URLEncoder.encode(file.getName(), "utf-8").replaceAll("\\+",
                                                                                                                 "%20")
                                           + "\";");
        } else {
            response.setHeader("Content-Disposition",
                               "attachment; fileName=\"" + new String(file.getName().getBytes("utf-8"), "8859_1")
                                           + "\";");
        }

        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
            out.flush();
        } finally {
            if (out != null) PortalUtil.safeCloseOut(out);
            if (fis != null) PortalUtil.safeCloseIn(fis);
        }
    }
}
