package com.mnyshenko.taxiSpringApp.dao;

import com.mnyshenko.taxiSpringApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
