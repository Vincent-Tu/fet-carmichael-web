package fet.carmichael.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;

import fet.carmichael.dao.InternalDBStore;

public class GetACR {
	public void getACR(final Map<String, Object> params, final InternalDBStore internalDBStore,
			final String correlationId, final String api_radio) {

		final String msisdn = (String) params.get("msisdn1");
		final String networkStatus = (String) params.get("networkStatus");
		final Map<String, String> map = new HashMap<String, String>();
		final Properties properties = new Properties();
		String uid = null;
		try {
			properties.load(getClass().getResourceAsStream("/application.properties"));
			String clientId = properties.getProperty("clientId");
			String url = "otp/" +msisdn + "/getACR?client_id=" + clientId + "&networkStatus=" + networkStatus;
		
			new HttpClientUtils().sslWithGet(url, map);
			
			String responseEntity = map.get("entityString");
			
			//for test result
//			responseEntity = "{\"uid\": \"15151515\",\"code\": \"0\",\"message\": \"Success\"}";
			
			String userMessage = null;
			String result = null;
			if("200".equals(map.get("status"))){
				JSONObject jasonObject = new JSONObject(responseEntity);
				result = jasonObject.get("code").toString();
				if("0".equals(result)){
					uid = jasonObject.get("uid").toString();
				}
				userMessage = jasonObject.get("message").toString();
			} else {
				JSONObject jasonObject = new JSONObject(responseEntity);
				userMessage = jasonObject.get("httpMessage").toString();
				result = jasonObject.get("httpCode").toString();
			}
			System.out.println("#########userMessage: " + userMessage);
			System.out.println("#########result: " + result);
			System.out.println("#########uid: " + uid);

			internalDBStore.saveSoapRecord(correlationId, null, responseEntity, result, userMessage, api_radio);
		} catch (Exception e) {
			e.printStackTrace();
		}

		internalDBStore.saveTestCaseForGetACRAndGetMSISDN(correlationId, api_radio, msisdn, uid, networkStatus);
	}
}
