package com.ArtGalleryManagement.Backend.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ArtGalleryManagement.Backend.Entity.Message;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.NoMessageFoundException;
import com.ArtGalleryManagement.Backend.Repository.MessageRepository;
import com.ArtGalleryManagement.Backend.RequestModels.AdminQuestionRequest;
import com.ArtGalleryManagement.Backend.Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessagesServiceImpl messageService;

    @BeforeEach
    void setUp() {
        // Set up any necessary initialization
    }

    @Test
    void postMessageTest() {
        // Arrange
        Message messageRequest = new Message();
        messageRequest.setTitle("Test Title");
        messageRequest.setQuestion("Test Question");
        String userEmail = "test@example.com";

        // Act
        messageService.postMessage(messageRequest, userEmail);

        // Assert
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void putMessageTest_messageFound() throws Exception {
        // Arrange
        Long messageId = 1L;
        AdminQuestionRequest adminQuestionRequest = new AdminQuestionRequest();
        adminQuestionRequest.setMessageId(messageId);
        adminQuestionRequest.setResponse("Test Response");
        String userEmail = "admin@example.com";

        Message existingMessage = new Message();
        existingMessage.setMessageId(messageId);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(existingMessage));

        // Act
        messageService.putMessage(adminQuestionRequest, userEmail);

        // Assert
        verify(messageRepository, times(1)).findById(messageId);
        verify(messageRepository, times(1)).save(existingMessage);
        assertEquals(userEmail, existingMessage.getAdminEmail());
        assertEquals(adminQuestionRequest.getResponse(), existingMessage.getResponse());
        assertTrue(existingMessage.isClosed());
    }

    @Test
    void putMessageTest_messageNotFound() {
        // Arrange
        Long messageId = 1L;
        AdminQuestionRequest adminQuestionRequest = new AdminQuestionRequest();
        adminQuestionRequest.setMessageId(messageId);
        adminQuestionRequest.setResponse("Test Response");
        String userEmail = "admin@example.com";

        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoMessageFoundException.class, () -> messageService.putMessage(adminQuestionRequest, userEmail));
        verify(messageRepository, times(1)).findById(messageId);
        verify(messageRepository, never()).save(any(Message.class));
    }
}