package resources.com.security.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import resources.com.vo.ComUserInfo;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
 
public class CustomJdbcDaoImpl extends JdbcDaoImpl {
 
    @Override
    public UserDetails loadUserByUsername(String user_id)  throws UsernameNotFoundException {

    	List<UserDetails> users = loadUsersByUsername(user_id);
 
        if (users.size() == 0) {
            logger.debug("Query returned no results for user '" + user_id + "'");
            System.out.println("Query returned no results for user '" + user_id + "'");
            UsernameNotFoundException ue = new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.notFound", new Object[]{user_id}, "Username {0} not found"));
            throw ue;
        }
 
        ComUserInfo user = (ComUserInfo)users.get(0); // contains no GrantedAuthority[]
 
        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
 
        if (getEnableAuthorities()) {
            dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
        }
 
        if (getEnableGroups()) {
            dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
        }
 
        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);
        user.setAuthorities(dbAuths);
 
        if (dbAuths.size() == 0) {
        	System.out.println("User '" + user_id + "' has no authorities and will be treated as 'not found'");
            logger.debug("User '" + user_id + "' has no authorities and will be treated as 'not found'");
 
            UsernameNotFoundException ue = new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.noAuthority", new Object[] {user_id}, "User {0} has no GrantedAuthority"));
            throw ue;
        }
 
        return user;
    }
 
    @Override
    protected List<UserDetails> loadUsersByUsername(String user_id) {

        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {user_id}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String user_id = rs.getString(1);
                String user_pw = rs.getString(2);
                String user_nm = rs.getString(3);
                return new ComUserInfo(user_id, user_pw, user_nm, AuthorityUtils.NO_AUTHORITIES);
            }
 
        });
    }
 
    @Override
    protected List<GrantedAuthority> loadUserAuthorities(String user_id) {

        return getJdbcTemplate().query(getAuthoritiesByUsernameQuery(), new String[] {user_id}, new RowMapper<GrantedAuthority>() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String roleName = getRolePrefix() + rs.getString(1);
 
                return new SimpleGrantedAuthority(roleName);
            }
        });
    }
 
    @Override
    protected List<GrantedAuthority> loadGroupAuthorities(String user_id) {

        return super.loadGroupAuthorities(user_id);
    }
}