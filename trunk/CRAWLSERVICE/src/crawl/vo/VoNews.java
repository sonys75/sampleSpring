package crawl.vo;

/**
 * VoNews class
 */
public class VoNews extends VoCommon {

	private String provide_cd;		//뉴스제공사
	private String news_time;		//뉴스수집시간
	private String seq;				//순번
	private String subject;			//뉴스제목
	private String link;			//뉴스링크
	private String category;		//뉴스분류
	private String pubdate;			//발표시간
	
	
	public String getProvide_cd() {
		return provide_cd;
	}
	public void setProvide_cd(String provide_cd) {
		this.provide_cd = provide_cd;
	}
	public String getNews_time() {
		return news_time;
	}
	public void setNews_time(String news_time) {
		this.news_time = news_time;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}


}	
