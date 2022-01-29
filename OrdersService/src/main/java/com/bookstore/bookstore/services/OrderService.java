package com.bookstore.bookstore.services;

import com.bookstore.bookstore.assembler.OrderModelAssembler;
import com.bookstore.bookstore.exceptions.ClientNotFoundException;
import com.bookstore.bookstore.exceptions.OrderNotFoundException;
import com.bookstore.bookstore.interfaces.IOrder;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.OrderStatus;
import com.bookstore.bookstore.repository.OrderRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService implements IOrder {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Autowired
    private final OrderModelAssembler assembler;

    public OrderService(OrderRepository orderRepository,MongoTemplate mongoTemplate,OrderModelAssembler assembler) {
        this.orderRepository=orderRepository;
        this.mongoTemplate=mongoTemplate;
        this.assembler=assembler;

    }
    public ResponseEntity<?> getOrders() {
        List<Order> orderList = orderRepository.findAll();
        return ResponseEntity.ok(assembler.toCollectionModel(orderList));
    }

    public EntityModel<Order> getOrdersByClientId(String clientId) {

        orderRepository.setCollectionName(clientId);

        if (mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            Order orders = orderRepository.findById(clientId).orElseThrow(ClientNotFoundException::new);
            return assembler.toModel(orders);
        } else {
            throw new ClientNotFoundException();
        }
    }

    public ResponseEntity<?> addOrder(Order order, String clientId) {

        orderRepository.setCollectionName(clientId);

        if (!mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            mongoTemplate.createCollection(orderRepository.getCollectionName());
        }

        order.setLocalDate(LocalDate.now());

        EntityModel<Order> entityModel=assembler.toModel(orderRepository.save(order));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    public JSONObject addBookByClientId(Book book, String clientId, String resultBookQuantity) {
        JSONObject bookJSON = new JSONObject(resultBookQuantity);

        Set<String> keys = bookJSON.keySet();
        String bookIsbn = "";
        for (String key : keys) {
            bookIsbn = key;
        }

        if (bookJSON.getInt(bookIsbn) == -1) {
            orderRepository.setCollectionName(clientId);

            if (!mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
                mongoTemplate.createCollection(orderRepository.getCollectionName());
            }

            List<Order> orderList = orderRepository.findAll();
            Order orderPending = null;
            if (!orderList.isEmpty()) {
                for (Order order : orderList) {
                    if (order.getOrderStatus() == OrderStatus.PENDING) {
                        orderPending = order;
                    }
                }
                if (orderPending != null) {
                    boolean existsIsbn = false;
                    for (Book bookFromPending : orderPending.getBookList()) {
                        if (bookFromPending.getIsbn().equals(book.getIsbn())) {
                            bookFromPending.setQuantity(bookFromPending.getQuantity() + book.getQuantity());
                            existsIsbn = true;
                        }
                    }
                    if (existsIsbn) {
                        orderRepository.save(orderPending);
                    } else {
                        orderPending.getBookList().add(book);
                        orderRepository.save(orderPending);
                    }


                } else {
                    List<Book> bookList = new ArrayList<Book>();
                    bookList.add(book);
                    Order newOrder = new Order();
                    newOrder.setLocalDate(LocalDate.now());
                    newOrder.setBookList(bookList);
                    newOrder.setOrderStatus(OrderStatus.PENDING);
                    orderRepository.save(newOrder);

                    return bookJSON;
                }
            } else {
                List<Book> bookList = new ArrayList<Book>();
                bookList.add(book);
                Order newOrder = new Order();
                newOrder.setLocalDate(LocalDate.now());
                newOrder.setBookList(bookList);
                newOrder.setOrderStatus(OrderStatus.PENDING);
                orderRepository.save(newOrder);

                return bookJSON;
            }
        }
        return bookJSON;
    }


    public JSONObject getOrderInPendingByClientId(String clientId) {
        orderRepository.setCollectionName(clientId);

        if (mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            JSONObject bookJSONList = new JSONObject();
            List<Order> bookOrderList = orderRepository.findAll();
            for (Order order : bookOrderList) {
                if (order.getOrderStatus() == OrderStatus.PENDING) {
                    for (Book book : order.getBookList()) {
                        bookJSONList.put(book.getIsbn(), book.getQuantity());
                    }
                }
            }
            return bookJSONList;
        } else {
            throw new IllegalStateException("The collection doesn't exists!");
        }
    }


    public void changeOrderStatusToFinished(String clientId) {
        orderRepository.setCollectionName(clientId);

        if (mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            List<Order> bookOrderList = orderRepository.findAll();
            if (!bookOrderList.isEmpty()) {
                String orderId = bookOrderList.get(0).getId();
                for (Order order : bookOrderList) {
                    if (order.getOrderStatus() == OrderStatus.PENDING) {
                        orderId = order.getId();
                        order.setOrderStatus(OrderStatus.FINISHED);
                    }
                }
                Optional<Order> orderOptional = orderRepository.findById(orderId);
                if (orderOptional.isPresent()) {
                    Order orderInPending = orderOptional.get();
                    orderInPending.setOrderStatus(OrderStatus.FINISHED);
                    orderRepository.save(orderInPending);
                } else {
                    throw new IllegalStateException("The order was not found for changing status to FINISHED!");
                }
            }
        } else {
            throw new IllegalStateException("The collection doesn't exists!");
        }
    }


    public void changeOrderStatus(String clientId, String orderId, OrderStatus orderStatus) {
        orderRepository.setCollectionName(clientId);
        if (mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                order.setOrderStatus(orderStatus);
                orderRepository.save(order);
            } else {
                throw new OrderNotFoundException();
            }

        } else {
            throw new IllegalStateException("Collection does not exists !");
        }
    }


    public ResponseEntity<?> deleteOrdersByClientId(String clientId) {
        orderRepository.setCollectionName(clientId);
        if (!mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            throw new IllegalStateException("Collection does not exists !");
        }
        orderRepository.deleteById(clientId);
        return ResponseEntity.noContent().build();
    }


    public ResponseEntity<?> deleteOrdersByOrderId(String clientId, String orderId) {
        orderRepository.setCollectionName(clientId);
        if (mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            orderRepository.deleteById(orderId);
            return ResponseEntity.noContent().build();
        } else {
            throw new IllegalStateException("Collection does not exists !");
        }
    }
}


