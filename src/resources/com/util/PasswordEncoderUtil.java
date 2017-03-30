package resources.com.util;

import resources.com.service.ReadProperties;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.springframework.beans.factory.annotation.Required;

public class PasswordEncoderUtil {
	
	//단방향 암호화 키
	private String	algorithm	= ReadProperties.getProperty("crypt.algorithm");	// default
	private String	hashedPassword;

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getAlgorithm() {
		return this.algorithm;
	}

	@Required
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String encryptPassword(String plainPassword) {
		ConfigurablePasswordEncryptor encoder = new ConfigurablePasswordEncryptor();

		encoder.setAlgorithm(this.algorithm);
		encoder.setPlainDigest(true);

		return encoder.encryptPassword(plainPassword);
	}

	public boolean checkPassword(String plainPassword) {
		ConfigurablePasswordEncryptor encoder = new ConfigurablePasswordEncryptor();

		encoder.setAlgorithm(this.algorithm);
		encoder.setPlainDigest(true);

		return encoder.checkPassword(plainPassword, this.hashedPassword);
	}

	public boolean checkPassword(String plainPassword, String encryptedPassword) {
		ConfigurablePasswordEncryptor encoder = new ConfigurablePasswordEncryptor();

		encoder.setAlgorithm(this.algorithm);
		encoder.setPlainDigest(true);

		return encoder.checkPassword(plainPassword, encryptedPassword);
	}

	public static void main(String[] args) {
		
//		if (args.length < 2) {
//			System.out.println("Arguments missing!!!");
//			System.out.println();
//			System.out
//					.println("Usage: java ... egovframework.rte.fdl.cryptography.EgovPasswordEncoder 'algorithm' 'password'");
//			System.out
//					.println("\t- algorithm : Message Digest Algorithms (ex: MD5, SHA-1, SHA-256, ...)");
//			System.out.println();
//			System.out
//					.println("Ex: java ... egovframework.rte.fdl.cryptography.EgovPasswordEncoder SHA-256 egovframework");
//
//			return;
//		}
//
//		// egovframe (SHA-256) : gdyYs/IZqY86VcWhT8emCYfqY1ahw2vtLG+/FzNqtrQ=

		PasswordEncoderUtil encoder = new PasswordEncoderUtil();

//		encoder.setAlgorithm(args[0]);
		encoder.setAlgorithm(encoder.getAlgorithm());

//		System.out.println("Digested Password : " + encoder.encryptPassword(args[1]));
		for ( int i = 0; i < 10000; i++) {
			System.out.println("Digested Password : " + encoder.encryptPassword("" + i));
		}
		System.out.println("Digested Password : " + encoder.encryptPassword(""));
		
	}
}
