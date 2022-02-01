package com.bookstore.bookstore.repository;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private String collectionName;

    @Override
    public String getCollectionName() {
        return collectionName;
    }

    @Override
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}
