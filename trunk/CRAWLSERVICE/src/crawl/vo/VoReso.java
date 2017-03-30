package crawl.vo;


/**
 * 자원정보
 */
public class VoReso extends VoCommon {

	private String reso_id			= "";	//자원아이디
	private String reso_nm			= "";	//자원명
	private String reso_ptn			= "";	//자원패턴
	private String reso_tp			= "";	//자원타입
	private String reso_ord			= "";	//자원정렬번호
 
	private String auth_id			= "";	//권한아이디
	private String auth_nm			= "";	//권한명
	private String auth_cnt			= "";	//권한개수
	
	public String getReso_id() {
		return reso_id;
	}
	public void setReso_id(String reso_id) {
		this.reso_id = reso_id;
	}
	public String getReso_nm() {
		return reso_nm;
	}
	public void setReso_nm(String reso_nm) {
		this.reso_nm = reso_nm;
	}
	public String getReso_ptn() {
		return reso_ptn;
	}
	public void setReso_ptn(String reso_ptn) {
		this.reso_ptn = reso_ptn;
	}
	public String getReso_tp() {
		return reso_tp;
	}
	public void setReso_tp(String reso_tp) {
		this.reso_tp = reso_tp;
	}
	public String getReso_ord() {
		return reso_ord;
	}
	public void setReso_ord(String reso_ord) {
		this.reso_ord = reso_ord;
	}
	public String getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
	public String getAuth_nm() {
		return auth_nm;
	}
	public void setAuth_nm(String auth_nm) {
		this.auth_nm = auth_nm;
	}
	public String getAuth_cnt() {
		return auth_cnt;
	}
	public void setAuth_cnt(String auth_cnt) {
		this.auth_cnt = auth_cnt;
	}
}	
