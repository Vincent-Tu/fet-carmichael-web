package fet.carmichael.controller;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import fet.carmichael.dao.InternalDBStore;

public class AccountProfile {
	public void accountProfile(final Map<String, Object> params, final InternalDBStore internalDBStore,
			final String correlationId, final String api_radio) {

		final Map<String, String> map = new HashMap<String, String>();
		final String account = (String) params.get("account2");
		final Properties properties = new Properties();
		
		try {
			String request = "<AccountProfileRequest><account refType='ACR'>" + account
					+ "</account></AccountProfileRequest>";
			
			properties.load(getClass().getResourceAsStream("/application.properties"));
			String clientId = properties.getProperty("clientId");
			String url = "accountProfile?client_id=" + clientId;
			HttpEntity requestEntity = new ByteArrayEntity(request.getBytes("UTF-8"));
			
			new HttpClientUtils().sslWithPost(url, requestEntity, map, api_radio);
			
			String responseEntity = map.get("entityString");
			
			//for test result
//			responseEntity = "<AccountProfileResponse><attribute key='eligibility'>true</attribute><result status='OK'><reasonCode>0</reasonCode><message>Success</message></result></AccountProfileResponse>";
			String userMessage = null;
			String result = null;
			String status = null;
			
			if("200".equals(map.get("status"))){
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				InputSource inputSource = new InputSource(new StringReader(responseEntity));
				Document doc = builder.parse(inputSource);
				NodeList messageList = doc.getElementsByTagName("message");
				userMessage = messageList.item(0).getTextContent();
				NodeList resultStatus = doc.getElementsByTagName("result");
				for(int temp = 0; temp < resultStatus.getLength(); temp++) {
					Node node = resultStatus.item(temp);
					Element statusElement = (Element) node;
					status = statusElement.getAttribute("status");
				}
				NodeList reasonCodeList = doc.getElementsByTagName("reasonCode");
				result = reasonCodeList.item(0).getTextContent();
				internalDBStore.saveSoapRecord(correlationId, request, responseEntity, status,
						userMessage, api_radio);
			} else {
				JSONObject jasonObject = new JSONObject(responseEntity);
				userMessage = jasonObject.get("httpMessage").toString();
				result = jasonObject.get("httpCode").toString();
				internalDBStore.saveSoapRecord(correlationId, request, responseEntity, result,
						userMessage, api_radio);
			}
			System.out.println("#########userMessage: " + userMessage);
			System.out.println("#########result: " + result);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		internalDBStore.saveTestCaseForAccountProfile(correlationId, api_radio, account);
	}
}
