package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String>, OrderRepositoryCollection {

}
