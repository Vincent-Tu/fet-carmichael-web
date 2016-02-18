package fet.carmichael.controller;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import fet.carmichael.dao.InternalDBStore;

public class Reverse {
	public void reverse(final Map<String, Object> params, final InternalDBStore internalDBStore,
			final String correlationId, final String api_radio) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		final String chargeRequestId = (String) params.get("chargeRequestId");
		Date reverseDate = null;
		String issuerReverseId = null;
		
		final Map<String, String> map = new HashMap<String, String>();
		final Properties properties = new Properties();
		
		try {
			reverseDate = StringUtils.isNotEmpty((String) params.get("reverseDate"))
					? simpleDateFormat.parse((String) params.get("reverseDate")) : new Date();
//			String formattedDate = simpleDateFormat.format(reverseDate);
			simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			String strdate = simpleDateFormat.format(reverseDate);
			String request = "<ReverseRequest><chargeRequestId>" + chargeRequestId + "</chargeRequestId><reverseDate>"
					+ strdate + "</reverseDate></ReverseRequest>";

			properties.load(getClass().getResourceAsStream("/application.properties"));
			String clientId = properties.getProperty("clientId");
			String url = "reverse?client_id=" + clientId;
			
			HttpEntity requestEntity = new ByteArrayEntity(request.getBytes("UTF-8"));
			
			new HttpClientUtils().sslWithPost(url, requestEntity, map, api_radio);
			
			String responseEntity = map.get("entityString");
			
			//for test result
//			responseEntity = "<ReverseResponse><issuerReverseId>114417796200047</issuerReverseId><result status='OK'><reasonCode>0</reasonCode><message>Success</message></result><paymentResponse><issuerPaymentId>114417796190018</issuerPaymentId><result status='OK'><reasonCode>0</reasonCode><message>Success</message></result></paymentResponse></ReverseResponse>";

			String userMessage = null;
			String result = null;
			String status = null;
			if((map.get("status").equals("200"))){
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
				if("0".equals(result)) {
					if(doc.getElementsByTagName("issuerReverseId").getLength() != 0){
						NodeList issuerReverseIdList = doc.getElementsByTagName("issuerReverseId");
						issuerReverseId = issuerReverseIdList.item(0).getTextContent();	
					}
				}
				internalDBStore.saveSoapRecord(correlationId, request, responseEntity, status,
						userMessage, api_radio);
			} else {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				InputSource inputSource = new InputSource(new StringReader(responseEntity));
				Document doc = builder.parse(inputSource);
				NodeList messageList = doc.getElementsByTagName("httpMessage");
				userMessage = messageList.item(0).getTextContent();
				NodeList reasonCodeList = doc.getElementsByTagName("httpCode");
				result = reasonCodeList.item(0).getTextContent();
				internalDBStore.saveSoapRecord(correlationId, request, responseEntity, result,
						userMessage, api_radio);
			}
			
			System.out.println("#########userMessage: " + userMessage);
			System.out.println("#########result: " + result);
			System.out.println("#########result: " + issuerReverseId);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		internalDBStore.saveTestCaseForReverse(correlationId, api_radio, chargeRequestId, issuerReverseId, reverseDate);
	}
}