package com.bookstore.bookstore.services;

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
import java.net.URISyntaxException;
import java.util.List;


@Service
public class OrderService implements IOrder {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public List<Order> getAllOrdersForClientId(Long clientId) {
        setCollectionName(clientId.toString());
        checkIfCollectionExists();
        return orderRepository.findAll();
    }

    @Override
    public Order createOrder(List<Book> items, Long clientId) {

        setCollectionName(clientId.toString());

        for (Book book : items) {
//            URI bookUri = createURI(String.format("http://localhost:8080/api/bookcollection/book/%s", book.getIsbn()));
//            Object bookFromRequest = getBook(bookUri, token);

            // serialize data into JSON
//            JSONObject bookJson = putIntoJSON(bookFromRequest);

            // check quantity
            int availableStock = 3;//bookJson.getInt("stock");

            if (availableStock >= book.getQuantity()) {
                // new stock
                int newStock = availableStock - book.getQuantity();
//                changeStockForBook(newStock, bookJson, bookUri, token);
            }
        }

        Order order = Order.builder()
                .orderStatus(OrderStatus.PENDING)
                .items(items)
                .build();

        return orderRepository.save(order);
    }

    private void setCollectionName(String clientId) {
        orderRepository.setCollectionName(String.format("client.%s", clientId));
    }


    private void checkIfCollectionExists() {
        if (!mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            orderRepository.getCollectionName()
                    .substring(orderRepository.getCollectionName().lastIndexOf(".") + 1);
        }
    }


    private URI createURI(String uriAsString) {
        try {
            return new URI(uriAsString);
        } catch (URISyntaxException exception) {
            throw new RuntimeException("Invalid URI");
        }
    }

    private Object getBook(URI uri, String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.substring(7));

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Object> book = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Object.class);

        return book.getBody();
    }


    private JSONObject putIntoJSON(Object object) {
        return new JSONObject(new Gson().toJson(object));
    }


    private void changeStockForBook(int newStock, JSONObject bookJson, URI uri, String token) {
        HttpHeaders headers = new HttpHeaders();
        JSONObject newBookJson = new JSONObject();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.substring(7));

        newBookJson.put("title", bookJson.getString("title"));
        newBookJson.put("publisher", bookJson.getString("publisher"));
        newBookJson.put("year", bookJson.getInt("year"));
        newBookJson.put("genre", bookJson.getString("genre"));
        newBookJson.put("price", bookJson.getDouble("price"));
        newBookJson.put("stock", newStock);

        // edit book with new available stock
        HttpEntity<String> httpEntity = new HttpEntity<>(newBookJson.toString(), headers);

        restTemplate.put(uri, httpEntity);
    }

}


