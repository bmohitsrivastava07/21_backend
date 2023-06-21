package com.ArtGalleryManagement.Backend.Service;

import com.ArtGalleryManagement.Backend.Repository.*;
import com.ArtGalleryManagement.Backend.ResponseModels.*;
import com.ArtGalleryManagement.Backend.Entity.*;
import com.ArtGalleryManagement.Backend.GlobalExceptionsHandler.NoProductFoundOrNotCheckedException;

import net.bytebuddy.asm.Advice;
import org.springframework.security.core.parameters.P;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    private CheckoutRepository checkoutRepository;

    private HistoryRepository historyRepository;
    private PaymentRepository paymentRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CheckoutRepository checkoutRepository,
                       HistoryRepository historyRepository,PaymentRepository paymentRepository) {
        this.productRepository = productRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
        this.paymentRepository= paymentRepository;
    }

	public Product checkoutProduct (String userEmail, Long productId) throws Exception {

        Optional<Product> product = productRepository.findById(productId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndProductId(userEmail, productId);

        if (!product.isPresent() || validateCheckout != null || product.get().getQuantityAvailable() <= 0) {
            throw new NoProductFoundOrNotCheckedException();
        }

        List<Checkout> currentProductsCheckedOut = checkoutRepository.findProductsByUserEmail(userEmail);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        boolean productNeedsReturned = false;

        for (Checkout checkout: currentProductsCheckedOut) {
            Date d1 = sdf.parse(checkout.getReturnDate());
            Date d2 = sdf.parse(LocalDate.now().toString());

            TimeUnit time = TimeUnit.DAYS;

            double differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

            if (differenceInTime < 0) {
                productNeedsReturned = true;
                break;
            }
        }

		Payment userPayment = paymentRepository.findByUserEmail(userEmail);
		  
		  if ((userPayment != null && userPayment.getAmount() > 0) || (userPayment !=
		  null && productNeedsReturned)) { throw new Exception("Outstanding fees"); }
		  
		  if (userPayment == null) { Payment payment = new Payment();
		  payment.setAmount(0.00); payment.setUserEmail(userEmail);
		  paymentRepository.save(payment); }
		 
        product.get().setQuantityAvailable(product.get().getQuantityAvailable() - 1);
        productRepository.save(product.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(3).toString(),
                product.get().getProductId()
        );

        checkoutRepository.save(checkout);

        return product.get();
    }

    public Boolean checkoutProductByUser(String userEmail, Long productId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndProductId(userEmail, productId);
        if (validateCheckout != null) {
            return true;
        } else {
            return false;
        }
    }

    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findProductsByUserEmail(userEmail).size();
    }

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {

        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.findProductsByUserEmail(userEmail);
        List<Long> productIdList = new ArrayList<>();

        for (Checkout i: checkoutList) {
            productIdList.add(i.getProductId());
        }

        List<Product> products = productRepository.findProductsByProductIds(productIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Product product : products) {
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(x -> x.getProductId() == product.getProductId()).findFirst();

            if (checkout.isPresent()) {

                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;

                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(),
                        TimeUnit.MILLISECONDS);

                shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(product, (int) difference_In_Time));
            }
        }
        return shelfCurrentLoansResponses;
    }

    public void returnProduct (String userEmail, Long productId) throws Exception {

        Optional<Product> product = productRepository.findById(productId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndProductId(userEmail, productId);

        if (!product.isPresent() || validateCheckout == null) {
            throw new NoProductFoundOrNotCheckedException();
        }

        product.get().setQuantityAvailable(product.get().getQuantityAvailable() + 1);

        productRepository.save(product.get());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = sdf.parse(validateCheckout.getReturnDate());
        Date d2 = sdf.parse(LocalDate.now().toString());

        TimeUnit time = TimeUnit.DAYS;

        long differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

        if (differenceInTime < 0) {
            Payment payment = paymentRepository.findByUserEmail(userEmail);

            payment.setAmount(payment.getAmount() + (-differenceInTime));
            paymentRepository.save(payment);
        }

        checkoutRepository.deleteById(validateCheckout.getCheckOutId());

        History history = new History(
                userEmail,
                validateCheckout.getCheckoutDate(),
                LocalDate.now().toString(),
                product.get().getTitle(),
                product.get().getArtist(),
                product.get().getProductDescription(),
                product.get().getImage()
        );

        historyRepository.save(history);
    }

    public void renewLoan(String userEmail, Long productId) throws Exception {

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndProductId(userEmail, productId);

        if (validateCheckout == null) {
            throw new NoProductFoundOrNotCheckedException();
        }

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
        Date d2 = sdFormat.parse(LocalDate.now().toString());

        if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
            validateCheckout.setReturnDate(LocalDate.now().plusDays(3).toString());
            checkoutRepository.save(validateCheckout);
        }
    }

}















