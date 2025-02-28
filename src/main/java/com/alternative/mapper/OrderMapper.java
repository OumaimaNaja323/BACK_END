package com.alternative.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.alternative.dto.request.*;
import com.alternative.entities.Order;
import com.alternative.entities.OrderItem;
@Component
public class OrderMapper {
	@Autowired
    @Lazy
    private   UserMapper userMapper ;
	@Autowired
    private   OrderItemMapper orderItemMapper;
	@Autowired
    private   ProductMapper productMapper;
	
	public OrderDTO toDTOonlyOrderItems(Order order) {
		 if (order == null) return null;
		 OrderDTO dto = new OrderDTO();
	        dto.setId(order.getId());
	        dto.setOrderDate(order.getOrderDate());
	        dto.setStatus(order.getStatus());
	        dto.setTotalAmount(order.getTotalAmount());
	        if (order.getOrderItems() != null) {
	       
	        	dto.setOrderItems(order.getOrderItems().stream()
	                    .map(i -> orderItemMapper.toDTO(i))
	                    .collect(Collectors.toList()));
	}
	      return dto;  
	}
	public OrderDTO toDTOonlyOrderItemsAndClient(Order order) {
		 if (order == null) return null;
		 OrderDTO dto = new OrderDTO();
	        dto.setId(order.getId());
	        dto.setOrderDate(order.getOrderDate());
	        dto.setStatus(order.getStatus());
	        dto.setTotalAmount(order.getTotalAmount());
	        if (order.getClient() != null) {
	            dto.setClient(userMapper.toDTOOnly(order.getClient()));
	        }
	        if (order.getOrderItems() != null) {
	       
	        	dto.setOrderItems(order.getOrderItems().stream()
	                    .map(i -> orderItemMapper.toDTO(i))
	                    .collect(Collectors.toList()));
	}
	      return dto;  
	}
    public OrderDTO toDTO(Order order) {
        if (order == null) return null;
        
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        
        // Handle client
        if (order.getClient() != null) {
            dto.setClient(userMapper.toDTOOnly(order.getClient()));
        }
        
        // Handle user (producer)
//        if (order.getUser() != null) {
//            dto.setUser(userMapper.toDTOOnly(order.getUser()));
//        }
        
        // Handle order items
        if (order.getOrderItems() != null) {
        	
        	
        	dto.setOrderItems(order.getOrderItems().stream()
                    .map(i -> orderItemMapper.toDTO(i))
                    .collect(Collectors.toList()));
        
      
        }
       
        return dto;
    }
    
    public Order toEntity(OrderDTO dto) {
        if (dto == null) return null;
        
        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(dto.getStatus());
        order.setTotalAmount(dto.getTotalAmount());
        
        if (dto.getClient() != null) {
            order.setClient(userMapper.toEntityWithoutOrders(dto.getClient()));
        }
        
//        if (dto.getUser() != null) {
//            order.setUser(userMapper.toEntityWithoutOrders(dto.getUser()));
//        }
        
        if (dto.getOrderItems() != null) {
            order.setOrderItems(dto.getOrderItems().stream()
                .map(orderItemMapper::toEntity)
                .collect(Collectors.toList()));
        }
        
        return order;
    }
}
