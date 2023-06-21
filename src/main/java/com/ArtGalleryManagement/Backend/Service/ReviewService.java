package com.ArtGalleryManagement.Backend.Service;

import com.ArtGalleryManagement.Backend.RequestModels.ReviewRequest;

public interface ReviewService {
	public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception;
	public Boolean userReviewListed(String userEmail, Long bookId);
}
