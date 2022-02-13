package com.mkyong.reactive.model;

import lombok.Data;

@Data
public class Comment {

    private String author;
    private String message;
    private String timestamp;

    public Comment() {
    	super();
    }
    public Comment(String author, String message, String timestamp) {
    	this();
        this.author = author;
        this.message = message;
        this.timestamp = timestamp;
    }
}
