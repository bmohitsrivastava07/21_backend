package com.ArtGalleryManagement.Backend.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ArtGalleryManagement.Backend.Entity.Product;
import com.ArtGalleryManagement.Backend.RequestModels.PaymentInfoRequest;
import com.ArtGalleryManagement.Backend.ResponseModels.ShelfCurrentLoansResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface PaymentService {
	 public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException;
	 public ResponseEntity<String> stripePayment(String userEmail) throws Exception;
	 
}
