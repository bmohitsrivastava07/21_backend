package com.ArtGalleryManagement.Backend.Service;

import com.ArtGalleryManagement.Backend.RequestModels.AddProductRequest;

public interface AdminService {
	 public void increaseProductQuantity(Long productId) throws Exception;
	 public void decreaseProductQuantity(Long productId) throws Exception;
	 public void deleteProduct(Long productId) throws Exception;
	 public void postProduct(AddProductRequest addProductRequest);
}
