package com.bookstore.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Document(collection="#{@orderRepository.getCollectionName()}")
public class Order {

    @Id
    private String id;
    private LocalDate localDate;
    private List<Book> bookList;
    private OrderStatus orderStatus;

    public Order() {
    }
}
