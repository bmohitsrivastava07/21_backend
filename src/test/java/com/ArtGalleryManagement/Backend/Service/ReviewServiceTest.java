package com.ArtGalleryManagement.Backend.Service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ArtGalleryManagement.Backend.Entity.Review;
import com.ArtGalleryManagement.Backend.Repository.ReviewRepository;
import com.ArtGalleryManagement.Backend.Service.ReviewServiceImpl;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @InjectMocks
    ReviewServiceImpl reviewServiceImpl;

    @Test
    void userReviewListed_ShouldReturnTrueWhenReviewExists() {
        // Arrange
        String userEmail = "arsh@gmail.com";
        Long productId = 1L;
        when(reviewRepository.findByUserEmailAndProductId(userEmail, productId)).thenReturn(new Review());

        // Act
        boolean result = reviewServiceImpl.userReviewListed(userEmail, productId);

        // Assert
        assertTrue(result);
    }
}