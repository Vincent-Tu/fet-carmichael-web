package fet.carmichael.controller;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpClientUtils {

	final String TRUST_STORE_TYPE = "JKS";
	final String KEY_STORE_TYPE = "PKCS12";
	final String SCHEME_HTTPS = "HTTPS";
	final int HTTPS_PORT = 443;
	
	public Map<String, String> sslWithPost(final String urlpath, final HttpEntity requestEntity,
			final Map<String, String> map, String api_radio) throws Exception {

		final Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/application.properties"));
		String url = properties.getProperty("carmichaelpost.url")+urlpath;
		
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			InputStream keyStoreInputStream = HttpClientUtils.class.getResourceAsStream("client.p12");
			InputStream trustStoreInputStream = HttpClientUtils.class.getResourceAsStream("trust.keystore");
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
			
			String scope = properties.getProperty("scope");
			String authorizeUrl = properties.getProperty("authorizeUrl");
			HttpPost httpPost = new HttpPost(authorizeUrl);
			String clientId = properties.getProperty("clientId");
			String clientSecret = properties.getProperty("clientSecret");
			String cred = clientId + ":" + clientSecret;
			String encodedValue = new String(Base64.encodeBase64(cred.getBytes()));
			httpPost.setHeader("Authorization", "Basic " + encodedValue);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("scope", scope));
			list.add(new BasicNameValuePair("grant_type", "client_credentials"));

			httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
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

			HttpPost post = new HttpPost(url);
			post.setHeader("Authorization", authorizationCode);
			if("sendSMS".equals(api_radio)){
				post.setHeader("Content-Type", "application/json");
			}else {
				post.setHeader("Content-Type", "application/xml");
			}
			post.setEntity(requestEntity);
			System.out.println("executing request: " + post.getRequestLine());
			System.out.println("post request entity: " + EntityUtils.toString(post.getEntity()));

			HttpResponse response = httpClient.execute(post);
			
//			HttpEntity entity = response.getEntity();
			String statusLine = response.getStatusLine().toString();
			String[] statusSplit = statusLine.split(" ");
			System.out.println("----------------------------------------");
			System.out.println(statusSplit[1]);
			
			String entityString = EntityUtils.toString(response.getEntity());
			System.out.println("response entityString: " + entityString);
			map.put("status", statusSplit[1]);
			map.put("entityString", entityString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public Map<String, String> sslWithGet(final String urlpath, final Map<String, String> map)
			throws Exception {

		final Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/application.properties"));
		String url = properties.getProperty("carmichaelget.url")+urlpath;
		
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			InputStream keyStoreInputStream = HttpClientUtils.class.getResourceAsStream("client.p12");
			InputStream trustStoreInputStream = HttpClientUtils.class.getResourceAsStream("trust.keystore");
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

			String scope = properties.getProperty("scope");
			String authorizeUrl = properties.getProperty("authorizeUrl");
			HttpPost httpPost = new HttpPost(authorizeUrl);
			String clientId = properties.getProperty("clientId");
			String clientSecret = properties.getProperty("clientSecret");
			String cred = clientId + ":" + clientSecret;
			String encodedValue = new String(Base64.encodeBase64(cred.getBytes()));
			httpPost.setHeader("Authorization", "Basic " + encodedValue);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("scope", scope));
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
			
			HttpGet get = new HttpGet(url);
			get.setHeader("Authorization", authorizationCode);
			System.out.println("executing request: " + get.getRequestLine());
			HttpResponse response = httpClient.execute(get);
//			HttpEntity entity = response.getEntity();
			String statusLine = response.getStatusLine().toString();
			String[] statusSplit = statusLine.split(" ");
			System.out.println("----------------------------------------");
			System.out.println(statusSplit[1]);
			
			String entityString = EntityUtils.toString(response.getEntity());
			System.out.println("response entityString: " + entityString);
			map.put("entityString", entityString);
			map.put("status", statusSplit[1]);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
}
