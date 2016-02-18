package fet.carmichael.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ben
 *
 */
@Component
public class InternalDBStore {
	private static final Logger log = LoggerFactory.getLogger(InternalDBStore.class);

	final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Autowired
	@Qualifier(value = "jdbcTemplateForH2")
	private JdbcTemplate jdbcTemplateForH2;
	@Autowired
	@Qualifier(value = "namedParameterJdbcTemplateForH2")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplateForH2;

	public void saveTestCaseForNetworkLookup(final String correlationId, final String api_radio, final String msisdn) {
		if (StringUtils.isNotEmpty(api_radio)) {
			final String sql = "insert into TESTCASE_HISTORY(CORRELATION_ID, API_RADIO, MSISDN) values(:CORRELATION_ID, :API_RADIO, :MSISDN)";

			final MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("CORRELATION_ID", correlationId).addValue("API_RADIO", api_radio)
					.addValue("MSISDN", msisdn);
			log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
			namedParameterJdbcTemplateForH2.update(sql, parameters);
		}
	}
	
	public void saveTestCaseForSendPIN(final String correlationId, final String api_radio, final String message) {
		if (StringUtils.isNotEmpty(api_radio)) {
			final String sql = "insert into TESTCASE_HISTORY(CORRELATION_ID, API_RADIO, MESSAGE) values(:CORRELATION_ID, :API_RADIO, :MESSAGE)";

			final MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("CORRELATION_ID", correlationId)
					.addValue("API_RADIO", api_radio)
					.addValue("MESSAGE", message);
			log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
			namedParameterJdbcTemplateForH2.update(sql, parameters);
		}
	}

	public void saveTestCaseForGetACRAndGetMSISDN(final String correlationId, final String api_radio,
			final String msisdn, final String uid, final String networkStatus) {
		if (StringUtils.isNotEmpty(api_radio)) {
			final String sql = "insert into TESTCASE_HISTORY(CORRELATION_ID, API_RADIO, MSISDN, UID, NETWORKSTATUS) values(:CORRELATION_ID, :API_RADIO, :MSISDN, :UID, :NETWORKSTATUS)";

			final MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("CORRELATION_ID", correlationId).addValue("API_RADIO", api_radio)
					.addValue("MSISDN", msisdn).addValue("UID", uid).addValue("NETWORKSTATUS", networkStatus);
			log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
			namedParameterJdbcTemplateForH2.update(sql, parameters);
		}
	}

	public void saveTestCaseForAccountProfile(final String correlationId, final String api_radio,
			final String account) {
		if (StringUtils.isNotEmpty(api_radio)) {
			final String sql = "insert into TESTCASE_HISTORY(CORRELATION_ID, API_RADIO, ACCOUNT, UID) values(:CORRELATION_ID, :API_RADIO, :ACCOUNT, :UID)";

			final MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("CORRELATION_ID", correlationId).addValue("API_RADIO", api_radio)
					.addValue("ACCOUNT", account).addValue("UID", account);
			log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
			namedParameterJdbcTemplateForH2.update(sql, parameters);
		}
	}

	public void saveTestCaseForCharge(final String correlationId, final String api_radio, final String requestId,
			final String clientTransactionId, final String account, final String purchaseAmount,
			final String purchaseCurrency, final String productDescription, final String orderNo,
			final String issuerPaymentId, final Date chargeDate) {
		if (StringUtils.isNotEmpty(api_radio)) {

			final String sql = "insert into TESTCASE_HISTORY(CORRELATION_ID, API_RADIO, REQUESTID, CLIENTTRANSACTIONID, ACCOUNT, "
					+ "PURCHASEAMOUNT, PURCHASECURRENCY, PRODUCTDESCRIPTION, ORDERNO, ISSUERPAYMENTID, CHARGEDATE) "
					+ "values(:CORRELATION_ID, :API_RADIO, :REQUESTID, :CLIENTTRANSACTIONID, :ACCOUNT, :PURCHASEAMOUNT, "
					+ ":PURCHASECURRENCY, :PRODUCTDESCRIPTION, :ORDERNO, :ISSUERPAYMENTID, :CHARGEDATE)";

			final MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("CORRELATION_ID", correlationId).addValue("API_RADIO", api_radio)
					.addValue("REQUESTID", requestId).addValue("CLIENTTRANSACTIONID", clientTransactionId)
					.addValue("ACCOUNT", account).addValue("PURCHASEAMOUNT", purchaseAmount)
					.addValue("PURCHASECURRENCY", purchaseCurrency).addValue("PRODUCTDESCRIPTION", productDescription)
					.addValue("ORDERNO", orderNo).addValue("ISSUERPAYMENTID", issuerPaymentId)
					.addValue("CHARGEDATE", chargeDate);
			log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
			namedParameterJdbcTemplateForH2.update(sql, parameters);
		}
	}

