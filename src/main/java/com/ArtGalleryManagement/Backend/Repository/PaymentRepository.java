package com.ArtGalleryManagement.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ArtGalleryManagement.Backend.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
	
	Payment findByUserEmail(String userEmail);
	
}
