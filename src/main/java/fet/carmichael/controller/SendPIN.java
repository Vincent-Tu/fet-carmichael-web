package fet.carmichael.controller;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import fet.carmichael.dao.InternalDBStore;

public class SendPIN {
	public void sendPIN(final Map<String, Object> params, final InternalDBStore internalDBStore,
			final String correlationId, final String api_radio) {
		final String originalSenderAddress = (String) params.get("senderAddress");
		final String senderAddress = "tel:886" + originalSenderAddress.substring(1);
		final String address = "tel:886" + ((String) params.get("address")).substring(1);
		final String message = (String) params.get("message");
		final String clientCorrelator = (String) params.get("clientCorrelator");
		final String senderName = "01020250600000000000";
		
		final Map<String, String> map = new HashMap<String, String>();
		final Properties properties = new Properties();
		String result = null;
		String resourceURL = null;
		String userMessage = null;
		
		try {
			properties.load(getClass().getResourceAsStream("/application.properties"));
			String clientId = properties.getProperty("clientId");
			String senderAddressForUrl = StringUtils.replace(senderAddress, ":", "%3A");
			String url = "smsmessaging/v1/outbound/" + senderAddressForUrl + "/requests?client_id=" + clientId;
//			String request = "{\"outboundSMSMessageRequest\" : {\"address\" : [\"tel:+19585550101\", \"tel:+19585550104\"],\"outboundSMSTextMessage\" : {\"message\" : \"Carmichael for test send sms\"},\"senderAddress\" : \"tel:+19585550151\",} }";
			String request = "{\"outboundSMSMessageRequest\" : {\"address\" : \"" + address+"\",\"senderAddress\" : \"" + senderAddress + "\",\"senderName\" : \"" + senderName + "\",\"outboundSMSTextMessage\" : {\"message\" : \"" + message + "\"},\"clientCorrelator\" : \"" + clientCorrelator + "\"}}";
			
			JSONObject sendRequest = new JSONObject(request);
//			sendRequest.put("senderAddress", senderAddress);
			HttpEntity requestEntity = new StringEntity(sendRequest.toString(), "utf-8");
			
			new HttpClientUtils().sslWithPost(url, requestEntity, map, api_radio);
			
			String responseEntity = map.get("entityString");
			
			//for test result
//			responseEntity = "{\"resourceReference\": {\"resourceURL\": \"http://example.com/oneapi/1/smsmessaging/outbound/tel%3A%2B5550100/requests/abc123\"}}";
			
			if((map.get("status").equals("200"))){
				JSONObject response = new JSONObject(responseEntity);
				JSONObject resourceReference = response.getJSONObject("resourceReference");
				resourceURL = resourceReference.get("resourceURL").toString();
				if (StringUtils.isNotEmpty(resourceURL)) {
					result = "0";
					userMessage = "Success";
				} else {
					userMessage = "Failure";
				}
			} else {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				InputSource inputSource = new InputSource(new StringReader(responseEntity));
				Document doc = builder.parse(inputSource);
				NodeList messageList = doc.getElementsByTagName("httpMessage");
				userMessage = messageList.item(0).getTextContent();
				NodeList reasonCodeList = doc.getElementsByTagName("httpCode");
				result = reasonCodeList.item(0).getTextContent();
			}
			
			internalDBStore.saveSoapRecord(correlationId, request, responseEntity, result, userMessage, api_radio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		internalDBStore.saveTestCaseForSendPIN(correlationId, api_radio, originalSenderAddress, message);
	}

}
