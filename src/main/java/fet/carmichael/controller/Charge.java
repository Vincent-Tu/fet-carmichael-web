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
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import fet.carmichael.dao.InternalDBStore;

public class Charge {
	public void charge(final Map<String, Object> params, final InternalDBStore internalDBStore,
			final String correlationId, final String api_radio) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		final String requestId = (String) params.get("requestId");
		final String account = (String) params.get("account");
		final String purchaseAmount = (String) params.get("purchaseAmount");
		final String purchaseCurrency = (String) params.get("purchaseCurrency");
		final String productDescription = (String) params.get("productDescription");
		final String clientTransactionId = (String) params.get("clientTransactionId");
		final String orderNo = (String) params.get("orderNo");
		String issuerPaymentId = null;
		Date chargeDate = null;
		
		final Map<String, String> map = new HashMap<String, String>();
		final Properties properties = new Properties();
		try {
			chargeDate = StringUtils.isNotEmpty((String) params.get("chargeDate"))
					? simpleDateFormat.parse((String) params.get("chargeDate")) : new Date();
//			String formattedDate = simpleDateFormat.format(chargeDate);
			simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			String strdate = simpleDateFormat.format(chargeDate);
			String request = "<ChargeRequest><requestId>" + requestId + "</requestId><clientTransactionId>"
					+ clientTransactionId + "</clientTransactionId><account refType='ACR'>" + account
					+ "</account><purchaseAmount><amount>" + purchaseAmount + "</amount><currency>" + purchaseCurrency
					+ "</currency></purchaseAmount><productDescription>" + productDescription
					+ "</productDescription><orderNo>" + orderNo + "</orderNo><chargeDate>"
					+ strdate + "</chargeDate></ChargeRequest>";
			
			properties.load(getClass().getResourceAsStream("/application.properties"));
			String clientId = properties.getProperty("clientId");
			
			String url = "charge?client_id=" + clientId;

			HttpEntity requestEntity = new ByteArrayEntity(request.getBytes("UTF-8"));
			
			new HttpClientUtils().sslWithPost(url, requestEntity, map, api_radio);
			
			String responseEntity = map.get("entityString");
			
			//for test result
//			responseEntity ="<ChargeResponse><issuerPaymentId>114417796190018</issuerPaymentId><result status='OK'><reasonCode>0</reasonCode><message>Success</message></result></ChargeResponse>";
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
				if("0".equals(result)) {
					if(doc.getElementsByTagName("issuerPaymentId").getLength() != 0){
						NodeList issuerPaymentIdList = doc.getElementsByTagName("issuerPaymentId");
						issuerPaymentId = issuerPaymentIdList.item(0).getTextContent();	
					}
				}
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
			System.out.println("#########issuerPaymentId: " + issuerPaymentId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		internalDBStore.saveTestCaseForCharge(correlationId, api_radio, requestId, clientTransactionId, account,
				purchaseAmount, purchaseCurrency, productDescription, orderNo, issuerPaymentId, chargeDate);
	}
}
