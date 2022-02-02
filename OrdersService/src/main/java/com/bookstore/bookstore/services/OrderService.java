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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;


@Service
public class OrderService implements IOrder {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final MongoTemplate mongoTemplate;

    private final RestTemplate restTemplate = new RestTemplate();

    public OrderService(OrderRepository orderRepository,MongoTemplate mongoTemplate){
        this.orderRepository=orderRepository;
        this.mongoTemplate=mongoTemplate;
    }

    @Override
    public List<Order> getAllOrdersForClient(Long clientId) {

        orderRepository.setCollectionName("client."+clientId);

        if (!mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            orderRepository.getCollectionName()
                    .substring(orderRepository.getCollectionName().lastIndexOf(".") + 1);
        }

        return orderRepository.findAll();
    }

    @Override
    public Order createNewOrder(List<Book> items, Long clientId) {

        orderRepository.setCollectionName("client."+clientId);

        for (Book book : items) {

            URI bookUri = URI.create("http://localhost:8080/api/bookcollection/book/"+book.getIsbn());

            JSONObject bookObject = new JSONObject(new Gson().toJson(getBookInformation(bookUri)));

            int currentBookStock = bookObject.getInt("stock");

            if (currentBookStock >= book.getQuantity()) {
                int newStock = currentBookStock - book.getQuantity();
                updateStock(newStock, bookObject, bookUri);
            }
            else{
                throw new StockNotFoundException();
            }
        }

        Order order = Order.builder().orderStatus(OrderStatus.PENDING).items(items).build();

        return orderRepository.save(order);
    }


    private Object getBookInformation(URI uri) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(responseHeaders);
        ResponseEntity<Object> book = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Object.class);

        return book.getBody();
    }


    private void updateStock(int newStock, JSONObject bookJson, URI uri) {

        HttpHeaders responseHeaders = new HttpHeaders();
        JSONObject newBookJson = new JSONObject();

        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        newBookJson.put("title", bookJson.getString("title"));
        newBookJson.put("publisher", bookJson.getString("publisher"));
        newBookJson.put("year", bookJson.getInt("year"));
        newBookJson.put("genre", bookJson.getString("genre"));
        newBookJson.put("price", bookJson.getDouble("price"));
        newBookJson.put("stock", newStock);

        HttpEntity<String> httpEntity = new HttpEntity<>(newBookJson.toString(), responseHeaders);

        restTemplate.put(uri, httpEntity);
    }

}


