package com.bookstore.bookstore.model;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    INITIALISED,
    ACTIVE,
    FINISHED
}
