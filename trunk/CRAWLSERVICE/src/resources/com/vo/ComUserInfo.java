package resources.com.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import crawl.vo.VoCommon;

/**
 * 사용자 class
 */
public class ComUserInfo extends VoCommon implements UserDetails {

	private static final long serialVersionUID = 4682124636839280877L;
	private String user_id			= "";	//사용자 아이디
	private String corp_id			= "";	//회사 아이디
	private String corp_nm			= "";	//회사명
	private String user_nm			= "";	//사용자 이름
	private String user_pw			= "";	//사용자 비밀번호
	private String user_email		= "";	//사용자 이메일
	private String user_tel			= "";	//사용자 일반전화번호
	private String user_hp			= "";	//사용자 휴대전화번호
	private String user_auth		= "";	//사용자 권한
	private String user_authorities	= "";	//사용자 권한
	private String use_yn			= "";	//아이디 사용여부
	private String access_fail_cnt	= "";	//로그인 실패 카운트
	private String user_ip			= "";	//사용자아이피
	private String corp_del			= "";	//회사정보 삭제 플레그

	public String getUser_authorities() {
		return user_authorities;
	}
	public void setUser_authorities(String user_authorities) {
		this.user_authorities = user_authorities;
	}
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getUser_hp() {
		return user_hp;
	}
	public void setUser_hp(String user_hp) {
		this.user_hp = user_hp;
	}
	public String getUser_auth() {
		return user_auth;
	}
	public void setUser_auth(String user_auth) {
		this.user_auth = user_auth;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getAccess_fail_cnt() {
		return access_fail_cnt;
	}
	public void setAccess_fail_cnt(String access_fail_cnt) {
		this.access_fail_cnt = access_fail_cnt;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
	
	private String re_user_pw			= "";	//변경 패스워드
	private String re_user_pw_confirm	= "";	//변경 패스워드 확인
	private String email_id		= "";	//이메일 아이디
	private String email_host	= "";	//이메일 호스트

	public String getRe_user_pw() {
		return re_user_pw;
	}
	public void setRe_user_pw(String re_user_pw) {
		this.re_user_pw = re_user_pw;
	}
	public String getRe_user_pw_confirm() {
		return re_user_pw_confirm;
	}
	public void setRe_user_pw_confirm(String re_user_pw_confirm) {
		this.re_user_pw_confirm = re_user_pw_confirm;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getEmail_host() {
		return email_host;
	}
	public void setEmail_host(String email_host) {
		this.email_host = email_host;
	}
	public String getCorp_del() {
		return corp_del;
	}
	public void setCorp_del(String corp_del) {
		this.corp_del = corp_del;
	}

    private Set<GrantedAuthority> authorities;    // 계정이 가지고 있는 권한 목록
    
    public ComUserInfo(){
        this.user_id = "";
        this.user_pw = "";
        this.user_nm = "";
    }
    
    public ComUserInfo(String user_id, String user_pw, String user_nm, Collection<? extends GrantedAuthority> authorities){
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_nm = user_nm;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    /**
     * Spring Security 내부 함수 Over ride
     * 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
     
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }
     
    /**
     * Spring Security 내부 함수 Over ride
     * 패스워드 
     */
    @Override
    public String getPassword() {
        return user_pw;
    }
 
    /**
     * Spring Security 내부 함수 Over ride
     * 아이디
     */
    @Override
    public String getUsername() {
        return getUser_id();
    }
 
    /**
     * Spring Security 내부 함수 Over ride
     * 계정 만료 상태 여부(false : 계정 만료)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    /**
     * Spring Security 내부 함수 Over ride
     * 계정 잠김 여부(false : 계정 잠김)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    /**
     * Spring Security 내부 함수 Over ride
     * 패스워드 만료 여부(false : 패스워드 만료)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    /**
     * Spring Security 내부 함수 Over ride
     * 계정 사용가능 여부(true : 사용가능)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities =
            new TreeSet<GrantedAuthority>(new AuthorityComparator());
 
        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
 
        return sortedAuthorities;
    }
     
    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
 
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }
 
            if (g1.getAuthority() == null) {
                return 1;
            }
 
            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
}	
