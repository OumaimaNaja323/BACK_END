package com.alternative.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alternative.dto.request.ReviewDTO;

import com.alternative.entities.Review;
@Component
public class ReviewMapper {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private UserMapper userMapper;
	
	
	public ReviewDTO toDTO(Review review) {
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setComment(review.getComment());
		reviewDTO.setRating(review.getRating());
		reviewDTO.setReviewDate(review.getReviewDate());
		
		if(review.getProduct() != null) {
			reviewDTO.setProduct(productMapper.toDTO(review.getProduct()));
		}

		
		if(review.getClient() != null) {
			reviewDTO.setClient(userMapper.toDTOOnly(review.getClient()));
		}
		return reviewDTO;
		
	}
	public Review toEntity(ReviewDTO reviewDTO) {
		
		Review review = new Review();
		review.setComment(reviewDTO.getComment());
		review.setRating(reviewDTO.getRating());
		review.setReviewDate(reviewDTO.getReviewDate());
		
		if(reviewDTO.getProduct() != null) {
			review.setProduct(productMapper.toEntity(reviewDTO.getProduct()));
		}

		
		if(reviewDTO.getClient() != null) {
			review.setClient(userMapper.toEntity(reviewDTO.getClient()));
		}
		return review;
		
	}
}
