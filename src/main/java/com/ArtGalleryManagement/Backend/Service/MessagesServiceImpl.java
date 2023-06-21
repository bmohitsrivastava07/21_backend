package com.ArtGalleryManagement.Backend.Service;

import com.ArtGalleryManagement.Backend.Repository.*;
import com.ArtGalleryManagement.Backend.RequestModels.*;
import com.ArtGalleryManagement.Backend.Entity.*;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.NoMessageFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MessagesServiceImpl implements MessageService{

    private MessageRepository messageRepository;

    @Autowired
    public MessagesServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getMessageId());
        if (!message.isPresent()) {
            throw new NoMessageFoundException();
        }

        message.get().setAdminEmail(userEmail);
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setClosed(true);
        messageRepository.save(message.get());
    }

}
