package com.ArtGalleryManagement.Backend.Service;

import com.ArtGalleryManagement.Backend.Entity.*;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.NoProductFoundException;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.OutOfStockException;
import com.ArtGalleryManagement.Backend.Repository.*;
import com.ArtGalleryManagement.Backend.RequestModels.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;
    private CheckoutRepository checkoutRepository;

    @Autowired
    public AdminServiceImpl (ProductRepository productRepository,
                         ReviewRepository reviewRepository,
                         CheckoutRepository checkoutRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public void increaseProductQuantity(Long productId) throws Exception {

        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent()) {
           throw new NoProductFoundException();
        }

        product.get().setQuantityAvailable(product.get().getQuantityAvailable() + 1);
        product.get().setQuantities(product.get().getQuantities() + 1);

        productRepository.save(product.get());
    }

    public void decreaseProductQuantity(Long productId) throws Exception {

        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent() ) {
        	throw new NoProductFoundException();
        }
        if(product.get().getQuantityAvailable() <= 0 || product.get().getQuantities() <= 0) {
        
            throw new OutOfStockException();
        }

        product.get().setQuantityAvailable(product.get().getQuantityAvailable() - 1);
        product.get().setQuantities(product.get().getQuantities() - 1);

        productRepository.save(product.get());
    }

    public void postProduct(AddProductRequest addProductRequest) {
    	Product product = new Product();
        product.setTitle(addProductRequest.getTitle());
        product.setArtist(addProductRequest.getArtist());
        product.setProductDescription(addProductRequest.getProductDescription());
        product.setQuantities(addProductRequest.getQuantities());
        product.setQuantityAvailable(addProductRequest.getQuantities());
        product.setCategory(addProductRequest.getCategory());
        product.setImage(addProductRequest.getImage());
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) throws Exception {

        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent()) {
            throw new NoProductFoundException();
        }

        productRepository.delete(product.get());
        checkoutRepository.deleteAllByProductId(productId);
        reviewRepository.deleteAllByProductId(productId);
    }
}
