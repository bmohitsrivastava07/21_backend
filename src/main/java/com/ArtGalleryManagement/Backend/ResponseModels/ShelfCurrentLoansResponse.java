package com.ArtGalleryManagement.Backend.ResponseModels;

import com.ArtGalleryManagement.Backend.Entity.*;
import lombok.Data;

@Data
public class ShelfCurrentLoansResponse {

    public ShelfCurrentLoansResponse(Product product, int daysLeft) {
        this.product = product;
        this.daysLeft = daysLeft;
    }

    private Product product;

    private int daysLeft;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getDaysLeft() {
		return daysLeft;
	}

	public void setDaysLeft(int daysLeft) {
		this.daysLeft = daysLeft;
	}
    
}
