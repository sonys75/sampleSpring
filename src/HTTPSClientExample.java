
import java.net.URL;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author amusarra
 * 
 *         VM Arguments
 * 
 *         -Djavax.net.debug=ssl
 *         -Djavax.net.ssl.trustStore=/Users/amusarra/Documents
 *         /workspace-myBlog/
 *         http-ssl-client-example/src/main/resources/cacerts.jks
 *         -Djavax.net.ssl.trustStorePassword=changeit
 * 
 *         Documents di riferimento: JavaTM Secure Socket Extension (JSSE)
 *         Reference Guide
 *         http://docs.oracle.com/javase/6/docs/technotes/guides/
 *         security/jsse/JSSERefGuide.html JavaTM Secure Socket Extension (JSSE)
 *         Reference Guide
 *         http://docs.oracle.com/javase/7/docs/technotes/guides/
 *         security/jsse/JSSERefGuide.html Transport Layer Security (TLS)
 *         Renegotiation Issue Readme
 *         http://www.oracle.com/technetwork/java/javase
 *         /documentation/tlsreadme2-176330.html
 */
public class HTTPSClientExample {

	private static final Logger logger = LoggerFactory.getLogger(HTTPSClientExample.class);
	private static List<String> targetURL = new ArrayList<String>();

	static {
		// for localhost testing only
		javax.net.ssl.HttpsURLConnection
				.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

					public boolean verify(String hostname,
							javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals("localhost")) {
							return true;
						}
						return false;
					}
				});
	}

	/**
	 * 
	 */
	public HTTPSClientExample() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		targetURL.add("https://www.goodreads.com/book/show/18734728-enterprise-integration-with-wso2-esb?from_search=true");
		targetURL.add("https://www.liferay.com/it/products/liferay-portal/overview");
		targetURL.add("https://crm-shiruslabs.dontesta.it:8443/SugarEnt-Full-7.1.0/");

		for (String httpURL : targetURL) {
			HttpsURLConnection connection = null;
			try {
				// Create connection
				System.out.println("Try to connect to the URL " + httpURL );
				URL url = new URL(httpURL);
				connection = (HttpsURLConnection) url.openConnection();

				// Prepare a GET request Action
				connection.setRequestMethod("GET");
				connection.setRequestProperty("User-Agent","Antonio Musarra's Blog Java Client 1.0");
				connection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml");
				connection.setRequestProperty("Accept-Language", "it-IT,it");

				connection.setUseCaches(false);
				connection.setDoOutput(true);

				// Create a SSL SocketFactory
				SSLSocketFactory sslSocketFactory = getFactorySimple();
				connection.setSSLSocketFactory(sslSocketFactory);

				System.out.println("HTTP Response Code : "+connection.getResponseCode());
				System.out.println("HTTP Response Message : "+connection.getResponseMessage());
				System.out.println("HTTP Content Length : "+connection.getContentLength());
				System.out.println("HTTP Content Type : "+connection.getContentType());
				System.out.println("HTTP Cipher Suite : "+connection.getCipherSuite());

				Certificate[] serverCertificate = connection.getServerCertificates();

				for (Certificate certificate : serverCertificate) {
					System.out.println("Certificate Type : "+certificate.getType());

					if (certificate instanceof X509Certificate) {
						X509Certificate x509cert = (X509Certificate) certificate;

						// Get subject
						Principal principal = x509cert.getSubjectDN();
						System.out.println("Certificate Subject DN : "+principal.getName());

						// Get issuer
						principal = x509cert.getIssuerDN();
						System.out.println("Certificate IssuerDn : "+principal.getName());
					}
				}

				// Close Connection
				connection.disconnect();

			} catch (Exception e) {
				if (connection != null) {
					connection.disconnect();
				}
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private static SSLSocketFactory getFactorySimple() throws Exception {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, null, null);
		return context.getSocketFactory();
	}
}