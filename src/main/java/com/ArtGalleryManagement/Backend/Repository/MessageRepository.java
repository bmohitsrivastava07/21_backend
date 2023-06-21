package com.ArtGalleryManagement.Backend.Repository;

import com.ArtGalleryManagement.Backend.Entity.Message;import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public interface MessageRepository extends JpaRepository<Message, Long> {

	
    Page<Message> findByUserEmail(@RequestParam("user_email") String userEmail, Pageable pageable);

    Page<Message> findByClosed(@RequestParam("closed") boolean closed, Pageable pageable);
}
