/**
 * (주)오픈잇 | http://www.openit.co.kr
 * Copyright (c)2006-2015, openit Inc.
 * All rights reserved.
 */
package com.skhynix.hydesign.portal.common.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import net.sf.json.xml.XMLSerializer;

/**
 * json 공통화 클래스
 * 
 * @author <a href mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.0
 * @created 2015. 4. 7.
 */
public class ReliableQueryUtil {
	
    /*
     * 성능개선안 private final Logger logger = LoggerFactory.getLogger(getClass());
     */
    private static final String REQ_SESSION_INFO = "01";

    private static final String REQ_ERROR_INFO = "02";

    private static final String PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"urn:Aspera:XML:FASPSessionNET:2009/11:Types\">"
                                         + "<soapenv:Header></soapenv:Header>"
                                         + "<soapenv:Body>";

    private static final String SUFFIX = "</soapenv:Body>" + "</soapenv:Envelope>";

    private String RELIABLE_QUERY_URL = "http://10.96.103.203:40001/services/soap/Transfer-201210";

    public ReliableQueryUtil(String host) {

    	//Aspera 서버 정보 설정
    	if(!host.isEmpty()) {
    		RELIABLE_QUERY_URL = String.format("http://%s:40001/services/soap/Transfer-201210", host);
    	}
    }

    private String toRequestMessage(String querytype, String keyword) throws IllegalArgumentException {
        if (querytype.equals(REQ_SESSION_INFO)) {
            return getTransInfoMessage(keyword);
        } else if (querytype.equals(REQ_ERROR_INFO)) {
            return getErrorInfoMessage(keyword);
        } else {
            throw new IllegalArgumentException("Unknown query mothod.");
        }
    }

    //전송정보(세션,파일)를 조회하기위한 메시지
    private String getTransInfoMessage(String keyword) {
        String msg = PREFIX + "<typ:GetInfoRequest>"
                     + "<FileTransferFilter><Cookie>"
                     + keyword
                     + "</Cookie></FileTransferFilter>"
                     + "</typ:GetInfoRequest>"
                     + SUFFIX;
        return msg;
    }

    //전송중 Error내용조회
    private String getErrorInfoMessage(String keyword) {
        String msg = PREFIX + "<typ:GetFileTransferStatisticsRequest>"
                     + "<FileTransferFilter><Cookie>"
                     + keyword
                     + "</Cookie></FileTransferFilter>"
                     + "</typ:GetFileTransferStatisticsRequest>"
                     + SUFFIX;
        return msg;
    }

    @SuppressWarnings("finally")
    public Map<String, Object> doRestQueryMap(String querytype, String keyword) throws Exception {

        URL svcURL = new URL(RELIABLE_QUERY_URL);
        HttpURLConnection conn = (HttpURLConnection)svcURL.openConnection();
        String credential = "nodeuser1:aspera";
        //        String basicAuth = "Basic " + new String(DatatypeConverter.printBase64Binary(credential.getBytes()));
        String basicAuth = String.format("Basic %s",
                                         new String(DatatypeConverter.printBase64Binary(credential.getBytes())));
        byte[] request = toRequestMessage(querytype, keyword).getBytes();
        conn.setRequestMethod("POST");

        conn.setRequestProperty("Authorization", basicAuth);
        conn.setRequestProperty("SOAPAction", "FASPSessionNET-200911#GetSessionInfo");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        conn.setRequestProperty("Content-Length", "" + request.length);

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        OutputStream out = null;
        InputStream in = null;
        Map<String, Object> map = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            out = conn.getOutputStream();

            out.write(request);
            out.flush();

            in = conn.getInputStream();

            while (true) {
                int ch = in.read();

                if (ch < 0) {
                    break;
                }

                bos.write(ch);
            }

            bos.flush();

            map = xmlToMap(new String(bos.toByteArray()));

        } finally {
            if (out != null) PortalUtil.safeCloseOut(out);
            if (in != null) PortalUtil.safeCloseIn(in);
            if (bos != null) PortalUtil.safeCloseOut(bos);
        }
        return map;
        //        return convertNodesFromXml(new String(bos.toByteArray()));
    }

    private Map<String, Object> xmlToMap(String msg) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        Map<String, Object> map = (Map<String, Object>)xmlSerializer.read(msg);
        Object[] keys = map.keySet().toArray();
        map = (Map<String, Object>)map.get(keys[2]);//soapenv:Envelope
        keys = map.keySet().toArray();
        map = (Map<String, Object>)map.get(keys[0]);//soapenv:Body
        keys = map.keySet().toArray();
        map = (Map<String, Object>)map.get(keys[1]);//ns1:GetInfoResponse
        return map;
    }
}
