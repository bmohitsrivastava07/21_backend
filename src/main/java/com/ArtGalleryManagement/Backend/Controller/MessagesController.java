package com.ArtGalleryManagement.Backend.Controller;

import com.ArtGalleryManagement.Backend.Entity.*;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.MethodNotAllowedException;
import com.ArtGalleryManagement.Backend.RequestModels.*;
import com.ArtGalleryManagement.Backend.Service.*;
import com.ArtGalleryManagement.Backend.Utils.ExtractJWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import com.ArtGalleryManagement.Backend.utils.*;
*/import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/messages")
public class MessagesController {
	
	 private static final Logger
	  logger=LoggerFactory.getLogger(MessagesController.class);
	 
	private MessagesServiceImpl messagesService;

	@Autowired
	public MessagesController(MessagesServiceImpl messagesService) {
		this.messagesService = messagesService;
	}

	@PostMapping("/secure/add/message")
	public void postMessage(@RequestHeader(value="Authorization") String token,@RequestBody Message messageRequest) {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		 logger.info("Received a new message: {}",messageRequest);
		messagesService.postMessage(messageRequest, userEmail);
	}

	@PutMapping("/secure/admin/message")
	public void putMessage(@RequestHeader(value="Authorization") String token,@RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		String admin =ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
		if (admin == null || !admin.equals("admin")) {
			logger.warn("Unauthorized access to putMessage endpoint"); 
			throw new MethodNotAllowedException();
		}
		 logger.info("Processing admin question: {}",adminQuestionRequest); 
		messagesService.putMessage(adminQuestionRequest, userEmail);
	}

}