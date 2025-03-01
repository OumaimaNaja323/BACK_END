package com.alternative.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alternative.entities.Order;
import com.alternative.entities.OrderItem;
import com.alternative.entities.User;



@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByClient(User client);

	
	
}
