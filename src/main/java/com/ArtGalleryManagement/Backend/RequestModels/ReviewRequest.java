package com.ArtGalleryManagement.Backend.RequestModels;

import lombok.Data;

import java.util.Optional;

@Data
public class ReviewRequest {

	
    private double rating;

    private Long productId;

    private Optional<String> reviewDescription;
    
    

	public ReviewRequest(double rating, Long productId, Optional<String> reviewDescription) {
		super();
		this.rating = rating;
		this.productId = productId;
		this.reviewDescription = reviewDescription;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Optional<String> getReviewDescription() {
		return reviewDescription;
	}

	public void setReviewDescription(Optional<String> reviewDescription) {
		this.reviewDescription = reviewDescription;
	}
    
    
}
