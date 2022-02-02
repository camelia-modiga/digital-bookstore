package com.bookstore.bookstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Document(collection = "#{@orderRepository.getCollectionName()}")
public class Order {

    @Id
    private String orderId;
    private OrderStatus orderStatus;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy - hh:mm:ssa")
    private LocalDateTime date=LocalDateTime.now();;
    @Field(name = "books")
    private List<Book> items;

    public String getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<Book> getItems() {
        return items;
    }
}
