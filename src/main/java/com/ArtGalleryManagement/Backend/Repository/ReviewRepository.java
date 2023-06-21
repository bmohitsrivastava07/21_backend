package com.ArtGalleryManagement.Backend.Repository;

import com.ArtGalleryManagement.Backend.Entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByProductId(@RequestParam("product_id") Long productId,
                              Pageable pageable);

    Review findByUserEmailAndProductId(String userEmail, Long productId);

    @Modifying
    @Query("delete from Review where product_id in :product_id")
    void deleteAllByProductId(@Param("product_id") Long productId);
    
    
}
