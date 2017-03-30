package crawl.vo;


/**
 * 권한정보
 */
public class VoMenu extends VoCommon {
	private String menu_id			= "";	//메뉴아이디
	private String up_menu_id		= "";	//상위메뉴아이디
	private String menu_nm			= "";	//메뉴명
	private String up_menu_nm		= "";	//상위메뉴명
	private String level 			= "";	//레벨
	private String menu_url			= "";	//메뉴URL
	private String auth_no			= "";	//권한번호
	private String auth_id			= "";	//권한아이디
	private String auth_nm			= "";	//권한명
	
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getUp_menu_id() {
		return up_menu_id;
	}
	public void setUp_menu_id(String up_menu_id) {
		this.up_menu_id = up_menu_id;
	}
	public String getMenu_nm() {
		return menu_nm;
	}
	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
	}
	public String getUp_menu_nm() {
		return up_menu_nm;
	}
	public void setUp_menu_nm(String up_menu_nm) {
		this.up_menu_nm = up_menu_nm;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public String getAuth_no() {
		return auth_no;
	}
	public void setAuth_no(String auth_no) {
		this.auth_no = auth_no;
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
