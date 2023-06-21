package com.ArtGalleryManagement.Backend.RequestModels;

import lombok.Data;

@Data
public class AddProductRequest {
	 private String title;
	private String artist;

    private String productDescription;

    private int quantities;

    private String category;

    private String image;


    public AddProductRequest(String title, String artist, String productDescription, int quantities, String category,
			String image) {
		super();
		this.title = title;
		this.artist = artist;
		this.productDescription = productDescription;
		this.quantities = quantities;
		this.category = category;
		this.image = image;
	}

	public AddProductRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public int getQuantities() {
		return quantities;
	}

	public void setQuantities(int quantities) {
		this.quantities = quantities;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