	public void saveTestCaseForReverse(final String correlationId, final String api_radio, final String chargeRequestId,
			final String issuerReverseId, final Date reverseDate) {
		if (StringUtils.isNotEmpty(api_radio)) {

			final String sql = "insert into TESTCASE_HISTORY(CORRELATION_ID, API_RADIO, CHARGEREQUESTID, "
					+ "ISSUERREVERSEID, REVERSEDATE, REQUESTID) values(:CORRELATION_ID, :API_RADIO, :CHARGEREQUESTID, "
					+ ":ISSUERREVERSEID, :REVERSEDATE, :REQUESTID )";

			final MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("CORRELATION_ID", correlationId).addValue("API_RADIO", api_radio)
					.addValue("CHARGEREQUESTID", chargeRequestId).addValue("ISSUERREVERSEID", issuerReverseId)
					.addValue("REVERSEDATE", reverseDate).addValue("REQUESTID", chargeRequestId);
			log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
			namedParameterJdbcTemplateForH2.update(sql, parameters);
		}
	}

	public void saveTestCaseForRefund(final String correlationId, final String api_radio, final String requestId,
			final String clientRefundId, final String issuerPaymentId, final String issuerRefundId,
			final String refundAmount, final String refundCurrency, final String refundReason, final Date refundDate) {
		if (StringUtils.isNotEmpty(api_radio)) {

			final String sql = "insert into TESTCASE_HISTORY(CORRELATION_ID, API_RADIO, REQUESTID, CLIENTREFUNDID, "
					+ "ISSUERPAYMENTID, ISSUERREFUNDID, REFUNDAMOUNT, REFUNDCURRENCY, REFUNDREASON, REFUNDDATE) values(:CORRELATION_ID, :API_RADIO, :REQUESTID, :CLIENTREFUNDID, "
					+ ":ISSUERPAYMENTID, :ISSUERREFUNDID, :REFUNDAMOUNT, :REFUNDCURRENCY, :REFUNDREASON, :REFUNDDATE)";

			final MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("CORRELATION_ID", correlationId).addValue("API_RADIO", api_radio)
					.addValue("REQUESTID", requestId).addValue("CLIENTREFUNDID", clientRefundId)
					.addValue("ISSUERPAYMENTID", issuerPaymentId).addValue("ISSUERREFUNDID", issuerRefundId)
					.addValue("REFUNDAMOUNT", refundAmount).addValue("REFUNDCURRENCY", refundCurrency)
					.addValue("REFUNDREASON", refundReason).addValue("REFUNDDATE", refundDate);
			log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
			namedParameterJdbcTemplateForH2.update(sql, parameters);
		}
	}

	public void saveTestCaseForCredit(final String correlationId, final String api_radio, final String requestId,
			final String clientCreditId, final String account, final String issuerCreditId, final String creditAmount,
			final String creditCurrency, final String creditReason, final Date creditDate) {
		if (StringUtils.isNotEmpty(api_radio)) {

			final String sql = "insert into TESTCASE_HISTORY(CORRELATION_ID, API_RADIO, REQUESTID, CLIENTCREDITID, "
					+ "ACCOUNT, ISSUERCREDITID, CREDITAMOUNT, CREDITCURRENCY, CREDITREASON, CREDITDATE) "
					+ "values(:CORRELATION_ID, :API_RADIO, :REQUESTID, :CLIENTCREDITID, "
					+ ":ACCOUNT, :ISSUERCREDITID, :CREDITAMOUNT, :CREDITCURRENCY, :CREDITREASON, :CREDITDATE)";

			final MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("CORRELATION_ID", correlationId).addValue("API_RADIO", api_radio)
					.addValue("REQUESTID", requestId).addValue("CLIENTCREDITID", clientCreditId)
					.addValue("ACCOUNT", account).addValue("ISSUERCREDITID", issuerCreditId)
					.addValue("CREDITAMOUNT", creditAmount).addValue("CREDITCURRENCY", creditCurrency)
					.addValue("CREDITREASON", creditReason).addValue("CREDITDATE", creditDate);
			log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
			namedParameterJdbcTemplateForH2.update(sql, parameters);
		}
	}

