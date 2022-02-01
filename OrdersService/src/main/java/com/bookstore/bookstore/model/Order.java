package com.bookstore.bookstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@Document(collection = "#{@orderRepository.getCollectionName()}")
public class Order {

    @Id
    private String orderId;

    @NotNull(message = "Order's status can not be null")
    private OrderStatus orderStatus;

    @NotNull(message = "Order's date can not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy - hh:mm:ssa")
    private LocalDateTime date;

    @Field(name = "books")
    private List<Book> items;
}
