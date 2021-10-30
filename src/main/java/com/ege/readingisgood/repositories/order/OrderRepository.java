package com.ege.readingisgood.repositories.order;

import com.ege.readingisgood.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface OrderRepository extends MongoRepository<Order, Long> , OrderRepositoryCustom{

    Page<Order> getOrderByCustomerId(Long customerId, Pageable pageable);

    List<Order> getOrderByCustomerId(Long customerId);

    Page<Order> getOrderByPurchaseDateBetween(Date startDate, Date endDate , Pageable pageable);

}
