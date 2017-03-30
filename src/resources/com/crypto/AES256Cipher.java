package resources.com.crypto;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES256Cipher {
	
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	public static String encodeAES(final String str, final String key)	throws Exception {
		//시큐어 점검 결과에 따른 조치
		if(str==null) return "";
    	if(key==null) return "";
    	
		byte[] textBytes = str.getBytes("UTF-8");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = null;
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
		byte[] encrypted = cipher.doFinal(textBytes);
		String enStr = new String(Base64.encodeBase64(encrypted));

		return enStr;
	}

	public static String decodeAES(final String str, final String key)	throws Exception {
		//시큐어 점검 결과에 따른 조치
		if(str==null) return "";
    	if(key==null) return "";
    	
		byte[] textBytes = Base64.decodeBase64(str.getBytes());
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
		return new String(cipher.doFinal(textBytes), "UTF-8");
	}
}