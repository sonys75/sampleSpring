package com.skhynix.hydesign.portal.common.resolver;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.skhynix.hydesign.portal.common.constants.PortalConstants;
import com.skhynix.hydesign.portal.common.util.PortalUtil;

/**
 * <pre>
 * 파일업로드 Resolver
 * Object requestFileMap 형태의 파라미터로 받는 요청에 대해 처리
 * </pre>
 *
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
public class ParameterFileMappingResolver implements HandlerMethodArgumentResolver {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Configuration
     */
    @Autowired
    private Configuration configuration;

    /**
     * parameter 명 정의
     */
    private String parameterName;

    /**
     * fileMap 명 정의
     */
    protected String requestFileMap;

    /**
     * Default constructor
     */
    public ParameterFileMappingResolver() {
        this.requestFileMap = "requestFileMap";
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();

        this.parameterName = methodParameter.getParameterName();
        if (this.parameterName.startsWith(this.requestFileMap)) {

            Map<String, Object> fileMap = new HashMap<String, Object>();

            // IP 참고 파일이 아닌 첨부파일
            if (!this.parameterName.contains("IP")) {
                // OS 확인
                String userOS = request.getHeader("User-Agent");
                if (userOS.indexOf("Linux") > -1) {
                    return fileMap;
                }
            }

            MultipartHttpServletRequest multipartRequest = null;
            if (request.getContentType().contains("multipart/form-data")) {
                multipartRequest = (MultipartHttpServletRequest)webRequest.getNativeRequest();
            }

            Enumeration<?> enumeration = multipartRequest.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String key = (String)enumeration.nextElement();
                String[] values = request.getParameterValues(key);

                if (values != null) {
                    fileMap.put(key, (values.length > 1) ? values : values[0]);
                }
            }

            // file 임시저장 경로
            String tempPath = configuration.getString("portal.file.upload.path") + File.separatorChar
                              + configuration.getString("portal.file.upload.temp.path");
            File tempDir = new File(tempPath);

            if (logger.isDebugEnabled()) {
                logger.debug("tempPath : {}, dir : {}, exists : {}", new Object[] {tempPath,
                                                                                   tempDir.isDirectory(),
                                                                                   tempDir.exists()});
            }

            if (!tempDir.exists()) {
                FileUtils.forceMkdir(tempDir);
                if (logger.isDebugEnabled()) {
                    logger.debug("upload tmp dir [{}] is made.", tempPath);
                }
            }

            Iterator<String> multiFileList = multipartRequest.getFileNames();

            List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
            Map<String, Object> tMap = null;
            while (multiFileList.hasNext()) {
                tMap = new HashMap<String, Object>();

                MultipartFile multiFile = multipartRequest.getFile(multiFileList.next());

                // 실제 파일명
                String orgFileNm = multiFile.getOriginalFilename();
                if (!multiFile.isEmpty() && orgFileNm != null && !orgFileNm.isEmpty()) {

                    if (logger.isDebugEnabled()) {
                        logger.debug("original file name : {}", orgFileNm);
                    }

                    // validation 체크
                    if (!isValid(multiFile, fileMap)) {
                        return fileMap;
                    }

                    // file 임시저장경로 + 파일명
                    String phyFileNm = PortalUtil.getPhyFileName(orgFileNm);
                    File tempFile = new File(tempPath + File.separatorChar + phyFileNm);

                    try {
                        // file 임시저장위치에 이동
                        multiFile.transferTo(tempFile);

                        // file 정보 저장
                        tMap.put("orgFileNm", orgFileNm);
                        tMap.put("phyFileNm", phyFileNm);
                        tMap.put("fileSize", multiFile.getSize());

                        String fileExt = "";
                        if (orgFileNm.indexOf(".") != -1) {
                            fileExt = orgFileNm.substring(orgFileNm.lastIndexOf(".") + 1);
                        }
                        tMap.put("fileExt", fileExt);

                        fileList.add(tMap);

                    } catch (Exception e) {
                        e.fillInStackTrace();
                    }
                }
            }

            fileMap.put("fileList", fileList);

            if (logger.isDebugEnabled()) {
                logger.debug("{} : {}", requestFileMap, fileMap);
            }

            return fileMap;
        }

        return null;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.getParameterName().startsWith(this.requestFileMap)) {
            return true;
        }
        return false;
    }

    /**
     * 파일 유효성 여부
     *
     * @param multiFile 파일 정보
     * @param pMap fileMap
     * @return 파일 유효성 결과
     */
    public boolean isValid(MultipartFile multiFile, Map<String, Object> pMap) {

        String orgFileNm = multiFile.getOriginalFilename().toLowerCase(Locale.ENGLISH);
        String fileExt = orgFileNm.substring(orgFileNm.lastIndexOf(".") + 1);
//        String fileExt = configuration.getString("portal.file.upload.security.ext");
        String permitExt = configuration.getString("portal.file.upload.permit.ext");

        if (fileExt.length() <= 0 || !permitExt.contains(fileExt)) {
            pMap.put(PortalConstants.Response.VALID, PortalConstants.Response.SECURITY_EXT);
            return false;
        }

        if (orgFileNm.length() > 80) {
            pMap.put(PortalConstants.Response.VALID, PortalConstants.Response.FILE_NAME);
            return false;
        }

        long maxSize = 0L;
        if (this.parameterName.contains("COMMUNITY")) {
            maxSize = Long.parseLong(configuration.getString("portal.file.upload.community.max.size"));
        } else if (this.parameterName.contains("IP")) {
            maxSize = Long.parseLong(configuration.getString("portal.file.upload.ip.max.size"));
        } else if (this.parameterName.contains("SUPPORT")) {
            maxSize = Long.parseLong(configuration.getString("portal.file.upload.support.max.size"));
        } else if (this.parameterName.contains("TOOLS")) {
            maxSize = Long.parseLong(configuration.getString("portal.file.upload.tools.max.size"));
        } else if (this.parameterName.contains("INFORMATION")) {
            maxSize = Long.parseLong(configuration.getString("portal.file.upload.tools.max.size"));
        } else if (this.parameterName.contains("SERVERPOOL")) {
        	maxSize = Long.parseLong(configuration.getString("portal.file.upload.tools.max.size"));
        } else if (this.parameterName.contains("TRANSFERPC")) {
        	maxSize = Long.parseLong(configuration.getString("portal.file.upload.tools.max.size"));
        }

        long fileSize = multiFile.getSize();
        if (fileSize > maxSize) {
            pMap.put(PortalConstants.Response.VALID, PortalConstants.Response.SIZE_OVER);
            return false;
        }

        if (fileSize == 0L) {
            pMap.put(PortalConstants.Response.VALID, PortalConstants.Response.SIZE_ZERO);
            return false;
        }

        return true;
    }
}
