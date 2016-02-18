package fet.carmichael.dao;

/**
 *
 * @author Ben
 *
 */
public class SoapRecordEntity {

    private String correlationId;

    private String sendMessage;

    private String receiveMessage;

    private String result;

    private String userMessage;

    private String soapCall;

    private String creationTime;
    
    private String echoStatus;
    
    private String echoMessage;
    
    private String details;
    
    public String getEchoStatus() {
		return echoStatus;
	}

	public void setEchoStatus(String echoStatus) {
		this.echoStatus = echoStatus;
	}

	public String getEchoMessage() {
		return echoMessage;
	}

	public void setEchoMessage(String echoMessage) {
		this.echoMessage = echoMessage;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getGbaseVerstion() {
		return gbaseVerstion;
	}

	public void setGbaseVerstion(String gbaseVerstion) {
		this.gbaseVerstion = gbaseVerstion;
	}

	public String getFetVerstion() {
		return fetVerstion;
	}

	public void setFetVerstion(String fetVerstion) {
		this.fetVerstion = fetVerstion;
	}

	private String gbaseVerstion;
    
    private String fetVerstion;
    
    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    public String getReceiveMessage() {
        return receiveMessage;
    }

    public void setReceiveMessage(String receiveMessage) {
        this.receiveMessage = receiveMessage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getSoapCall() {
        return soapCall;
    }

    public void setSoapCall(String soapCall) {
        this.soapCall = soapCall;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

}
