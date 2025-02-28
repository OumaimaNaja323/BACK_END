package com.alternative.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alternative.dto.request.PaimentDTO;
import com.alternative.entities.Order;
import com.alternative.entities.Payment;
import com.alternative.mapper.PaimentMapper;
import com.alternative.repositories.OrderRepository;
import com.alternative.repositories.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaimentMapper paimentMapper;
	
	@Transactional
	public PaimentDTO payer(PaimentDTO paimentDTO) {
		Order order = orderRepository.findById(paimentDTO.getOrderId()).orElse(null);
		
		order.setStatus("Paied");
		orderRepository.save(order);

		
		Payment payment = paimentMapper.toEntity(paimentDTO);
		payment.setOrder(order);
		paymentRepository.save(payment);
		
		return paimentMapper.toDTO(payment);
		
		
	}
	
}
