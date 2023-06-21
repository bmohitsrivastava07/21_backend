package com.ArtGalleryManagement.Backend.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ArtGalleryManagement.Backend.Entity.Checkout;
import com.ArtGalleryManagement.Backend.Entity.Product;
import com.ArtGalleryManagement.Backend.Repository.CheckoutRepository;
import com.ArtGalleryManagement.Backend.Repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    CheckoutRepository checkoutRepository;

    ProductServiceImpl productServiceImpl;

    @BeforeEach
    void setUp() {
        this.productServiceImpl = new ProductServiceImpl(productRepository, checkoutRepository, null, null);
    }

    @Test
    void currentLoansCountTest() {
        // Mocking the checkoutRepository behavior
        List<Checkout> checkoutList = new ArrayList<>();
        checkoutList.add(new Checkout());
        when(checkoutRepository.findProductsByUserEmail(any())).thenReturn(checkoutList);

        int loansCount = productServiceImpl.currentLoansCount("mohit@gmail.com");
        assertEquals(1, loansCount);
    }

    @Test
    void checkoutProductByUser() {
        // Mocking the checkoutRepository behavior
        Checkout checkout = new Checkout();
        when(checkoutRepository.findByUserEmailAndProductId(any(), any())).thenReturn(checkout);

        boolean result = productServiceImpl.checkoutProductByUser("mohit@gmail.com", 3L);
        assertTrue(result);
    }
}