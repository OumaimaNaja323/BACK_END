package com.alternative.dto.request;

import java.util.Date;

import com.alternative.entities.Product;
import com.alternative.entities.User;

public class ReviewDTO {
	private Long id;
	private ProductDTO product;
	private Long productId;
	private UserDTO client;
	private Long userId;
    private int rating;
    private String comment;
    private Date reviewDate;
    
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	public UserDTO getClient() {
		return client;
	}
	public void setClient(UserDTO client) {
		this.client = client;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    
}
