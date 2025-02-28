package com.alternative.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alternative.dto.request.PaimentDTO;
import com.alternative.entities.Payment;

@Component
public class PaimentMapper {
	@Autowired
	private OrderMapper orderMapper;
	
	
	public PaimentDTO toDTO(Payment payment) {
		PaimentDTO paiment = new PaimentDTO();
		paiment.setPaymentMethod(payment.getPaymentMethod());
		paiment.setStatus(payment.getStatus());
		paiment.setPaymentDate(payment.getPaymentDate());
		paiment.setAmount(payment.getAmount());
		
		if(payment.getOrder() != null) {
			paiment.setOrder(orderMapper.toDTOonlyOrderItems(payment.getOrder()));
		}
		return paiment;
		
	}
	public Payment toEntity(PaimentDTO paiment) {
		Payment payment = new Payment();
		payment.setPaymentDate(paiment.getPaymentDate());
		payment.setStatus(paiment.getStatus());
		payment.setPaymentMethod(paiment.getPaymentMethod());
		payment.setAmount(paiment.getAmount());
		if(paiment.getOrder() != null) {
			payment.setOrder(orderMapper.toEntity(paiment.getOrder()));
		}
		
		
		return payment;
	}

	
}
