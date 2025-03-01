package com.alternative.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alternative.entities.Payment;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
