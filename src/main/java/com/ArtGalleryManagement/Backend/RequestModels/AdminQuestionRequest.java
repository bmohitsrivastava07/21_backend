package com.ArtGalleryManagement.Backend.RequestModels;

import lombok.Data;

@Data
public class AdminQuestionRequest {

    private Long messageId;

    private String response;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
    
    
}
