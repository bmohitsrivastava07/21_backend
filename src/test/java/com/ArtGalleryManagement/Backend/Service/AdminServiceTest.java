package com.ArtGalleryManagement.Backend.Service;

import com.ArtGalleryManagement.Backend.Entity.Product;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.NoProductFoundException;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.OutOfStockException;
import com.ArtGalleryManagement.Backend.Repository.CheckoutRepository;
import com.ArtGalleryManagement.Backend.Repository.ProductRepository;
import com.ArtGalleryManagement.Backend.Repository.ReviewRepository;
import com.ArtGalleryManagement.Backend.RequestModels.AddProductRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CheckoutRepository checkoutRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        // Set up any necessary initialization
    }

    @Test
    void increaseProductQuantityTest_productFound() throws Exception {
        // Arrange
        Long productId = 1L;
        int initialQuantity = 5;

        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        existingProduct.setQuantities(initialQuantity);
        existingProduct.setQuantityAvailable(initialQuantity);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Act
        adminService.increaseProductQuantity(productId);

        // Assert
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
        assertEquals(initialQuantity + 1, existingProduct.getQuantities());
        assertEquals(initialQuantity + 1, existingProduct.getQuantityAvailable());
    }

    @Test
    void increaseProductQuantityTest_productNotFound() {
        // Arrange
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoProductFoundException.class, () -> adminService.increaseProductQuantity(productId));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void decreaseProductQuantityTest_productFound() throws Exception {
        // Arrange
        Long productId = 1L;
        int initialQuantity = 5;

        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        existingProduct.setQuantities(initialQuantity);
        existingProduct.setQuantityAvailable(initialQuantity);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Act
        adminService.decreaseProductQuantity(productId);

        // Assert
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
        assertEquals(initialQuantity - 1, existingProduct.getQuantities());
        assertEquals(initialQuantity - 1, existingProduct.getQuantityAvailable());
    }

    @Test
    void decreaseProductQuantityTest_productNotFound() {
        // Arrange
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoProductFoundException.class, () -> adminService.decreaseProductQuantity(productId));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

        // ...

    @Test
    void decreaseProductQuantityTest_productOutOfStock() {
        // Arrange
        Long productId = 1L;
        int initialQuantity = 0;

        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        existingProduct.setQuantities(initialQuantity);
        existingProduct.setQuantityAvailable(initialQuantity);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Act and Assert
        assertThrows(OutOfStockException.class, () -> adminService.decreaseProductQuantity(productId));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void postProductTest() {
        // Arrange
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setTitle("Test Product");
        addProductRequest.setArtist("Test Artist");
        addProductRequest.setProductDescription("Test Description");
        addProductRequest.setQuantities(10);
        addProductRequest.setCategory("Test Category");
        addProductRequest.setImage("test-image.jpg");

        // Act
        adminService.postProduct(addProductRequest);

        // Assert
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteProductTest_productFound() throws Exception {
        // Arrange
        Long productId = 1L;

        Product existingProduct = new Product();
        existingProduct.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Act
        adminService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(existingProduct);
        verify(checkoutRepository, times(1)).deleteAllByProductId(productId);
        verify(reviewRepository, times(1)).deleteAllByProductId(productId);
    }

    @Test
    void deleteProductTest_productNotFound() {
        // Arrange
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoProductFoundException.class, () -> adminService.deleteProduct(productId));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).delete(any(Product.class));
        verify(checkoutRepository, never()).deleteAllByProductId(productId);
        verify(reviewRepository, never()).deleteAllByProductId(productId);
    }
}