	public void saveTestCaseForPaymentStatus(final String correlationId, final String api_radio,
			final String paymentRequestId, final String issuerPaymentId) {

		if (StringUtils.isNotEmpty(api_radio)) {
			final String sql = "insert into TESTCASE_HISTORY(CORRELATION_ID, API_RADIO, PAYMENTREQUESTID, ISSUERPAYMENTID, REQUESTID) "
					+ "values(:CORRELATION_ID, :API_RADIO, :PAYMENTREQUESTID, :ISSUERPAYMENTID, :REQUESTID)";

			final MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("CORRELATION_ID", correlationId)
					.addValue("API_RADIO", api_radio)
					.addValue("PAYMENTREQUESTID", paymentRequestId)
					.addValue("REQUESTID", paymentRequestId)
					.addValue("ISSUERPAYMENTID", issuerPaymentId);
			log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
			namedParameterJdbcTemplateForH2.update(sql, parameters);
		}
	}

	public void saveTestCaseForRefundStatus(final String correlationId, final String api_radio,
			final String refundRequestId, final String issuerRefundId) {

		if (StringUtils.isNotEmpty(api_radio)) {
			final String sql = "insert into TESTCASE_HISTORY(CORRELATION_ID, API_RADIO, REFUNDREQUESTID, ISSUERREFUNDID, REQUESTID) "
					+ "values(:CORRELATION_ID, :API_RADIO, :REFUNDREQUESTID, :ISSUERREFUNDID, :REQUESTID)";

			final MapSqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("CORRELATION_ID", correlationId)
					.addValue("API_RADIO", api_radio)
					.addValue("REFUNDREQUESTID", refundRequestId)
					.addValue("REQUESTID", refundRequestId)
					.addValue("ISSUERREFUNDID", issuerRefundId);
			log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
			namedParameterJdbcTemplateForH2.update(sql, parameters);
		}
	}


	public void saveSoapRecord(final String correlationId, final String sendMessage, final String receiveMessage,
			final String result, final String userMessage, final String soapCall) {
		final String sql = "insert into INVOKE_RECORD(CORRELATION_ID, SEND_MESSAGE, RECEIVE_MESSAGE, RESULT, USER_MESSAGE, SOAP_CALL) values(:CORRELATION_ID, :SEND_MESSAGE, :RECEIVE_MESSAGE, :RESULT, :USER_MESSAGE, :SOAP_CALL)";

		final MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("CORRELATION_ID", correlationId)
				.addValue("SEND_MESSAGE", sendMessage).addValue("RECEIVE_MESSAGE", receiveMessage)
				.addValue("RESULT", result).addValue("USER_MESSAGE", userMessage).addValue("SOAP_CALL", soapCall);

		log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
		namedParameterJdbcTemplateForH2.update(sql, parameters);
	}

	public String getCorrelationIdBySequence() {
		final String sql = "select CORRELATION_ID_SEQ.nextval as correlation_id from dual";

		log.info("sql:[{}]", sql);
		final String correlationId = jdbcTemplateForH2.queryForObject(sql, String.class);
		return correlationId;
	}

	public int queryHistoryRowsCount() {
		final String sql = "SELECT * FROM TESTCASE_HISTORY";
		log.info("sql:[{}]", sql);
		return jdbcTemplateForH2.queryForList(sql).size();
	}

