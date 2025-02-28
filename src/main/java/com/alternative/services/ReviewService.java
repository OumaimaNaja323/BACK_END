package com.alternative.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alternative.dto.request.ReviewDTO;
import com.alternative.entities.Product;
import com.alternative.entities.Review;
import com.alternative.entities.User;
import com.alternative.mapper.ReviewMapper;
import com.alternative.repositories.ProductRepository;
import com.alternative.repositories.ReviewRepository;
import com.alternative.repositories.UserRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReviewMapper reviewMapper;
	
	public List<ReviewDTO> listerCommantaire(){
		return reviewRepository.findAll().stream()
				.map(i->reviewMapper.toDTO(i))
				.collect(Collectors.toList());
	}
	public ReviewDTO ajouterCommentaire(ReviewDTO reviewDTO) {
		Product product = productRepository.findById(reviewDTO.getProductId()).orElse(null);
		User client = userRepository.findById(reviewDTO.getUserId()).orElse(null);
		Review review = reviewMapper.toEntity(reviewDTO);
		review.setProduct(product);
		review.setClient(client);
		
		reviewRepository.save(review);
		
		return reviewMapper.toDTO(review);
		
	}
	
	public void supprimmerCommantaire(ReviewDTO reviewDTO) {
		reviewRepository.deleteById(reviewDTO.getId());
	}
	
}
