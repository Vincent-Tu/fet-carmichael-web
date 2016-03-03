<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Manual Testing</title>
    <style>
        .error {
            color:#FF0000;
        }
        .placeholder{color: grey;}
		select option:first-child{color: grey; display: none;}
		select option{color: #555;} // bootstrap default color
    </style>
</head>
<body>
    <!-- Page Content -->
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <div class="list-group">
                    <a href="npp/history" class="list-group-item">History</a> 
                    <a href="npp/manualTesting" class="list-group-item active">Manual Testing</a>
                </div>
            </div>
            <div class="col-md-9">
                <div class="thumbnail">
                    <div class="caption-full">
                        <form class="form-horizontal" role="form" id="manualTestingForm">
                            <div class="form-group">
	                            <label class="col-md-3 control-label">API Calls:</label>
                              	<div class="col-md-7">
                                    <div class="checkbox">
                                        <label>
                                            <input type="radio" id="api_network_lookup" name="api_radio" value="networkLookup"> Network Lookup
                                        </label>
                                        <label>
                                            <input type="radio" id="api_send_sms" name="api_radio" value="sendSMS"> Send SMS
                                        </label>
                                        <label>
                                            <input type="radio" id="api_get_acr" name="api_radio" value="getACR"> Get ACR
                                        </label>
                                        <label>
                                            <input type="radio" id="api_get_msisdn" name="api_radio" value="getMSISDN"> Get MSISDN
                                        </label>
										<BR>
										<label>
											<input type="radio" id="api_account_profile" name="api_radio" value="accountProfile"> Account Profile
										</label>
                                        <label>
                                            <input type="radio" id="api_charge" name="api_radio" value="charge"> Charge 
                                        </label>
                                        <label>
                                            <input type="radio" id="api_reverse" name="api_radio" value="reverse"> Reverse 
                                        </label>
                                        <label>
                                            <input type="radio" id="api_refund" name="api_radio" value="refund"> Refund 
                                        </label>
                                        <label>
                                            <input type="radio" id="api_credit" name="api_radio" value="credit"> Credit
                                        </label>
                                        <label>
                                            <input type="radio" id="api_payment_status" name="api_radio" value="paymentStatus"> Payment Status
                                        </label>
                                        <label>
                                            <input type="radio" id="api_refund_status" name="api_radio" value="refundStatus"> Refund Status
                                        </label>
                                    </div>
                              	</div>
                            </div>
                            <div class="form-group" id="network_lookup" style="display: none">
                             	<label class="col-md-3 control-label" for="msisdn">MSISDN:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="msisdn"  name="msisdn" maxlength="15" class="form-control"/>
                              	</div>
                              	<label class="col-md-3 control-label" for="networkStatus2">Network Status:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="networkStatus2"  name="networkStatus2" maxlength="10" class="numeric form-control" value="0"/>
                              	</div>
                            </div>
                            <div class="form-group" id="send_sms" style="display: none">
                             	<label class="col-md-3 control-label" for="address">Address:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="address"  name="address" class="form-control"/>
                              	</div>
                              	<label class="col-md-3 control-label" for="senderAddress">Sender Address:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="senderAddress"  name="senderAddress" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="message">Message:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="message"  name="message" class="form-control"/>
                              	</div>
                              	<label class="col-md-3 control-label" for="clientCorrelator">ClientCorrelator:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="clientCorrelator"  name="clientCorrelator" class="form-control"/>
                              	</div>
                            </div>
                            <div class="form-group" id="get_acr" style="display: none">
                             	<label class="col-md-3 control-label" for="msisdn1">MSISDN:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="msisdn1"  name="msisdn1" maxlength="15" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="networkStatus">Network Status:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="networkStatus"  name="networkStatus" class="numeric form-control" value="0"/>
                              	</div>
                            </div>
                            <div class="form-group" id="get_msisdn" style="display: none">
                             	<label class="col-md-3 control-label" for="uid">UID(ACR):</label>
                              	<div class="col-md-7">
                                    <input type="text" id="uid"  name="uid" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="networkStatus1">Network Status:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="networkStatus1"  name="networkStatus1" class="numeric form-control" value="0"/>
                              	</div>
                            </div>
                            <div class="form-group" id="charge" style="display: none">
                               	<label class="col-md-3 control-label" for="requestId">Request Id:</label>
                               	<div class="col-md-7">
                                    <input type="text" id="requestId" name="requestId" class="form-control" />
                               	</div>
                             	<label class="col-md-3 control-label" for="clientTransactionId">Client Transaction Id:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="clientTransactionId"  name="clientTransactionId" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="account">Account(ACR):</label>
                              	<div class="col-md-7">
                                    <input type="text" id="account"  name="account" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="purchaseAmount">Purchase Amount:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="purchaseAmount"  name="purchaseAmount" class="numeric form-control" value="100"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="purchaseCurrency">Purchase Currency:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="purchaseCurrency"  name="purchaseCurrency" class="form-control" value="TWD"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="productDescription">Product Description:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="productDescription"  name="productDescription" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="orderNo">Order No:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="orderNo"  name="orderNo" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="chargeDate">Charge Date:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="chargeDate"  name="chargeDate" class="form-control datetime" placeholder="YYYY-MM-DD HH:mm:ss">
                              	</div>
                            </div>
                            <div class="form-group" id="reverse" style="display: none">
                               	<label class="col-md-3 control-label" for="chargeRequestId">Charge Request Id:</label>
                               	<div class="col-md-7">
                                    <input type="text" id="chargeRequestId" name="chargeRequestId" class="form-control" />
                               	</div>
                             	<label class="col-md-3 control-label" for="reverseDate">Reverse Date:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="reverseDate"  name="reverseDate" class="form-control datetime" placeholder="YYYY-MM-DD HH:mm:ss">
                              	</div>
                            </div>
                            <div class="form-group" id="refund" style="display: none">
                               	<label class="col-md-3 control-label" for="requestId1">Request Id:</label>
                               	<div class="col-md-7">
                                    <input type="text" id="requestId1" name="requestId1" class="form-control" />
                               	</div>
                             	<label class="col-md-3 control-label" for="clientRefundId">Client Refund Id:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="clientRefundId"  name="clientRefundId" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="issuerPaymentId">Issuer Payment Id(TX_Id):</label>
                              	<div class="col-md-7">
                                    <input type="text" id="issuerPaymentId"  name="issuerPaymentId" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="refundAmount">Refund Amount:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="refundAmount"  name="refundAmount" class="numeric form-control" value="100"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="refundCurrency">Refund Currency:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="refundCurrency"  name="refundCurrency" class="form-control" value="TWD"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="refundReason">Refund Reason:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="refundReason"  name="refundReason" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="refundDate">Refund Date:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="refundDate"  name="refundDate" class="form-control datetime" placeholder="YYYY-MM-DD HH:mm:ss">
                              	</div>
                            </div>
                            <div class="form-group" id="credit" style="display: none">
                               	<label class="col-md-3 control-label" for="requestId2">Request Id:</label>
                               	<div class="col-md-7">
                                    <input type="text" id="requestId2" name="requestId2" class="form-control" />
                               	</div>
                             	<label class="col-md-3 control-label" for="clientCreditId">Client Credit Id:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="clientCreditId"  name="clientCreditId" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="account1">Account(ACR):</label>
                              	<div class="col-md-7">
                                    <input type="text" id="account1"  name="account1" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="creditAmount">Credit Amount:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="creditAmount"  name="creditAmount" class="numeric form-control" value="100"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="creditCurrency">Credit Currency:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="creditCurrency"  name="creditCurrency" class="form-control" value="TWD"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="creditReason">Credit Reason:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="creditReason"  name="creditReason" class="form-control"/>
                              	</div>
                             	<label class="col-md-3 control-label" for="creditDate">Credit Date:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="creditDate"  name="creditDate" class="form-control datetime" placeholder="YYYY-MM-DD HH:mm:ss">
                              	</div>
                            </div>
                            <div class="form-group" id="account_profile" style="display: none">
                             	<label class="col-md-3 control-label" for="account2">Account(ACR):</label>
                              	<div class="col-md-7">
                                    <input type="text" id="account2"  name="account2" class="form-control"/>
                              	</div>
                            </div>
                            <div class="form-group" id="payment_status" style="display: none">
                             	<label class="col-md-3 control-label" for="paymentRequestId">Payment Request Id:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="paymentRequestId"  name="paymentRequestId" class="form-control"/>
                              	</div>
                            </div>
                            <div class="form-group" id="refund_status" style="display: none">
                             	<label class="col-md-3 control-label" for="refundRequestId">Refund Request Id:</label>
                              	<div class="col-md-7">
                                    <input type="text" id="refundRequestId"  name="refundRequestId" class="form-control"/>
                              	</div>
                            </div>
							<div class="form-group" id="run_manual_test_btn">
                            	<div class="col-md-offset-3 col-md-7">
                                    <input type="submit" class="btn btn-primary" id="runManualTestBtn" name ="runManualTest" value="Run Manual Test" data-loading-text="Running..." />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script>
    $(document).ready(function() {
    	
    	$('.datetime').datetimepicker({
        	format: "YYYY-MM-DD HH:mm:ss",
        	autoclose:true,
        	useSeconds: true,
        	todayHighlight: true,
        	todayBtn: true
        });
    	
        var $manualTestingForm = $('#manualTestingForm');
        var $runManualTestBtn = $('#runManualTestBtn');
        
        $manualTestingForm.validate({
            submitHandler: function() {
                $runManualTestBtn.button('loading');
                $.ajax({
                    type: 'POST',
                    url: 'npp/runManualTest',
                    data: $manualTestingForm.serializeArray(),
                    success: function(response) {
                    	redirect(response);
                    }
                });
                return false;
            }
        });
        var redirect = function(response) {
        	var projectName = window.location.pathname.split('/')[1];
        	window.location.href = 'http://'+window.location.host+'/'+projectName+'/'+response;
        }
        

        $(".numeric").numeric();
       
		var $manualTestingForm = $('#manualTestingForm');

		var $network_lookup = $manualTestingForm.find("#network_lookup");
		var $send_sms = $manualTestingForm.find("#send_sms");
		var $get_acr = $manualTestingForm.find("#get_acr");
		var $get_msisdn = $manualTestingForm.find("#get_msisdn");
		var $account_profile = $manualTestingForm.find("#account_profile");
		var $charge = $manualTestingForm.find("#charge");
		var $reverse = $manualTestingForm.find("#reverse");
		var $refund = $manualTestingForm.find("#refund");
		var $credit = $manualTestingForm.find("#credit");
		var $account_profile = $manualTestingForm.find("#account_profile");
		var $payment_status = $manualTestingForm.find("#payment_status");
		var $refund_status = $manualTestingForm.find("#refund_status");
		

        $manualTestingForm.find('#api_network_lookup').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();

			$network_lookup.show();
        });
        $manualTestingForm.find('#api_send_sms').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();
	
			$send_sms.show();
        });
        $manualTestingForm.find('#api_get_acr').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();
	
			$get_acr.show();
        });
        $manualTestingForm.find('#api_get_msisdn').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();
	
			$get_msisdn.show();
        });
        $manualTestingForm.find('#api_account_profile').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();
	
			$account_profile.show();
        });
        $manualTestingForm.find('#api_charge').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();

			$charge.show();
        });
        $manualTestingForm.find('#api_reverse').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();

			$reverse.show();
        });
        $manualTestingForm.find('#api_refund').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();

			$refund.show();
        });
        $manualTestingForm.find('#api_credit').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();

			$credit.show();
        });
        $manualTestingForm.find('#api_account_profile').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();

			$account_profile.show();
        });
        $manualTestingForm.find('#api_payment_status').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();

			$payment_status.show();
        });
        $manualTestingForm.find('#api_refund_status').change(function () {
			$network_lookup.hide();
			$send_sms.hide();
			$get_acr.hide();
			$get_msisdn.hide();
			$account_profile.hide();
			$charge.hide();
			$reverse.hide();
			$refund.hide();
			$credit.hide();
			$account_profile.hide();
			$payment_status.hide();
			$refund_status.hide();

			$refund_status.show();
        });
        
    });
</script>
</html>