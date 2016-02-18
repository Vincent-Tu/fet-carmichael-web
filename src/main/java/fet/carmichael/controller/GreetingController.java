package fet.carmichael.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fet.carmichael.dao.InternalDBStore;
import fet.carmichael.dao.SoapRecordEntity;
import fet.carmichael.dao.TestCaseHistoryEntity;
import fet.carmichael.services.PaginationService;


/**
 *
 * @author
 *
 */
@Controller
public class GreetingController {

	private static final Logger log = LoggerFactory.getLogger(GreetingController.class);
	final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// private ThreadPoolTaskScheduler scheduler;

	@Autowired
	InternalDBStore internalDBStore;

	@Autowired
	PaginationService paginationService;

	@RequestMapping(value = { "/home", "/history" })
	public String index() {
		final String defaultPage = "1";
		return "redirect:/carmichael/history/testResultList/0/0/0/0/0/" + defaultPage;
	}

	@RequestMapping(value = {
			"/history/testResultList/{msisdn}/{requestid}/{uid}/{creationTimeFrom}/{creationTimeTo}/{page}" })
	public String getTestResultListSearch(@PathVariable String msisdn, @PathVariable String requestid, @PathVariable String uid,  
			@PathVariable String creationTimeFrom,
			@PathVariable String creationTimeTo, @PathVariable String page, @RequestParam Map<String, Object> params,
			Model model) {
		log.info("getHistorySearchList params:{}", params);

		final int clickPage = Integer.parseInt(page);

		final int rowsShowCount = 20;
		final int pageIndexCount = 10;

		msisdn = "0".equals(msisdn) ? null : msisdn;
		requestid = "0".equals(requestid) ? null : requestid;
		uid = "0".equals(uid) ? null : uid;
		creationTimeFrom = "0".equals(creationTimeFrom) ? null : creationTimeFrom;
		creationTimeTo = "0".equals(creationTimeTo) ? null : creationTimeTo;

		final List<TestCaseHistoryEntity> historyList = internalDBStore.queryHistoryList(msisdn, requestid, uid, creationTimeFrom, creationTimeTo, clickPage, rowsShowCount);
		log.info("result:{}", historyList);
		log.info("result size:{}", historyList.size());
//		for (int i = 0; i < historyList.size(); i++) {
//			final TestCaseHistoryEntity row = historyList.get(i);
//			log.info("row:{}", ToStringBuilder.reflectionToString(row));
//		}
		model.addAttribute("historyList", historyList);
		model.addAllAttributes(paginationService.pagination(clickPage,
				historyList.isEmpty() ? 0 : historyList.get(0).getTotalCount(), rowsShowCount, pageIndexCount));
		model.addAttribute("msisdn", null == msisdn ? "0" : msisdn);
		model.addAttribute("requestid", null == requestid ? "0" : requestid);
		model.addAttribute("uid", null == uid ? "0" : uid);
		model.addAttribute("creationTimeFrom", null == creationTimeFrom ? "0" : creationTimeFrom);
		model.addAttribute("creationTimeTo", null == creationTimeTo ? "0" : creationTimeTo);

		return "history/testResultList";
	}

	@RequestMapping("/history/testResultDetails/{correlationId}")
	public String getTestResultDetails(@PathVariable String correlationId, Model model) {
		log.info("getTestResultDetails correlationId:{}", correlationId);
		final List<SoapRecordEntity> soapRecordEntityList = internalDBStore.querySoapRecordList(correlationId);
		model.addAttribute("soapRecord", soapRecordEntityList);
		return "history/testResultDetails";
	}

	@RequestMapping(value = "/runManualTest", method = RequestMethod.POST)
	// @Transactional
	@ResponseBody
	public String runManualTest(@RequestParam Map<String, Object> params) throws ParseException {
		log.info("runManualTest params:{}", params);
		final String correlationId = StringUtils.isNotEmpty((String) params.get("correlationId"))
				? (String) params.get("correlationId") : internalDBStore.getCorrelationIdBySequence();

		// invoke API
		final String api_radio = (String) params.get("api_radio");
		
		switch(api_radio){
		
		case "networkLookup":
			new NetworkLookup().networkLookup(params, internalDBStore, correlationId, api_radio);
			break;
		case "sendSMS":
			new SendPIN().sendPIN(params, internalDBStore, correlationId, api_radio);
			break;
		case "getACR":
			new GetACR().getACR(params, internalDBStore, correlationId, api_radio);
			break;
		case "getMSISDN":
			new GetMSISDN().getMSISDN(params, internalDBStore, correlationId, api_radio);
			break;
		case "accountProfile":
			new AccountProfile().accountProfile(params, internalDBStore, correlationId, api_radio);
			break;
		case "charge":
			new Charge().charge(params, internalDBStore, correlationId, api_radio);
			break;
		case "reverse":
			new Reverse().reverse(params, internalDBStore, correlationId, api_radio);
			break;
		case "refund":
			new Refund().refund(params, internalDBStore, correlationId, api_radio);
			break;
		case "credit":
			new Credit().credit(params, internalDBStore, correlationId, api_radio);
			break;
		case "paymentStatus":
			new PaymentStatus().paymentStatus(params, internalDBStore, correlationId, api_radio);
			break;
		case "refundStatus":
			new RefundStatus().refundStatus(params, internalDBStore, correlationId, api_radio);
			break;
		}
		
		return String.format("carmichael/history/testResultDetails/%s", correlationId);
	}

	@RequestMapping("/manualTesting")
	public String getManualTesting(Model model) {
		// final Map<String, Object> envConfig =
		// internalDBStore.queryEnvConfig();
		// for (String key : envConfig.keySet()) {
		// model.addAttribute(key, (String) envConfig.get(key));
		// }
		return "manualTesting";
	}
}
