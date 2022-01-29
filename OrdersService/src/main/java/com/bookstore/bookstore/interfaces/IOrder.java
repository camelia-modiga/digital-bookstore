package com.bookstore.bookstore.interfaces;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.OrderStatus;
import org.json.JSONObject;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface IOrder {
    ResponseEntity<?> getOrders();
    EntityModel<Order> getOrdersByClientId(String clientId);
    ResponseEntity<?> addOrder(Order order, String clientId);
    JSONObject addBookByClientId(Book book, String clientId, String resultBookQuantity);
    JSONObject getOrderInPendingByClientId(String clientId);
    void changeOrderStatusToFinished(String clientId);
    void changeOrderStatus(String clientId, String orderId, OrderStatus orderStatus);
    ResponseEntity<?> deleteOrdersByClientId(String clientId);
    ResponseEntity<?> deleteOrdersByOrderId(String clientId, String orderId);
}
