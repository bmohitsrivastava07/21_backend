package com.ArtGalleryManagement.Backend.Service;

import com.ArtGalleryManagement.Backend.Entity.Message;
import com.ArtGalleryManagement.Backend.RequestModels.AdminQuestionRequest;

public interface MessageService {
	 public void postMessage(Message messageRequest, String userEmail);
	 public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception ;
	 
}
