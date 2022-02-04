package com.bookstore.bookstore.services;

import com.bookstore.bookstore.exceptions.StockNotFoundException;
import com.bookstore.bookstore.interfaces.IOrder;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.OrderStatus;
import com.bookstore.bookstore.repository.OrderRepository;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;


@Service
public class OrderService implements IOrder {

    @Autowired
    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrdersForClient(Integer id) {
        orderRepository.setCollectionName("client." + id);
        return orderRepository.findAll();
    }

    @Override
    public Order createNewOrder(List<Book> items, Integer id) {
        orderRepository.setCollectionName("client." + id);

        for (Book book : items) {
            JSONObject bookObject = new JSONObject(new Gson().toJson(getBookInformation(URI.create("http://localhost:8080/api/bookcollection/book/" + book.getIsbn()))));
            int currentBookStock = bookObject.getInt("stock");
            if (currentBookStock >= book.getQuantity()) {
                int newStock = currentBookStock - book.getQuantity();
                updateStock(newStock, bookObject, URI.create("http://localhost:8080/api/bookcollection/book/" + book.getIsbn()));
            } else {
                throw new StockNotFoundException();
            }
        }
        Order order = Order.builder().orderStatus(OrderStatus.INITIALISED).items(items).build();
        return orderRepository.save(order);
    }

    private void updateStock(int updatedStock, JSONObject book, URI uri) {
        JSONObject createNewBook = new JSONObject();
        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        createNewBook.put("title", book.getString("title"));
        createNewBook.put("publisher", book.getString("publisher"));
        createNewBook.put("year", book.getInt("year"));
        createNewBook.put("genre", book.getString("genre"));
        createNewBook.put("price", book.getDouble("price"));
        createNewBook.put("stock", updatedStock);

        HttpEntity<String> httpEntity = new HttpEntity<>(createNewBook.toString(), responseHeaders);
        restTemplate.put(uri, httpEntity);
    }

    private Object getBookInformation(URI uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Object.class).getBody();
    }
}


