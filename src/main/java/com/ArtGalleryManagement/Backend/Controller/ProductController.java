package com.ArtGalleryManagement.Backend.Controller;

import com.ArtGalleryManagement.Backend.Entity.*;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.NoProductFoundOrNotCheckedException;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.ReturnProductException;
import com.ArtGalleryManagement.Backend.ResponseModels.*;
import com.ArtGalleryManagement.Backend.Service.*;
import com.ArtGalleryManagement.Backend.Utils.ExtractJWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/products")
public class ProductController {

	private Logger logger= LoggerFactory.getLogger(ProductController.class); 
	private ProductServiceImpl productService;

	@Autowired
	public ProductController(ProductServiceImpl productService) {
		this.productService = productService;
	}

	@GetMapping("/secure/currentloans")
	public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		 logger.info("Fetching current loans for user: {}", userEmail); 
		return productService.currentLoans(userEmail);
	}

	@GetMapping("/secure/currentloans/count")
	public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		 logger.info("Fetching current loans count for user: {}", userEmail); 
		return productService.currentLoansCount(userEmail);
	}

	@GetMapping("/secure/ischeckedout/byuser")
	public Boolean checkoutProductByUser(@RequestHeader(value = "Authorization") String token,@RequestParam Long productId) {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		  logger.info("Checking if product with ID {} is checked out by user: {}",
		  productId, userEmail);
		 
		return productService.checkoutProductByUser(userEmail, productId);
	}

	@PutMapping("/secure/checkout")
	public Product checkoutProduct(@RequestHeader(value = "Authorization") String token,@RequestParam Long productId) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		  logger.info("Checking out product with ID {} for user: {}", productId,
		  userEmail);
		 
		return productService.checkoutProduct(userEmail, productId);
	}

	@PutMapping("/secure/return")
	public void returnProduct(@RequestHeader(value = "Authorization") String token,@RequestParam Long productId) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
	 logger.info("Returning product with ID {} for user: {}", productId,
	 userEmail);
	
		productService.returnProduct(userEmail, productId);		 
	}

	@PutMapping("/secure/renew/loan")
	public void renewLoan(@RequestHeader(value = "Authorization") String token,@RequestParam Long productId) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		
		  logger.info("Renewing loan for product with ID {} for user: {}", productId,
		  userEmail);
		 

		productService.renewLoan(userEmail, productId);
	}

}