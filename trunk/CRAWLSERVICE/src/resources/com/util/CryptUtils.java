package resources.com.util; 

import java.security.MessageDigest;

import resources.com.crypto.AESCipher;
import resources.com.crypto.Base64;
import resources.com.service.ReadProperties;



public class CryptUtils {
	
	//암복호화 seed키
	public static String cryptKey = ReadProperties.getProperty("crypt.AES.key");
		
	public static String enCrypt( final String strValue )  throws Exception {
		if(  strValue ==  null || "".equals( strValue.trim()) ) return null; 
		Base64 b64 = new Base64();
		MessageDigest messageDigest = MessageDigest.getInstance(ReadProperties.getProperty("crypt.algorithm"));
		messageDigest.update(strValue.getBytes());  
		return  new String(b64.encode(messageDigest.digest()));   
	}
	
	/**
	 * String을 MD5로 변환하는 함수
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String encryptMD5(final String str) throws Exception {
		//시큐어 점검 결과에 따른 조치
		if(str==null) return "";
		
		/* generate key - Start */
		StringBuffer sb = new StringBuffer();
		String apiKey = "";
		
        try{
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            
            byte[] bytData = str.getBytes();	
            md.update(bytData);
            byte[] digest = md.digest();
            sb.setLength(0);
            for( int i = 0; i < digest.length; i++ ) { 
                sb.append(Integer.toString((digest[i] & 0xf0) >> 4, 16)); 
                sb.append(Integer.toString(digest[i] & 0x0f, 16));
            }
            apiKey = sb.toString();
        }catch(Exception e){
            //e.printStackTrace();
            sb = null;
            apiKey = "";
        }
        sb = null;

		return apiKey;

		/* generate key - End */ 
 	}
	
	/**
	 * Seed 값을 가지고 MD5로 변환하는 함수
	 * Seed 값이 없거나 null일 경우 random 숫자를 호출한다.
	 * 
	 * @param Seed
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String encryptMD5(String seed,String str) throws Exception {
		//시큐어 점검 결과에 따른 조치
		if(str==null) 
			str = "";
		
		/* generate key - Start */
		StringBuffer sb = new StringBuffer();
		String apiKey = "";
		
        try{
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            
            if(seed==null || "".equals(seed)){
            	seed = Integer.toString(NumberUtil.getRandomNum(100000, 999999));
            }
            str=seed+str;
            
            byte[] bytData = str.getBytes();	
            md.update(bytData);
            byte[] digest = md.digest();
            sb.setLength(0);
            for( int i = 0; i < digest.length; i++ ) { 
                sb.append(Integer.toString((digest[i] & 0xf0) >> 4, 16)); 
                sb.append(Integer.toString(digest[i] & 0x0f, 16));
            }
            apiKey = sb.toString();
        }catch(Exception e){
            //e.printStackTrace();
            sb = null;
            apiKey = "";
        }
        sb = null;

		return apiKey;
 	}
	
	/**
	 * 작성자: sonys75
	 * 내용: AES 암복호화-암호화
	 * @param str
	 * @return String
	 */
	public static String strToEncode(String str) throws Exception {
		if(str==null || "".equals(str) || "null".equals(str)){
			return "";
		}
		// Encrypt
		String encodeText = AESCipher.encodeAES(str, cryptKey);		
		return encodeText;
    }
	
	/**
	 * 작성자: sonys75
	 * 내용: AES 암복호화-복호화
	 * @param encode
	 * @return String
	 */
	public static String decodeToStr(String encode) throws Exception {
		if(encode==null || "".equals(encode) || "null".equals(encode)){
			return "";
		}
		
		//System.out.println("encode"+encode);
		//System.out.println("cryptKey"+cryptKey);
		// Decrypt
		String decodeText = AESCipher.decodeAES(encode, cryptKey);
		return decodeText;
    }
}