	public List<TestCaseHistoryEntity> queryHistoryList(final String msisdn, final String requestid, final String uid, 
			final String creationTimeFrom, final String creationTimeTo, final int page,
			final int count) {
		final StringBuilder sql = new StringBuilder("SELECT * FROM TESTCASE_HISTORY WHERE 0=0 ");

		final MapSqlParameterSource parameters = new MapSqlParameterSource();

		boolean alLeastOneNotEmpty = false;
		if (StringUtils.isNotEmpty(msisdn)) {
			alLeastOneNotEmpty = true;
			sql.append("AND MSISDN = :MSISDN ");
			parameters.addValue("MSISDN", msisdn);
		}
		if (StringUtils.isNotEmpty(requestid)) {
			alLeastOneNotEmpty = true;
			sql.append("AND REQUESTID = :REQUESTID ");
			parameters.addValue("REQUESTID", requestid);
		}
		if (StringUtils.isNotEmpty(uid)) {
			alLeastOneNotEmpty = true;
			sql.append("AND UID = :UID ");
			parameters.addValue("UID", uid);
		}
		if (StringUtils.isNotEmpty(creationTimeFrom)) {
			alLeastOneNotEmpty = true;
			sql.append("AND CREATION_TIME >= :CREATION_TIME_FROM ");
			parameters.addValue("CREATION_TIME_FROM", creationTimeFrom);
		}
		if (StringUtils.isNotEmpty(creationTimeTo)) {
			alLeastOneNotEmpty = true;
			sql.append("AND CREATION_TIME <= :CREATION_TIME_TO ");
			parameters.addValue("CREATION_TIME_TO", creationTimeTo);
		}

		if (!alLeastOneNotEmpty) {
			sql.delete(sql.indexOf("WHERE"), sql.length());
		}

		sql.append("ORDER BY CREATION_TIME DESC");

		final int totalCount = namedParameterJdbcTemplateForH2.queryForList(sql.toString(), parameters).size();

		sql.append(String.format(" LIMIT %d OFFSET %d", count, (page - 1) * count));

		log.info("sql:[{}],parameters:[{}]", sql.toString(), parameters.getValues());
		final List<Map<String, Object>> rows = namedParameterJdbcTemplateForH2.queryForList(sql.toString(), parameters);

		final List<TestCaseHistoryEntity> historyList = new ArrayList<TestCaseHistoryEntity>();
		for (int i = 0; i < rows.size(); i++) {
			final TestCaseHistoryEntity testCaseHistoryEntity = new TestCaseHistoryEntity();
			final Map<String, Object> row = rows.get(i);

			final String correlationId = (String) row.get("CORRELATION_ID");
			final String api_radio = (String) row.get("API_RADIO");
			final Timestamp creationTime = (Timestamp) row.get("CREATION_TIME");
			final String msisdn1 = (String) row.get("MSISDN");
			final String uid1 = (String) row.get("UID");
			final String requestid1 = (String) row.get("REQUESTID");

			testCaseHistoryEntity.setRequestid(requestid1);
			testCaseHistoryEntity.setUid(uid1);
			testCaseHistoryEntity.setCorrelationId(correlationId);
			testCaseHistoryEntity.setApi_radio(api_radio);
			testCaseHistoryEntity.setMsisdn(msisdn1);
			testCaseHistoryEntity.setCreationTime(simpleDateFormat.format(creationTime));
			testCaseHistoryEntity.setTotalCount(totalCount);

			historyList.add(testCaseHistoryEntity);
		}
		return historyList;
	}

	public List<SoapRecordEntity> querySoapRecordList(final String correlationId) {
		final String sql = "SELECT * FROM INVOKE_RECORD WHERE CORRELATION_ID = :CORRELATION_ID ORDER BY CREATION_TIME";

		final MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("CORRELATION_ID", correlationId);

		log.info("sql:[{}],parameters:[{}]", sql, parameters.getValues());
		final List<Map<String, Object>> rows = namedParameterJdbcTemplateForH2.queryForList(sql, parameters);
		final List<SoapRecordEntity> soapRecordList = new ArrayList<SoapRecordEntity>();
		for (int i = 0; i < rows.size(); i++) {
			final SoapRecordEntity soapRecordEntity = new SoapRecordEntity();
			final Map<String, Object> row = rows.get(i);

			final String sendMessage = (String) row.get("SEND_MESSAGE");
			final String receiveMessage = (String) row.get("RECEIVE_MESSAGE");
			final String result = (String) row.get("RESULT");
			final String userMessage = (String) row.get("USER_MESSAGE");
			final String soapCall = (String) row.get("SOAP_CALL");
			final Timestamp creationTime = (Timestamp) row.get("CREATION_TIME");

			soapRecordEntity.setCorrelationId(correlationId);
			soapRecordEntity.setSendMessage(sendMessage);
			soapRecordEntity.setReceiveMessage(receiveMessage);
			soapRecordEntity.setResult(result);
			soapRecordEntity.setUserMessage(userMessage);
			soapRecordEntity.setSoapCall(soapCall);
			soapRecordEntity.setCreationTime(simpleDateFormat.format(creationTime));

			soapRecordList.add(soapRecordEntity);
		}
		return soapRecordList;
	}
}
