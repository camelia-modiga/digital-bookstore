package com.bookstore.bookstore.assembler;

import com.bookstore.bookstore.controller.OrderController;
import com.bookstore.bookstore.model.Order;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>>{
    @Override
    public EntityModel<Order> toModel(Order order) {
        return EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOrdersByClientId(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getOrders()).withRel("authors"));
    }

}

