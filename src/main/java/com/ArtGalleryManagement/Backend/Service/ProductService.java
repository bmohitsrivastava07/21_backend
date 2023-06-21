package com.ArtGalleryManagement.Backend.Service;

import java.util.List;

import com.ArtGalleryManagement.Backend.Entity.Checkout;
import com.ArtGalleryManagement.Backend.Entity.Product;
import com.ArtGalleryManagement.Backend.ResponseModels.ShelfCurrentLoansResponse;

public interface ProductService {
	public Product checkoutProduct (String userEmail, Long productId) throws Exception ;
	public void returnProduct (String userEmail, Long productId) throws Exception;
	 public void renewLoan(String userEmail, Long productId) throws Exception ;
	 public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception;
	 public int currentLoansCount(String userEmail);
	 public Boolean checkoutProductByUser(String userEmail, Long productId) ;
	
}
