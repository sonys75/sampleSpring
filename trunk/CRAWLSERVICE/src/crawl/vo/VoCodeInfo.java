package crawl.vo;


/**
 * 코드정보
 */
public class VoCodeInfo extends VoCommon {

	private String code_part		= "";	//코드그룹
	private String code_part_nm		= "";	//코드그룹명
	private String code				= "";	//코드
	private String code_nm			= "";	//코드명
	private String code_ord			= "";	//코드정렬순서
	private String code_desc		= "";	//코드설명
	
	public String getCode_part() {
		return code_part;
	}
	public void setCode_part(String code_part) {
		this.code_part = code_part;
	}
	public String getCode_part_nm() {
		return code_part_nm;
	}
	public void setCode_part_nm(String code_part_nm) {
		this.code_part_nm = code_part_nm;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode_nm() {
		return code_nm;
	}
	public void setCode_nm(String code_nm) {
		this.code_nm = code_nm;
	}
	public String getCode_ord() {
		return code_ord;
	}
	public void setCode_ord(String code_ord) {
		this.code_ord = code_ord;
	}
	public String getCode_desc() {
		return code_desc;
	}
	public void setCode_desc(String code_desc) {
		this.code_desc = code_desc;
	}

	
}	
