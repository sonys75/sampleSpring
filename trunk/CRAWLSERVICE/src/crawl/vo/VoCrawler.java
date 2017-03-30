package crawl.vo;


/**
 * VoCrawler
 */
public class VoCrawler extends VoCommon {
	private String part;			//수집구분
	private String title;			//수집제목
	private String crawl_url;		//수집주소
	private String crawl_part;		//수집메소드
	private String crawl_method;	//수집메소드
	private String crawl_yn;		//수집여부
	private String use_yn;			//게시판사용여부
	private String crawling_yn;		//수집실행여부
	
	private String seq;				//순번
	private String link;			//본문링크
	private String subject;			//링크제목
	private String content	;		//본문내용
	private String read_cnt	;		//본문조회수
	private String pubdate;			//게시날짜
	private String cont_yn;			//내용수집여부
	private String img_yn;			//이미지수집여부
	private String commit_yn;		//서비스이관여부
	
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCrawl_url() {
		return crawl_url;
	}
	public void setCrawl_url(String crawl_url) {
		this.crawl_url = crawl_url;
	}
	public String getCrawl_part() {
		return crawl_part;
	}
	public void setCrawl_part(String crawl_part) {
		this.crawl_part = crawl_part;
	}
	public String getCrawl_method() {
		return crawl_method;
	}
	public void setCrawl_method(String crawl_method) {
		this.crawl_method = crawl_method;
	}
	public String getCrawl_yn() {
		return crawl_yn;
	}
	public void setCrawl_yn(String crawl_yn) {
		this.crawl_yn = crawl_yn;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getCrawling_yn() {
		return crawling_yn;
	}
	public void setCrawling_yn(String crawling_yn) {
		this.crawling_yn = crawling_yn;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRead_cnt() {
		return read_cnt;
	}
	public void setRead_cnt(String read_cnt) {
		this.read_cnt = read_cnt;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	public String getCont_yn() {
		return cont_yn;
	}
	public void setCont_yn(String cont_yn) {
		this.cont_yn = cont_yn;
	}
	public String getImg_yn() {
		return img_yn;
	}
	public void setImg_yn(String img_yn) {
		this.img_yn = img_yn;
	}
	public String getCommit_yn() {
		return commit_yn;
	}
	public void setCommit_yn(String commit_yn) {
		this.commit_yn = commit_yn;
	}
}	
