package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.OrderStatus;
import com.bookstore.bookstore.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@RestController
@Tag(name = "Orders", description = "The orders API")
@RequestMapping(value = "/api/bookcollection", produces = {MediaType.APPLICATION_JSON_VALUE})
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<?> getOrders() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET).body(
                        orderService.getOrders());
    }

    @GetMapping(value = "/orders/{clientId}")
    public EntityModel<Order> getOrdersByClientId(@PathVariable(name = "clientId") String clientId) {
        return orderService.getOrdersByClientId(clientId);
    }


    @PostMapping(value = "/addOrder/{clientId}")
    public void addOrder(@RequestBody Order order,
                         @PathVariable(name = "clientId") String clientId) {
        orderService.addOrder(order, clientId);
    }


    @PostMapping(value = "/addBook/{clientId}")
    public String addBookByClientId(@RequestBody Book book,
                                    @PathVariable(name = "clientId") String clientId) {

        RestTemplate restTemplate = new RestTemplate();

        JSONObject bookJSONObject = new JSONObject();
        bookJSONObject.put(book.getIsbn(), book.getQuantity());

        String endpointPath = "http://localhost:8080/api/bookcollection/bookCheckStock";

        String resultBookQuantity = restTemplate.postForObject(endpointPath, bookJSONObject.toString(), String.class);

        bookJSONObject = orderService.addBookByClientId(book, clientId, resultBookQuantity);

        return bookJSONObject.toString();
    }


    @PostMapping(value = "/finishOrder/{clientId}")
    public ResponseEntity<String> finishOrderByClientId(@PathVariable(name = "clientId") String clientId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;
        String endpointPath = "http://localhost:8080/api/bookcollection/checkFinishOrder";

        JSONObject bookJSONList = orderService.getOrderInPendingByClientId(clientId);

        try {
            responseEntity = restTemplate.postForEntity(endpointPath, bookJSONList.toString(), String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                orderService.changeOrderStatusToFinished(clientId);
            }
            return responseEntity;
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<String>(
                    e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST
            );
        }
    }


    @PutMapping(value = "/changeOrderStatus", params = {"clientId", "orderId", "orderStatus"})
    public void changeOrderStatus(@RequestParam(name = "clientId") String clientId,
                                  @RequestParam(name = "orderId") String orderId,
                                  @RequestParam(name = "orderStatus") OrderStatus orderStatus) {
        orderService.changeOrderStatus(clientId, orderId, orderStatus);
    }


    @DeleteMapping(value = "/deleteOrdersByClientId/{clientId}")
    public ResponseEntity<?> deleteOrdersByClientId(@PathVariable(name = "clientId") String clientId) {
        return orderService.deleteOrdersByClientId(clientId);
    }

    @DeleteMapping(value = "/deleteOrderByOrderId", params = {"clientId", "orderId"})
    public ResponseEntity<?> deleteOrdersByOrderId(@RequestParam(name = "clientId") String clientId,
                                                   @RequestParam(name = "orderId") String orderId) {
        return orderService.deleteOrdersByOrderId(clientId, orderId);
    }
}