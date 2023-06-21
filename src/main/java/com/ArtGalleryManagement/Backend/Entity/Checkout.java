package com.ArtGalleryManagement.Backend.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "checkout")
@Data
public class Checkout {

    public Checkout() {}

    public Checkout(String userEmail, String checkoutDate, String returnDate, Long productId) {
        this.userEmail = userEmail;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
        this.productId = productId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkout_id")
    private Long checkoutId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "checkout_date")
    private String checkoutDate;

    @Column(name = "return_date")
    private String returnDate;

    @Column(name = "product_id")
    private Long productId;

	public Long getCheckOutId() {
		return checkoutId;
	}

	public void setCheckOutId(Long checkoutId) {
		this.checkoutId = checkoutId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
    
    
}
