package com.alternative.dto.request;

import java.util.Date;

import com.alternative.entities.Order;

public class PaimentDTO {
	
	    private OrderDTO order;
	    private double amount;
	    private Date paymentDate;
	    private String paymentMethod;
	    private String status;
	    private Long orderId;
	    
		
		public OrderDTO getOrder() {
			return order;
		}
		public void setOrder(OrderDTO order) {
			this.order = order;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public Date getPaymentDate() {
			return paymentDate;
		}
		public void setPaymentDate(Date paymentDate) {
			this.paymentDate = paymentDate;
		}
		public String getPaymentMethod() {
			return paymentMethod;
		}
		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Long getOrderId() {
			return orderId;
		}
		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
	    

}
