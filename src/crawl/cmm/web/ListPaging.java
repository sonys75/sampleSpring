package crawl.cmm.web;

import java.io.Serializable;

/**
 * 게시판 페이징 처리
 */
public class ListPaging
        implements Serializable {

    private int totalCount;
    private int currentPage;
    private int perPage;
    private int perBlock;
    private int currentBlock;
    private String movePage;
    private String commonPara;
    private String search_field;
    private String search_keyword;

    public ListPaging() {
        this.perPage = 10;
        this.perBlock = 10;
        this.currentPage = 1;
        this.commonPara = "";
        this.search_field = "";
        this.search_keyword = "";
    }

    public ListPaging(int page_no) {
        if ((page_no < 1) || (page_no == 1)) {
            page_no = 1;
        }
        this.perPage = 10;
        this.currentPage = page_no;
        this.perBlock = 10;
    }

    public int getStartLine() {
        //  (조회 페이지-1)*게시물 조회 갯수)
        return (getPerPage() * (getCurrentPage() - 1) + 1); //oracle
//        return (getPerPage() * (getCurrentPage() - 1));
    }

    public int getEndLine() {
        return (getStartLine() + getPerPage() - 1);
//        return getPerPage();
    }

    public int getTotalPage() {
        int totalPage = 0;
        if (getTotalCount() % getPerPage() == 0) {
            if (getTotalCount() == 0) {
                totalPage = 1;
            } else {
                totalPage = getTotalCount() / getPerPage();
            }
        } else {
            totalPage = getTotalCount() / getPerPage() + 1;
        }

        return totalPage;
    }

    protected int getTotalBlock() {
        int totalBlock = 0;
        if (getTotalPage() % getPerBlock() == 0) {
            totalBlock = getTotalPage() / getPerBlock();
        } else {
            totalBlock = getTotalPage() / getPerBlock() + 1;
        }

        return totalBlock;
    }

    protected String getOnePageHtml(int page_no) {
        String url = "";
        if (getCurrentPage() == page_no) {
            url += url + "<strong>" + String.valueOf(page_no) + "</strong>";
        } else {
            url += url + "<a href=\"" + getMoveUri(page_no) + "\" title=\""+page_no+" 페이지\">" + page_no + "</a>";
        }
        url += "";
        return url;
    }

    protected String getMoveUri(int page_no) {
        String moveUri = "";
        moveUri = getMovePage() + "?page_no=" + page_no + ((getCommonPara() != null && !"".equals(getCommonPara())) ? "&" + getCommonPara() : "");
        return moveUri;
    }

    public String getPageSetHtml() {
        StringBuilder sb = new StringBuilder();
        setCurrentBlock((getCurrentPage() - 1) / getPerBlock());
        sb.append("<div class=\"paginate\">");
        
        if (getCurrentBlock() > 0) {
            int prevBlock = getCurrentBlock() * getPerBlock();
            sb.append("<a href=\"").append(getMoveUri(prevBlock)).append("\" class=\"btn\" title=\"이전 "+getPerBlock()+" 페이지\">").append("이전</a>");
        } else {
        	//sb.append("<img src=\"").append("\"/>");
        }
        
        
        int startPage = getCurrentBlock() * getPerBlock() + 1;
        int endPage = 0;
        
        if (getTotalPage() < (getCurrentBlock() + 1) * getPerBlock()) {
            endPage = getTotalPage();
        } else {
            endPage = (getCurrentBlock() + 1) * getPerBlock();
        }
        
        sb.append(getOnePageHtml(1 + getCurrentBlock() * getPerBlock() ));
        
        for (int i = startPage + 1; i <= endPage; ++i) {
            sb.append(getOnePageHtml(i));
        }
  
        if (getTotalBlock() - 1 > getCurrentBlock()) {
            int nextBlock = (getCurrentBlock() + 1) * getPerBlock() + 1;
            sb.append("<a href=\"").append(getMoveUri(nextBlock)).append("\" class=\"btn\" title=\"다음 "+getPerBlock()+" 페이지\">").append("다음</a>");
        } else {
            //sb.append("<img src=\"").append("\"/>");
        }
        
        sb.append("</div>");
        
        return sb.toString();
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPerPage() {
        return this.perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPerBlock() {
        return this.perBlock;
    }

    public void setPerBlock(int perBlock) {
        this.perBlock = perBlock;
    }

    public int getCurrentBlock() {
        return this.currentBlock;
    }

    public void setCurrentBlock(int currentBlock) {
        this.currentBlock = currentBlock;
    }

    public String getMovePage() {
        return this.movePage;
    }

    public void setMovePage(String movePage) {
        this.movePage = movePage;
    }

    public String getCommonPara() {
        return this.commonPara;
    }

    public void setCommonPara(String commonPara) {
        this.commonPara = commonPara;
    }

    public String getSearch_field() {
        return this.search_field;
    }

    public void setSearch_field(String search_field) {
        this.search_field = search_field;
    }

    public String getSearch_keyword() {
        return this.search_keyword;
    }

    public void setSearch_keyword(String search_keyword) {
        this.search_keyword = search_keyword;
    }
}