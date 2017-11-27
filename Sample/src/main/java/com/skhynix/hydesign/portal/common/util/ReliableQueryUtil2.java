/**
 * (주)오픈잇 | http://www.openit.co.kr
 * Copyright (c)2006-2015, openit Inc.
 * All rights reserved.
 */
package com.skhynix.hydesign.portal.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * json 공통화 클래스 (사용안함)
 * 
 * @author <a href mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.0
 * @created 2015. 4. 7.
 */
public class ReliableQueryUtil2 {
//
//    /*
//     * 성능개선안 private final Logger logger = LoggerFactory.getLogger(getClass());
//     */
//    private static final int QUERY_BY_JOB_ID = 1;
//
//    private static final int QUERY_BY_COOKIE = 2;
//
//    //    private static final String SOAP_REQUEST_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"urn:Aspera:XML:FASPSessionNET:2009/11:Types\">"
//    //                                                      + "<soapenv:Header></soapenv:Header>"
//    //                                                      + "<soapenv:Body><typ:GetFileTransferStatisticsRequest><FileTransferFilter>";
//    //
//    //        private static final String SOAP_REQUEST_SUFFIX = "<TransferStatus>error</TransferStatus>" + "</FileTransferFilter>"
//    //                                                          + "</typ:GetFileTransferStatisticsRequest>"
//    //                                                          + "</soapenv:Body>"
//    //                                                          + "</soapenv:Envelope>";
//    private static final String SOAP_REQUEST_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"urn:Aspera:XML:FASPSessionNET:2009/11:Types\">"
//                                                      + "<soapenv:Header></soapenv:Header>"
//                                                      + "<soapenv:Body>"
//                                                      + "<typ:GetSessionInfoRequest>"
//                                                      + "<SessionFilter>";
//
//    private static final String SOAP_REQUEST_SUFFIX = "</SessionFilter>" + "</typ:GetSessionInfoRequest>"
//                                                      + "</soapenv:Body>"
//                                                      + "</soapenv:Envelope>";
//
//    private final String RELIABLE_QUERY_URL = "http://10.96.103.203:40001/services/soap/Transfer-201210";
//
//    public ReliableQueryUtil2() {
//
//    }
//
//    private String toRequestMessage(int method, String keyword) throws IllegalArgumentException {
//        if (method == QUERY_BY_JOB_ID) {
//            return toMessageWithJobId(keyword);
//        } else if (method == QUERY_BY_COOKIE) {
//            return toMessageWithCookie(keyword);
//        } else {
//            throw new IllegalArgumentException("Unknown query mothod.");
//        }
//    }
//
//    private String toMessageWithJobId(String keyword) {
//        System.out.println(SOAP_REQUEST_PREFIX + "<JobId>" + keyword + "</JobId>" + SOAP_REQUEST_SUFFIX);
//        return SOAP_REQUEST_PREFIX + "<JobId>" + keyword + "</JobId>" + SOAP_REQUEST_SUFFIX;
//    }
//
//    private String toMessageWithCookie(String keyword) {
//        System.out.println(SOAP_REQUEST_PREFIX + "<Cookie>" + keyword + "</Cookie>" + SOAP_REQUEST_SUFFIX);
//        return SOAP_REQUEST_PREFIX + "<Cookie>" + keyword + "</Cookie>" + SOAP_REQUEST_SUFFIX;
//    }
//
//    public String doRestQuery(int method, String keyword) throws Exception {
//
//        URL svcURL = new URL(RELIABLE_QUERY_URL);
//        HttpURLConnection conn = (HttpURLConnection)svcURL.openConnection();
//        String credential = "nodeuser1:aspera";
//        //        String basicAuth = "Basic " + new String(DatatypeConverter.printBase64Binary(credential.getBytes()));
//        String basicAuth = new StringBuilder().append("Basic")
//                                              .append(new String(DatatypeConverter.printBase64Binary(credential.getBytes())))
//                                              .toString();
//        byte[] request = toRequestMessage(method, keyword).getBytes();
//
//        conn.setRequestMethod("POST");
//
//        conn.setRequestProperty("Authorization", basicAuth);
//        conn.setRequestProperty("SOAPAction", "FASPSessionNET-200911#GetFileTransfers");
//        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
//        conn.setRequestProperty("Content-Length", "" + request.length);
//
//        conn.setUseCaches(false);
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
//
//        OutputStream out = conn.getOutputStream();
//
//        out.write(request);
//        out.flush();
//
//        InputStream in = conn.getInputStream();
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//        try {
//
//            while (true) {
//                int ch = in.read();
//
//                if (ch < 0) {
//                    break;
//                }
//
//                bos.write(ch);
//            }
//
//            bos.flush();
//        } catch (Exception e) {
//
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//                if (bos != null) {
//                    bos.close();
//                }
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//            }
//        }
//        return new String(bos.toByteArray());
//    }
//
//    public Map<String, Object> doRestQueryMap(int method, String keyword) throws Exception {
//
//        URL svcURL = new URL(RELIABLE_QUERY_URL);
//        HttpURLConnection conn = (HttpURLConnection)svcURL.openConnection();
//        String credential = "nodeuser1:aspera";
//        String basicAuth = "Basic " + new String(DatatypeConverter.printBase64Binary(credential.getBytes()));
//        byte[] request = toRequestMessage(method, keyword).getBytes();
//        conn.setRequestMethod("POST");
//
//        conn.setRequestProperty("Authorization", basicAuth);
//        conn.setRequestProperty("SOAPAction", "FASPSessionNET-200911#GetSessionInfo");
//        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
//        conn.setRequestProperty("Content-Length", "" + request.length);
//
//        conn.setUseCaches(false);
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
//
//        OutputStream out = conn.getOutputStream();
//
//        out.write(request);
//        out.flush();
//
//        InputStream in = conn.getInputStream();
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        String rsltStr = "";
//        try {
//            while (true) {
//                int ch = in.read();
//
//                if (ch < 0) {
//                    break;
//                }
//
//                bos.write(ch);
//            }
//
//            bos.flush();
//            rsltStr = new String(bos.toByteArray());
//        } catch (Exception e) {
//
//        } finally {
//            if (out != null) PortalUtil.safeCloseOut(out);
//            if (in != null) PortalUtil.safeCloseIn(in);
//            if (bos != null) PortalUtil.safeCloseOut(bos);
//        }
////        System.out.println(new String(bos.toByteArray()));
//        return convertNodesFromXml(rsltStr);
//    }
//
//    public static Map<String, Object> convertNodesFromXml(String xml) throws SAXException,
//                                                                     IOException,
//                                                                     ParserConfigurationException {
//        InputStream is = null;
//        try {
//            is = new ByteArrayInputStream(xml.getBytes());
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            dbf.setNamespaceAware(true);
//            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
//            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
//
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document document = db.parse(is);
//            return createMap(document.getDocumentElement());
//        } catch (SAXException ex) {
//            //        Logger.getLogger(BaseDataLoader.class.getName()).log(Level.SEVERE, null, ex);
//            throw ex;
//        } catch (IOException ex) {
//            //        Logger.getLogger(BaseDataLoader.class.getName()).log(Level.SEVERE, null, ex);
//            throw ex;
//        } catch (ParserConfigurationException ex) {
//            //        Logger.getLogger(BaseDataLoader.class.getName()).log(Level.SEVERE, null, ex);
//            throw ex;
//        } finally {
//            if (is != null) PortalUtil.safeCloseIn(is);
//        }
//    }
//
//    public static Map<String, Object> createMap(Node node) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        NodeList nodeList = node.getChildNodes();
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node currentNode = nodeList.item(i);
//            if (currentNode.hasAttributes()) {
//                for (int j = 0; j < currentNode.getAttributes().getLength(); j++) {
//                    Node item = currentNode.getAttributes().item(i);
//                    map.put(item.getNodeName(), item.getTextContent());
//                }
//            }
//            if (node.getFirstChild() != null && node.getFirstChild().getNodeType() == Node.ELEMENT_NODE) {
//                map.putAll(createMap(currentNode));
//            } else if (node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
//                map.put(node.getLocalName(), node.getTextContent());
//            }
//        }
//        return map;
//    }
    
}
