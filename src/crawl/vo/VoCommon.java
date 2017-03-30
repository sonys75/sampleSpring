package crawl.vo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import crawl.cmm.web.ListPaging;


/**
 * Class 공통 정보만 모아 놓은 Class
 */
public class VoCommon {
	private String retMessage	= "";	//리턴 Message
	private String retUrl		= "";	//리턴 URL
	private String retTarget	= "";	//리턴 Target
	
	public String getRetMessage() {
		return retMessage;
	}
	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}
	public String getRetUrl() {
		return retUrl;
	}
	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}
	public String getRetTarget() {
		return retTarget;
	}
	public void setRetTarget(String retTarget) {
		this.retTarget = retTarget;
	}

	private String last_mod_dt	= "";	//수정일
	private String mod_dt		= "";	//수정일
	private String mod_id		= "";	//수정자아이디
	private String mod_ip		= "";	//수정자아이피
	private String reg_dt		= "";	//등록일
	private String reg_id		= "";	//등록자아이디
	private String reg_ip		= "";	//등록자아이피

	public String getLast_mod_dt() {
		return last_mod_dt;
	}
	public void setLast_mod_dt(String last_mod_dt) {
		this.last_mod_dt = last_mod_dt;
	}
	public String getMod_dt() {
		return mod_dt;
	}
	public void setMod_dt(String mod_dt) {
		this.mod_dt = mod_dt;
	}
	public String getMod_id() {
		return mod_id;
	}
	public void setMod_id(String mod_id) {
		this.mod_id = mod_id;
	}
	public String getMod_ip() {
		return mod_ip;
	}
	public void setMod_ip(String mod_ip) {
		this.mod_ip = mod_ip;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}
	public String getReg_ip() {
		return reg_ip;
	}
	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
	}
 
	private String s_user_id	= "";	//사용자 아이디
	private String s_corp_id	= "";	//회사 아이디
	private String s_user_nm	= "";	//사용자 이름
	private String s_user_email	= "";	//사용자 이메일
	private String s_user_auth	= "";	//사용자 권한
	private String s_use_yn		= "";	//아이디 사용여부
	private String s_user_ip	= "";	//사용자아이피
	
	public String getS_user_id() {
		return s_user_id;
	}
	public void setS_user_id(String s_user_id) {
		this.s_user_id = s_user_id;
	}
	public String getS_corp_id() {
		return s_corp_id;
	}
	public void setS_corp_id(String s_corp_id) {
		this.s_corp_id = s_corp_id;
	}
	public String getS_user_nm() {
		return s_user_nm;
	}
	public void setS_user_nm(String s_user_nm) {
		this.s_user_nm = s_user_nm;
	}
	public String getS_user_email() {
		return s_user_email;
	}
	public void setS_user_email(String s_user_email) {
		this.s_user_email = s_user_email;
	}
	public String getS_user_auth() {
		return s_user_auth;
	}
	public void setS_user_auth(String s_user_auth) {
		this.s_user_auth = s_user_auth;
	}
	public String getS_use_yn() {
		return s_use_yn;
	}
	public void setS_use_yn(String s_use_yn) {
		this.s_use_yn = s_use_yn;
	}
	public String getS_user_ip() {
		return s_user_ip;
	}
	public void setS_user_ip(String s_user_ip) {
		this.s_user_ip = s_user_ip;
	}
	
	public void getSessionData(HttpServletRequest request) {
		// Session true: 세션개체가 없을 경우 세션을 생성한후 세션에 기록한다.
		HttpSession session = request.getSession(true);
		this.s_user_id 		= (String)session.getAttribute("s_user_id");
		this.s_corp_id 		= (String)session.getAttribute("s_corp_id");
		this.s_user_nm 		= (String)session.getAttribute("s_user_nm");
		this.s_user_email 	= (String)session.getAttribute("s_user_email");
		this.s_user_auth	= (String)session.getAttribute("s_user_auth");
		this.s_use_yn		= (String)session.getAttribute("s_use_yn");
		this.s_user_ip		= request.getRemoteAddr();
	}

	private ListPaging listPaging;

	public ListPaging getListPaging() {
		return listPaging;
	}
	public void setListPaging(ListPaging listPaging) {
		this.listPaging = listPaging;
	}
	
	private String rowno		= "";	//글 번호
	
	public String getRowno() {
		return rowno;
	}
	public void setRowno(String rowno) {
		this.rowno = rowno;
	}
	
	//데이터 정렬 관련
	private String ord_fld		= "";	//정렬필드
	private String ord_sort		= "";	//정렬순서
	
	public String getOrd_fld() {
		return ord_fld;
	}
	public void setOrd_fld(String ord_fld) {
		this.ord_fld = ord_fld;
	}

	public String getOrd_sort() {
		return ord_sort;
	}
	public void setOrd_sort(String ord_sort) {
		this.ord_sort = ord_sort;
	}

	//검색어 관련
	private String sch_fld		= "";	//검색구분
	private String sch_word		= "";	//검색어
 
	public String getSch_fld() {
		return sch_fld;
	}
	public void setSch_fld(String sch_fld) {
		this.sch_fld = sch_fld;
	}
	public String getSch_word() {
		return sch_word;
	}
	public void setSch_word(String sch_word) {
		this.sch_word = sch_word;
	}
	
	//데이터 처리 관련
	private String mode			= "";	//데이터 처리 구분자
	private String page_no		= "";	//페이지 번호

	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getPage_no() {
		return page_no;
	}
	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}
	
	//게시판 관련
	private String part			= "";	//게시판 구분자
	private String sort			= "";	//게시판 정렬

	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}
