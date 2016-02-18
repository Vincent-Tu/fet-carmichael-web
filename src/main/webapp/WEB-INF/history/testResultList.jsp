<!DOCTYPE html>
<html lang="en">
    <head>
        <%@include file="/WEB-INF/header.jsp" %>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Test Result List</title>	
    </head>
    <body>
        <!-- Page Content -->
        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <div class="list-group">
                        <a href="carmichael/history" class="list-group-item active">History</a>
                        <a href="carmichael/manualTesting" class="list-group-item">Manual Testing</a>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="thumbnail">
                        <div class="caption-full">
                            <div class="table-responsive">
	                            <form id="testResultListSearchForm" class="form-horizontal" role="form">
	                            
		                            <div class="form-group">
		                               	<label class="col-md-3 control-label" for="msisdn">MSISDN:</label>
		                               	<div class="input-group col-md-7">
		                               		<input type="text" id="msisdn" name="msisdn" maxLength="255" value="<c:if test="${'0' != msisdn}">${msisdn}</c:if>" class="form-control"/>
		                               		<span></span>
		                               	</div>
		                            </div>
		                            
		                            <div class="form-group">
		                               	<label class="col-md-3 control-label" for="requestid">Request Id:</label>
		                               	<div class="input-group col-md-7">
		                               		<input type="text" id="requestid" name="requestid" maxLength="255" value="<c:if test="${'0' != requestid}">${requestid}</c:if>" class="form-control"/>
		                               		<span></span>
		                               	</div>
		                            </div>
		                            
		                            <div class="form-group">
		                               	<label class="col-md-3 control-label" for="uid">UID(ACR):</label>
		                               	<div class="input-group col-md-7">
		                               		<input type="text" id="uid" name="uid" maxLength="255" value="<c:if test="${'0' != uid}">${uid}</c:if>" class="form-control"/>
		                               		<span></span>
		                               	</div>
		                            </div>
	                            
		                            <%-- <div class="form-group" hidden="true">
		                               	<label class="col-md-3 control-label" for="correlationIdFrom" hidden="true" >Correlation id from:</label>
		                               	<div class="input-group col-md-7" hidden="true">
		                               		<input hidden="true" type="text" id="correlationIdFrom" maxLength="17" name="correlationIdFrom" class="numeric form-control" value="<c:if test="${'0' != correlationIdFrom}">${correlationIdFrom}</c:if>"/>
		                               		<span class="input-group-addon" hidden="true">to</span>
		                               		<input hidden="true" type="text" id="correlationIdTo" maxLength="17" name="correlationIdTo" class="numeric form-control" value="<c:if test="${'0' != correlationIdTo}">${correlationIdTo}</c:if>"/>
		                               	</div>
		                            </div> --%>
		                            
		                            <div class="form-group">
		                               	<label class="col-md-3 control-label" for="creationTimeFrom">Creation Time from:</label>
						  				<div class='input-group col-md-7'>
						                    <input type='text' class="form-control datetime"  id="creationTimeFrom" name="creationTimeFrom"  value="<c:if test="${'0' != creationTimeFrom}">${creationTimeFrom}</c:if>" placeholder="YYYY-MM-DD HH:mm:ss"/>
						                    <span class="input-group-addon">to</span>
						                    <input type='text' class="form-control datetime"  id="creationTimeTo" name="creationTimeTo" value="<c:if test="${'0' != creationTimeTo}">${creationTimeTo}</c:if>" placeholder="YYYY-MM-DD HH:mm:ss"/>
						                </div>
		                            </div>
		                            
		                            <div class="form-group">
		                            	<div class="input-group col-md-offset-3 col-md-7">
		                                	<input type="button" class="btn btn-primary" name="search" value="search">
		                                	<span></span>
		                                </div>
		                            </div>
	                            
	                            </form>
	                            <nav>
	                                <ul class="pagination">
	                                    <li <c:if test="${disablePrevious}">class="disabled"</c:if>>
	                                        <a <c:if test="${!disablePrevious}">href="carmichael/history/testResultList/${msisdn}/${requestid}/${uid}/${creationTimeFrom}/${creationTimeTo}/${begin - 1}"</c:if> aria-label="Previous" id="pagePrevious">
	                                            <span aria-hidden="true">&laquo;</span>
	                                        </a>
	                                    </li>
	                                    <c:forEach var="i" begin="${begin}" end="${end}" step="1">
	                                        <li <c:if test="${i == clickPage}">class="active"</c:if>><a href="carmichael/history/testResultList/${msisdn}/${requestid}/${uid}/${creationTimeFrom}/${creationTimeTo}/${i}">${i}</a></li>
	                                        </c:forEach>
	                                    <li <c:if test="${disableNext}">class="disabled"</c:if>>
	                                        <a <c:if test="${!disableNext}">href="carmichael/history/testResultList/${msisdn}/${requestid}/${uid}/${creationTimeFrom}/${creationTimeTo}/${end + 1}"</c:if> aria-label="Next" id="pageNext">
	                                            <span aria-hidden="true">&raquo;</span>
	                                        </a>
	                                    </li>
	                                </ul>
	                            </nav>
	                            <table class="table table-striped">
	                                <tr class="active">
	                               		<th>Id</th>
	                                    <th>MSISDN</th>
	                                    <th>Request Id</th>
	                                    <th>UID(ACR)</th>
	                                    <th>API</th>
	                                    <th>Creation Time</th>
	                                    <th>Result Link</th>
	                                </tr>
	                                <c:forEach var="historyList" begin="0" items="${requestScope.historyList}">
	                                    <tr>
	                                    	<td>
	                                            ${historyList.correlationId}
	                                        </td>
	                                        <td>
	                                            ${historyList.msisdn}
	                                        </td>
	                                        <td>
	                                            ${historyList.requestid}
	                                        </td>
	                                        <td>
	                                            ${historyList.uid}
	                                        </td>
	                                        <td>
	                                            ${historyList.api_radio}
	                                        </td>
	                                        <td>
	                                            ${historyList.creationTime}
	                                        </td>
	                                        <td>
	                                            <a href="carmichael/history/testResultDetails/${historyList.correlationId}">Test Result</a>
	                                        </td>
	                                    </tr>
	                                </c:forEach>
	                            </table>
	                            <nav>
	                                <ul class="pagination">
	                                    <li <c:if test="${disablePrevious}">class="disabled"</c:if>>
	                                        <a <c:if test="${!disablePrevious}">href="carmichael/history/testResultList/${msisdn}/${requestid}/${uid}/${creationTimeFrom}/${creationTimeTo}/${begin - 1}"</c:if> aria-label="Previous" id="pagePrevious">
	                                            <span aria-hidden="true">&laquo;</span>
	                                        </a>
	                                    </li>
	                                    <c:forEach var="i" begin="${begin}" end="${end}" step="1">
	                                        <li <c:if test="${i == clickPage}">class="active"</c:if>><a href="carmichael/history/testResultList/${msisdn}/${requestid}/${uid}/${creationTimeFrom}/${creationTimeTo}/${i}">${i}</a></li>
	                                        </c:forEach>
	                                    <li <c:if test="${disableNext}">class="disabled"</c:if>>
	                                        <a <c:if test="${!disableNext}">href="carmichael/history/testResultList/${msisdn}/${requestid}/${uid}/${creationTimeFrom}/${creationTimeTo}/${end + 1}"</c:if> aria-label="Next" id="pageNext">
	                                            <span aria-hidden="true">&raquo;</span>
	                                        </a>
	                                    </li>
	                                </ul>
	                            </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <style>
		.form-control + .glyphicon {
		  position: absolute;
		  right: 0;
		  padding: 8px 27px;
		}
    </style>
    <script>
	    $(document).ready(function () {
	        $(".numeric").numeric();
	        $('.datetime').datetimepicker({
	        	format: "YYYY-MM-DD HH:mm:ss",
	        	autoclose:true,
	        	useSeconds: true,
	        	todayHighlight: true,
	        	todayBtn: true
	        });
	    });
	    
        $('#testResultListSearchForm input[name=search]').click(function(){
            var msisdn = !$("#msisdn").val().split(" ").join("") ? 0 : $("#msisdn").val();
            var requestid = !$("#requestid").val().split(" ").join("") ? 0 : $("#requestid").val();
            var uid = !$("#uid").val().split(" ").join("") ? 0 : $("#uid").val();
            /* var correlationIdFrom = !$("#correlationIdFrom").val() ? 0 : $("#correlationIdFrom").val(); 
            var correlationIdTo = !$("#correlationIdTo").val() ? 0 : $("#correlationIdTo").val() */
            var creationTimeFrom = !$("#creationTimeFrom").val() ? 0 : $("#creationTimeFrom").val();
            var creationTimeTo = !$("#creationTimeTo").val() ? 0 : $("#creationTimeTo").val();
            var projectName = window.location.pathname.split('/')[1];
            window.location.href = "http://"+window.location.host+"/"+projectName+"/carmichael/history/testResultList/" + msisdn +'/' + requestid +'/' + uid + '/' + creationTimeFrom + '/'+ creationTimeTo+'/1';
        });
    </script>
</html>