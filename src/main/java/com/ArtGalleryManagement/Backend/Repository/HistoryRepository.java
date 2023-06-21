package com.ArtGalleryManagement.Backend.Repository;

import com.ArtGalleryManagement.Backend.Entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public interface HistoryRepository extends JpaRepository<History, Long> {
    Page<History> findProductsByUserEmail(@RequestParam("email") String userEmail, Pageable pageable);
    
}
