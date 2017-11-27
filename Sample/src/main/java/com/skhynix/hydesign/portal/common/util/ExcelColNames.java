/**
 * 
 * 파 일 명 : com.skhynix.farms.common.util.ExcelReadOption.java<BR>
 * 파일설명 : Excel 파일을 읽을 때 옵션을 설정하는 java 파일<BR>
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

import java.util.ArrayList;
import java.util.List;
 
public class ExcelColNames {
    
    private List<String> colList;
    private List<String> titleList;
   
	public List<String> getColList() {
		return colList;
	}
	
	public List<String> geTitleList() {
		return titleList;
	}
		
	public void setESCComList() {

		List<String> temp = new ArrayList<String>();

		temp.add(0, "serverGubun");
		temp.add(1, "clusterName");
		temp.add(2, "logPath");
		temp.add(3, "serverDescription");
		temp.add(4, "hostName");
		temp.add(5, "primary");
		temp.add(6, "ip1");
		temp.add(7, "ip2");
		temp.add(8, "ip3");
		temp.add(9, "ip4");
		temp.add(10, "url");
		temp.add(11, "location");
		temp.add(12, "detailedLocation");
		temp.add(13, "alias");
		temp.add(14, "sn");
		temp.add(15, "serverVirtualization");
		temp.add(16, "virtualHost");
		temp.add(17, "os");
		temp.add(18, "memory");
		temp.add(19, "cpuCore");
		temp.add(20, "hdd");
		temp.add(21, "mac");
		temp.add(22, "macAssign");

		this.colList = temp;
	}
		
	public void setECSComTitleList() {

		List<String> temp = new ArrayList<String>();

		temp.add(0, "ECS 서버");
		temp.add(1, "Cluster Name");
		temp.add(2, "Log Path");
		temp.add(3, "서버 설명");
		temp.add(4, "Host Name");
		temp.add(5, "Primary");
		temp.add(6, "IP1");
		temp.add(7, "IP2");
		temp.add(8, "IP3");
		temp.add(9, "IP4");
		temp.add(10, "접속 URL");
		temp.add(11, "위치(IDC)");
		temp.add(12, "상세위치");
		temp.add(13, "Alias");
		temp.add(14, "S/N");
		temp.add(15, "서버가상화");
		temp.add(16, "가상화Host");
		temp.add(17, "OS");
		temp.add(18, "메모리");
		temp.add(19, "CPU Core");
		temp.add(20, "HDD");
		temp.add(21, "MAC");
		temp.add(22, "MAC할당방법");

		this.titleList = temp;
	}
	
	public void setProjectComList() {
		
		List<String> temp = new ArrayList<String>();
		
		temp.add(0, "serverGubun");
		temp.add(1, "serverDescription");
		temp.add(2, "hostName");
		temp.add(3, "ip1");
		temp.add(4, "ip2");
		temp.add(5, "ip3");
		temp.add(6, "ip4");
		temp.add(7, "location");
		temp.add(8, "detailedLocation");
		temp.add(9, "alias");
		temp.add(10, "sn");
		temp.add(11, "serverVirtualization");
		temp.add(12, "virtualHost");
		temp.add(13, "os");
		temp.add(14, "memory");
		temp.add(15, "cpuCore");
		temp.add(16, "hdd");
		temp.add(17, "mac");
		temp.add(18, "macAssign");
		
		this.colList = temp;
	}
	
	public void setProjectComTitleList() {
		
		List<String> temp = new ArrayList<String>();
		
		temp.add(0,"서버구분");
		temp.add(1,"서버설명");
		temp.add(2,"Host Name");
		temp.add(3,"IP1");
		temp.add(4,"IP2");
		temp.add(5,"IP3");
		temp.add(6,"IP4");
		temp.add(7,"위치(IDC)");
		temp.add(8,"상세위치");
		temp.add(9,"Alias");
		temp.add(10,"S/N");
		temp.add(11,"서버가상화");
		temp.add(12,"가상화Host");
		temp.add(13,"OS");
		temp.add(14,"메모리");
		temp.add(15,"CPU Core");
		temp.add(16,"HDD");
		temp.add(17,"MAC");
		temp.add(18,"MAC할당방법");
		
		this.titleList = temp;
	}
	
	public void setTeamComList() {
		
		List<String> temp = new ArrayList<String>();
		
		temp.add(0, "serverGubun");
		temp.add(1, "indexName");
		temp.add(2, "serverDescription");
		temp.add(3, "hostName");
		temp.add(4, "ip1");
		temp.add(5, "ip2");
		temp.add(6, "ip3");
		temp.add(7, "ip4");
		temp.add(8, "location");
		temp.add(9, "detailedLocation");
		temp.add(10, "alias");
		temp.add(11, "sn");
		temp.add(12, "serverVirtualization");
		temp.add(13, "virtualHost");
		temp.add(14, "os");
		temp.add(15, "memory");
		temp.add(16, "cpuCore");
		temp.add(17, "hdd");
		temp.add(18, "mac");
		temp.add(19, "macAssign");
		
		this.colList = temp;
	}
	
	public void setTeamComTitleList() {
		
		List<String> temp = new ArrayList<String>();
		
		temp.add(0,"서버구분");
		temp.add(1,"Index Name");
		temp.add(2,"서버설명");
		temp.add(3,"Host Name");
		temp.add(4,"IP1");
		temp.add(5,"IP2");
		temp.add(6,"IP3");
		temp.add(7,"IP4");
		temp.add(8,"위치(IDC)");
		temp.add(9,"상세위치");
		temp.add(10,"Alias");
		temp.add(11,"S/N");
		temp.add(12,"서버가상화");
		temp.add(13,"가상화Host");
		temp.add(14,"OS");
		temp.add(15,"메모리");
		temp.add(16,"CPU Core");
		temp.add(17,"HDD");
		temp.add(18,"MAC");
		temp.add(19,"MAC할당방법");
		
		this.titleList = temp;
	}

	
}
