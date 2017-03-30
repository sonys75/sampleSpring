package resources.com.security.encoder;

import resources.com.util.PasswordEncoderUtil;
import resources.com.util.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class PasswordEncoder implements org.springframework.security.authentication.encoding.PasswordEncoder{
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();
	
	public String encodePassword(String rawPass, Object salt) throws DataAccessException {
		String encryptedPassword = null;
		try {
			PasswordEncoderUtil enc = new PasswordEncoderUtil();
			encryptedPassword = enc.encryptPassword(getSaltedString(rawPass, salt)).trim();
			System.out.println("encodePassword encodePassword : "+ encryptedPassword );
			/*
			System.out.println("encodePassword|salt:"+salt+"(length:"+salt.toString().length()+")");
			System.out.println("encodePassword|rawPass:"+rawPass+"(length:"+rawPass.length()+")");
			System.out.println("encodePassword|encryptedPassword:"+encryptedPassword+"(length:"+encryptedPassword.trim().length()+")");
			*/
		} catch (Exception e) {
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ this.getClass().getName() +" encodePassword");
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			
			return null;
		}
		return encryptedPassword;
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt) throws DataAccessException {
		
		String encryptedPassword = null;
		try {
			PasswordEncoderUtil enc = new PasswordEncoderUtil();
			encryptedPassword = enc.encryptPassword(getSaltedString(rawPass, salt)).trim(); 
			System.out.println("isPasswordValid encodePassword : "+ encryptedPassword  );
 
			//System.out.println("getPackage : "+ this.getClass().getPackage().getName() );
			//System.out.println("getClass : "+ this.getClass().getName() );
			//System.out.println("isPasswordValid encodePassword : "+ encryptedPassword + " :: " + this.getClass().getPackage().getName() + " :: " + this.getClass().getName());
			//q3wU81QEMj9IhdodzOg3jbRd1TEf4ZAy1zMczSXXI7c=
			
			/*
			System.out.println("isPasswordValid|encPass:"+encPass+"(length:"+encPass.length()+")");
			System.out.println("isPasswordValid|salt:"+salt+"(length:"+salt.toString().length()+")");
			System.out.println("isPasswordValid|rawPass:"+rawPass+"(length:"+rawPass.length()+")");
			System.out.println("isPasswordValid|encryptedPassword:"+encryptedPassword+"(length:"+encryptedPassword.trim().length()+")");
			*/
		} catch (Exception e) {
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ this.getClass().getName() +" isPasswordValid");
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			return false;
		}
		
		return (encryptedPassword.equals(encPass));
	}
	private String getSaltedString(String passwd, Object salt) {
		String rtnString = "{" + StringUtil.getNull(salt).toString().trim().hashCode() + "}" + passwd.trim();
		return rtnString;
	}
	
 }

