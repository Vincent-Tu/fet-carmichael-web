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

public class Refund {
	public void refund(final Map<String, Object> params, final InternalDBStore internalDBStore,
			final String correlationId, final String api_radio) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		final String requestId = (String) params.get("requestId1");
		final String clientRefundId = (String) params.get("clientRefundId");
		final String issuerPaymentId = (String) params.get("issuerPaymentId");
		final String refundAmount = (String) params.get("refundAmount");
		final String refundCurrency = (String) params.get("refundCurrency");
		final String refundReason = (String) params.get("refundReason");
		
		final Map<String, String> map = new HashMap<String, String>();
		final Properties properties = new Properties();
		
		Date refundDate = null;
		String issuerRefundId = null;
		
		try {
			refundDate = StringUtils.isNotEmpty((String) params.get("refundDate"))
					? simpleDateFormat.parse((String) params.get("refundDate")) : new Date();
//			String formattedDate = simpleDateFormat.format(refundDate);
			simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			String strdate = simpleDateFormat.format(refundDate);
			String request = "<RefundRequest><requestId>" + requestId + "</requestId><clientRefundId>" + clientRefundId
					+ "</clientRefundId><issuerPaymentId>" + issuerPaymentId
					+ "</issuerPaymentId><refundAmount><amount>" + refundAmount + "</amount><currency>" + refundCurrency
					+ "</currency></refundAmount><refundReason>" + refundReason + "</refundReason><refundDate>"
					+ strdate + "</refundDate></RefundRequest>";
			properties.load(getClass().getResourceAsStream("/application.properties"));
			String clientId = properties.getProperty("clientId");
			String url = "refund?client_id=" + clientId;
			
			HttpEntity requestEntity = new ByteArrayEntity(request.getBytes("UTF-8"));
			
			new HttpClientUtils().sslWithPost(url, requestEntity, map, api_radio);
			
			String responseEntity = map.get("entityString");
			
			//for test results
//			responseEntity = "<RefundResponse><issuerRefundId>114417796200047</issuerRefundId><result status='OK'><reasonCode>0</reasonCode><message>Success</message></result></RefundResponse>";

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
					if(doc.getElementsByTagName("issuerRefundId").getLength() != 0){
						NodeList issuerRefundIdList = doc.getElementsByTagName("issuerRefundId");
						issuerRefundId = issuerRefundIdList.item(0).getTextContent();
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
			System.out.println("#########issuerRefundId: " + issuerRefundId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		internalDBStore.saveTestCaseForRefund(correlationId, api_radio, requestId, clientRefundId, issuerPaymentId,
				issuerRefundId, refundAmount, refundCurrency, refundReason, refundDate);
	}
}
