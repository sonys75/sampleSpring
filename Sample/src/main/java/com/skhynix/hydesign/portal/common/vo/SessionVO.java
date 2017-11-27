package com.skhynix.hydesign.portal.common.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 세션에 저장되는 정보
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
public class SessionVO implements Serializable {

	/**
     * serialVersionUID
     */
	private static final long serialVersionUID = -7023802014112800657L;

	private String origEmpNo;	//실제 사번(관리자 우회 로그인시)
	
	private String empNo;

    private String empNm;

    private String orgCd;

    private String orgNm;

    private String jtitCd; // 직위코드

    private String jtitNm; // 직위명( ex, 책임, 선임, 상무(그룹장))

    private String jpstnCd; // 직급코드

    private String jpstnNm; // 직급명 (ex, 그룹장)

    private String groupCd;

    private String groupNm;

    private List<Object> gnbMenuList;

    private Map<String, Object> menuListMap;

    private String serverDist;

    private String ipAddress;

    private String ipDistCd; // 해외 구분코드
    
    private String corpNm; // 법인명
    
    private String supportManagerYn; // support 담당자 여부
    
    private String adminAuthYn;
    
    private long loginHistId; // 관리자 접속 이력 ID 

    /**
     * Default Constructor
     */
    public SessionVO() {
        // Default Constructor
    }
    
    public String getOrigEmpNo() {
		return origEmpNo;
	}
	public void setOrigEmpNo(String origEmpNo) {
		this.origEmpNo = origEmpNo;
	}

	/**
     * @return
     */
    public String getEmpNo() {
        return empNo;
    }

    /**
     * @param empNo - the empNo to set
     */
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    /**
     * @return
     */
    public String getEmpNm() {
        return empNm;
    }

    /**
     * @param empNm - the empNm to set
     */
    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    /**
     * @return
     */
    public String getOrgCd() {
        return orgCd;
    }

    /**
     * @param orgCd - the orgCd to set
     */
    public void setOrgCd(String orgCd) {
        this.orgCd = orgCd;
    }

    /**
     * @return
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * @param orgNm - the orgNm to set
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * @return
     */
    public String getJtitCd() {
        return jtitCd;
    }

    /**
     * @param jtitCd - the jtidCd to set
     */
    public void setJtitCd(String jtitCd) {
        this.jtitCd = jtitCd;
    }

    /**
     * @return
     */
    public String getJtitNm() {
        return jtitNm;
    }

    /**
     * @param jtitCd - the jtidCd to set
     */
    public void setJtitNm(String jtitNm) {
        this.jtitNm = jtitNm;
    }

    /**
     * @return
     */
    public String getJpstnCd() {
        return jpstnCd;
    }

    /**
     * @param jtitCd - the jtidCd to set
     */
    public void setJpstnCd(String jpstnCd) {
        this.jpstnCd = jpstnCd;
    }

    /**
     * @return
     */
    public String getJpstnNm() {
        return jpstnNm;
    }

    /**
     * @param jtitCd - the jtidCd to set
     */
    public void setJpstnNm(String jpstnNm) {
        this.jpstnNm = jpstnNm;
    }

    /**
     * @return
     */
    public String getGroupCd() {
        return groupCd;
    }

    /**
     * @param groupCd - the groupCd to set
     */
    public void setGroupCd(String groupCd) {
        this.groupCd = groupCd;
    }

    /**
     * @return
     */
    public String getGroupNm() {
        return groupNm;
    }

    /**
     * @param groupNm - the groupNm to set
     */
    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }

    /**
     * @return
     */
    public List<Object> getGnbMenuList() {
        return gnbMenuList;
    }

    /**
     * @param gnbMenuList - the gnbMenuList to set
     */
    public void setGnbMenuList(List<Object> gnbMenuList) {
        this.gnbMenuList = gnbMenuList;
    }

    /**
     * @return
     */
    public Map<String, Object> getMenuListMap() {
        return menuListMap;
    }

    /**
     * @param menuListMap - the menuListMap to set
     */
    public void setMenuListMap(Map<String, Object> menuListMap) {
        this.menuListMap = menuListMap;
    }

    /**
     * @return
     */
    public String getServerDist() {
        return serverDist;
    }

    /**
     * @param serverDist
     */
    public void setServerDist(String serverDist) {
        this.serverDist = serverDist;
    }

    /**
     * @return
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return
     */
    public String getIpDistCd() {
        return ipDistCd;
    }

    /**
     * @param ipDistCd
     */
    public void setIpDistCd(String ipDistCd) {
        this.ipDistCd = ipDistCd;
    }

    /**
     * @return
     */
    public String getCorpNm() {
		return corpNm;
	}

	/**
	 * @param corpNm
	 */
	public void setCorpNm(String corpNm) {
		this.corpNm = corpNm;
	}

	/**
	 * @return
	 */
	public String getSupportManagerYn() {
		return supportManagerYn;
	}

	/**
	 * @param supportManagerYn
	 */
	public void setSupportManagerYn(String supportManagerYn) {
		this.supportManagerYn = supportManagerYn;
	}

	public String getAdminAuthYn() {
		return adminAuthYn;
	}

	public void setAdminAuthYn(String adminAuthYn) {
		this.adminAuthYn = adminAuthYn;
	}

	public long getLoginHistId() {
		return loginHistId;
	}

	public void setLoginHistId(long loginHistId) {
		this.loginHistId = loginHistId;
	}

	/**
     * toString overriding
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
