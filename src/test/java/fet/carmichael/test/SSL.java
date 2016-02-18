package fet.carmichael.test;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class SSL {

	public static void main(String[] args) {
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			InputStream keyStoreInputStream = SSL.class.getResourceAsStream("client.p12");
			InputStream trustStoreInputStream = SSL.class.getResourceAsStream("trust.keystore");
//			InputStream keyStoreInputStream = new FileInputStream("C:/Users/vincent/Desktop/dev_cer/client.p12");
			keyStore.load(keyStoreInputStream, "123456".toCharArray());
//			InputStream trustStoreInputStream = new FileInputStream("C:/Users/vincent/Desktop/dev_cer/trust.keystore");
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(trustStoreInputStream, "123456".toCharArray());
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy())
					.loadKeyMaterial(keyStore, "123456".toCharArray())
					.build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

			HttpPost httpPost = new HttpPost("https://10.77.8.19/dsp/d/boku/v1/oauth/authorize");

			String clientId = "1a60da28-ec3a-457e-8108-5cdb45cbb82b";
			String clientSecret = "wD6aA4kN1rN5mS5sX7aC4qB5sD8qS2hS3jY5pJ6tS3sG8mE4rT";
			String cred = clientId + ":" + clientSecret;
			String encodedValue = new String(Base64.encodeBase64(cred.getBytes()));
			httpPost.setHeader("Authorization", "Basic " + encodedValue);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("scope", "/boku/v1"));
			list.add(new BasicNameValuePair("grant_type", "client_credentials"));

			httpPost.setEntity(new UrlEncodedFormEntity(list));
			System.out.println("executing request: " + httpPost.getRequestLine());
			System.out.println("post request entity: " + EntityUtils.toString(httpPost.getEntity()));

			CloseableHttpResponse getTokenResponse = httpClient.execute(httpPost);
			
			String entity = EntityUtils.toString(getTokenResponse.getEntity());
			
			JSONObject jasonObject = new JSONObject(entity);
			String accessToken = jasonObject.get("access_token").toString();
			String tokenType = "Bearer ";
			String authorizationCode = tokenType + accessToken;

			System.out.println("authorizationCode:" + authorizationCode);
			
			System.out.println("----------------------------------------");
			System.out.println(getTokenResponse.getStatusLine());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
