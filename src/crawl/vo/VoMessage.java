package crawl.vo;

/**
 * VoMessage 
 */
public class VoMessage extends VoCommon {
	private String msg_id			= "";	//메세지아이디(pk)	n/a	number(9)	not null
	private String msg_title		= "";	//메세지제목
	private String corp_id			= "";	//회사 아이디
	private String corp_nm			= "";	//회사 아이디
	private String user_id			= "";	//사용자 아이디	n/a	varchar2(20)	not null
	private String msg_type			= "";	//메세지타입(G:일반, S:긴급)	n/a	char(1)	null
	private String msg_ord			= "";	//메세지타입별 일련번호
	private String msg_seq			= "";	//메세지일련번호
	private String msg				= "";	//메세지	n/a	varchar2(200)	null
	private String view_tm			= "";	//반복시간	n/a	number(4)	null
	private String use_yn			= "";	//사용여부	n/a	char(1)	null
	private String last_mod_dt		= "";	//최종변경시간	n/a	char(14)	null
	private String down_yn			= "";	//다운로드여부	n/a	char(1)	null
	private String del_yn			= "";	//삭제여부	n/a	char(1)	null	
	private String stp_id			= "";	//셋톱 아이디(pk)	n/a	varchar2(15)	not null
	private String stp_title		= "";	//셋톱 아이디
	private String stp_cnt			= "";	//셋톱 갯수
	private String msg_cnt			= "";	//메세지 갯수
	private String stplistmodYn		= "";	//셋탑 수정 여부
	private String msglistmodYn		= "";	//공지 수정 여부
	private String msg_end_dt		= "";	//메세지 종료시간
	
	private String[] arrStpId;
	private String[] arrStpTitle;
	private String[] arrMsgSeq;
	private String[] arrMsg;
	
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getMsg_title() {
		return msg_title;
	}
	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}
	public String getCorp_id() {
		return corp_id;
	}
	public void setCorp_id(String corp_id) {
		this.corp_id = corp_id;
	}
	public String getCorp_nm() {
		return corp_nm;
	}
	public void setCorp_nm(String corp_nm) {
		this.corp_nm = corp_nm;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getMsg_ord() {
		return msg_ord;
	}
	public void setMsg_ord(String msg_ord) {
		this.msg_ord = msg_ord;
	}
	public String getMsg_seq() {
		return msg_seq;
	}
	public void setMsg_seq(String msg_seq) {
		this.msg_seq = msg_seq;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getView_tm() {
		return view_tm;
	}
	public void setView_tm(String view_tm) {
		this.view_tm = view_tm;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getLast_mod_dt() {
		return last_mod_dt;
	}
	public void setLast_mod_dt(String last_mod_dt) {
		this.last_mod_dt = last_mod_dt;
	}
	public String getDown_yn() {
		return down_yn;
	}
	public void setDown_yn(String down_yn) {
		this.down_yn = down_yn;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public String getStp_id() {
		return stp_id;
	}
	public void setStp_id(String stp_id) {
		this.stp_id = stp_id;
	}
	public String getStp_title() {
		return stp_title;
	}
	public void setStp_title(String stp_title) {
		this.stp_title = stp_title;
	}
	public String getStp_cnt() {
		return stp_cnt;
	}
	public void setStp_cnt(String stp_cnt) {
		this.stp_cnt = stp_cnt;
	}
	public String getMsg_cnt() {
		return msg_cnt;
	}
	public void setMsg_cnt(String msg_cnt) {
		this.msg_cnt = msg_cnt;
	}
	public String getStplistmodYn() {
		return stplistmodYn;
	}
	public void setStplistmodYn(String stplistmodYn) {
		this.stplistmodYn = stplistmodYn;
	}
	public String getMsglistmodYn() {
		return msglistmodYn;
	}
	public void setMsglistmodYn(String msglistmodYn) {
		this.msglistmodYn = msglistmodYn;
	}
	public String getMsg_end_dt() {
		return msg_end_dt;
	}
	public void setMsg_end_dt(String msg_end_dt) {
		this.msg_end_dt = msg_end_dt;
	}
	public String[] getArrStpId() {
		return arrStpId;
	}
	public void setArrStpId(String[] arrStpId) {
		this.arrStpId = arrStpId;
	}
	public String[] getArrStpTitle() {
		return arrStpTitle;
	}
	public void setArrStpTitle(String[] arrStpTitle) {
		this.arrStpTitle = arrStpTitle;
	}
	public String[] getArrMsgSeq() {
		return arrMsgSeq;
	}
	public void setArrMsgSeq(String[] arrMsgSeq) {
		this.arrMsgSeq = arrMsgSeq;
	}
	public String[] getArrMsg() {
		return arrMsg;
	}
	public void setArrMsg(String[] arrMsg) {
		this.arrMsg = arrMsg;
	}
}	
