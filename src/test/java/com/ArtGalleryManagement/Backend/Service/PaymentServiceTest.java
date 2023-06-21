package com.ArtGalleryManagement.Backend.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ArtGalleryManagement.Backend.Entity.Payment;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.NoPaymentInfoFoundException;
import com.ArtGalleryManagement.Backend.Repository.PaymentRepository;
import com.ArtGalleryManagement.Backend.RequestModels.PaymentInfoRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.net.RequestOptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        Stripe.apiKey = "sk_test_51NA9sdSJVgLExqPUoc8lADLutsRszMnxvKBtLUUfAD1hinnQpWce3LoIyTxnryfB2cDox5UBVVfyMz9xyQReTvVl00ysAONpFI";
    }

    @Test
    void createPaymentIntentTest() throws StripeException {
        // Arrange
        PaymentInfoRequest paymentInfoRequest = new PaymentInfoRequest();
        paymentInfoRequest.setAmount(1000);
        paymentInfoRequest.setCurrency("USD");

        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount((long) paymentInfoRequest.getAmount())
                .setCurrency(paymentInfoRequest.getCurrency())
                .addPaymentMethodType("card")
                .build();

        // Act
        PaymentIntent paymentIntent = PaymentIntent.create(createParams);

        // Assert
        assertNotNull(paymentIntent);
        assertNotNull(paymentIntent.getId());
    }

    @Test
    void stripePaymentTest_paymentInfoFound() throws Exception {
        // Arrange
        String userEmail = "test@example.com";
        Payment paymentMock = mock(Payment.class);
        when(paymentRepository.findByUserEmail(userEmail)).thenReturn(paymentMock);

        // Mock the saving of payment and return the paymentMock
        when(paymentRepository.save(paymentMock)).thenReturn(paymentMock);

        // Act
        ResponseEntity<String> response = paymentService.stripePayment(userEmail);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment done successfully", response.getBody());
        verify(paymentMock, times(1)).setAmount(0.0);
        verify(paymentRepository, times(1)).save(paymentMock);

    }

    @Test
    void stripePaymentTest_paymentInfoNotFound() {
        // Arrange
        String userEmail = "test@example.com";
        when(paymentRepository.findByUserEmail(userEmail)).thenReturn(null);

        // Act and Assert
        assertThrows(NoPaymentInfoFoundException.class, () -> paymentService.stripePayment(userEmail));
    }
}