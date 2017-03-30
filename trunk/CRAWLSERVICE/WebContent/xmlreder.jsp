<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.io.File" %>
<%@ page import="javax.xml.parsers.DocumentBuilder" %>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import="org.w3c.dom.Document"%>
<%@ page import="org.w3c.dom.NodeList"%>
<%
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder db = dbf.newDocumentBuilder();
Document document = db.parse(new File("c:\\input.xml"));
NodeList nodeList = document.getElementsByTagName("Item");
for(int x=0,size= nodeList.getLength(); x<size; x++) {
    out.println("<br>"+nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue() +" : "+nodeList.item(x).getTextContent());
}
%>
