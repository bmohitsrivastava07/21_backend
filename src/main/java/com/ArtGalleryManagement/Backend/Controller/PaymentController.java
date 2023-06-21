package com.ArtGalleryManagement.Backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.UserEmailNotFoundException;
import com.ArtGalleryManagement.Backend.RequestModels.PaymentInfoRequest;
import com.ArtGalleryManagement.Backend.Service.PaymentService;
import com.ArtGalleryManagement.Backend.Utils.ExtractJWT;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/payment/secure")
public class PaymentController {
	
	  private static final Logger
	 logger=LoggerFactory.getLogger(PaymentController.class);

	PaymentService paymentService;
	
	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping("/payment-intent")
	public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest) throws StripeException{
		logger.info("Creating payment intent for request: {}",paymentInfoRequest); 
		PaymentIntent paymentIntent= paymentService.createPaymentIntent(paymentInfoRequest);
		String paymentStr= paymentIntent.toJson();
		return new ResponseEntity<>(paymentStr,HttpStatus.OK);
	}
	
	@PutMapping("/payment-complete")
	public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value="Authorization") String token) throws Exception{
		String userEmail= ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		if(userEmail==null) {
			logger.error("User email not found"); 
			throw new UserEmailNotFoundException();
		}
		 logger.info("Completing stripe payment for user email: {}",userEmail); 
		return paymentService.stripePayment(userEmail);
	}
	
	
	
}