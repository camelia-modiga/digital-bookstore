package com.bookstore.bookstore.interfaces;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;

import java.util.List;

public interface IOrder {

    List<Order> getAllOrdersForClientId(Long clientId);

    Order createOrder(List<Book> items, Long clientId);
}
