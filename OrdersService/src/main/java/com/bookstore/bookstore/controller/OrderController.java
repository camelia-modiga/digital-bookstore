package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@Tag(name = "Orders", description = "The orders API")

public class OrderController {

    @Autowired
    private OrderService orderService;
    private HttpServletRequest request;

    @RequestMapping(value="api/bookorder/orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getAllOrdersForClientId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getAllOrdersForClientId(id));
    }

    @RequestMapping(value="api/bookorder/order/{id}", method = RequestMethod.POST)
    public ResponseEntity<Order> createOrder(
            @PathVariable("id") Long id,
            @RequestBody List<Book> items) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(items, id));
    }

}
