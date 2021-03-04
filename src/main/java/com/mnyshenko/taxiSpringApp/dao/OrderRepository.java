package com.mnyshenko.taxiSpringApp.dao;

import com.mnyshenko.taxiSpringApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findTop5ByUserIdOrderByDateDesc(Long id);

    List<Order> findAllByUserId(Long id);

    List<Order> findAllByCarId(Long id);

}
