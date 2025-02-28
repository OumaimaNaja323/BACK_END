package com.alternative.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alternative.entities.OrderItem;
import com.alternative.entities.Product;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	 void deleteByProductId(Long productId);
	 List<OrderItem> findByProductIn(List<Product> products);
}
