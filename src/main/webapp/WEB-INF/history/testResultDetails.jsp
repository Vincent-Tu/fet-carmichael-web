<!DOCTYPE html>
<html lang="en">
    <head>
        <%@include file="/WEB-INF/header.jsp" %>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Test Result Details</title>
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
                                <div>
                                    <center>CARMICHAEL Calls</center>
                                    <table class="table table-bordered">
                                        <tr class="active">
                                            <th>Id</th>
                                            <th>Call</th>
                                            <th>Result</th>
                                            <th>User Message</th>
                                        </tr>
                                        <c:forEach var="soapRecord" begin="0" items="${requestScope.soapRecord}" varStatus="status">
                                        <tr>
                                            <td name="correlationId">${soapRecord.correlationId}</td>
                                            <td name="soapCall">${soapRecord.soapCall}</td>
                                            <td name="result${status.count}">${soapRecord.result}</td>
                                            <td name="userMessage${status.count}">${soapRecord.userMessage}</td>
                                        </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <c:forEach var="soapRecord" begin="0" items="${requestScope.soapRecord}">
                            <div class="form-group">
                                <label for="comment"><font size="6">${soapRecord.soapCall} Request</font></label>
                                <textarea class="form-control" rows="11" id="comment">${soapRecord.sendMessage}</textarea>
                            </div>
                            <div class="form-group">
                                <label for="comment"><font size="6">${soapRecord.soapCall} Response</font></label>
                                <textarea class="form-control" rows="11" id="comment">${soapRecord.receiveMessage}</textarea>
                            </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>