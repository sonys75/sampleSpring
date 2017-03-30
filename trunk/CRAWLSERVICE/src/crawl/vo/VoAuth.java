package crawl.vo;


/**
 * 권한정보
 */
public class VoAuth extends VoCommon {
	private String auth_no			= "";	//권한번호
	private String up_auth_no		= "";	//상위권한번호
	private String level 			= "";	//레벨
	private String up_auth_id		= "";	//상위권한아이디
	private String up_auth_nm		= "";	//상위권한명
	private String auth_id			= "";	//권한아이디
	private String auth_nm			= "";	//권한명
	
	public String getAuth_no() {
		return auth_no;
	}
	public void setAuth_no(String auth_no) {
		this.auth_no = auth_no;
	}
	public String getUp_auth_no() {
		return up_auth_no;
	}
	public void setUp_auth_no(String up_auth_no) {
		this.up_auth_no = up_auth_no;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getUp_auth_id() {
		return up_auth_id;
	}
	public void setUp_auth_id(String up_auth_id) {
		this.up_auth_id = up_auth_id;
	}
	public String getUp_auth_nm() {
		return up_auth_nm;
	}
	public void setUp_auth_nm(String up_auth_nm) {
		this.up_auth_nm = up_auth_nm;
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
}	
