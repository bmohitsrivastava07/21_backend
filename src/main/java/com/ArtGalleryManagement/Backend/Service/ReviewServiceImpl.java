package com.ArtGalleryManagement.Backend.Service;

import com.ArtGalleryManagement.Backend.Repository.*;
import com.ArtGalleryManagement.Backend.RequestModels.*;
import com.ArtGalleryManagement.Backend.Entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.ReviewException;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
        Review validateReview = reviewRepository.findByUserEmailAndProductId(userEmail, reviewRequest.getProductId());
        if (validateReview != null) {
            throw new ReviewException();
        }

        Review review = new Review();
        review.setProductId(reviewRequest.getProductId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if (reviewRequest.getReviewDescription().isPresent()) {
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }

    public Boolean userReviewListed(String userEmail, Long productId) {
        Review validateReview = reviewRepository.findByUserEmailAndProductId(userEmail, productId);
        if (validateReview != null) {
            return true;
        } else {
            return false;
        }
    }

}









