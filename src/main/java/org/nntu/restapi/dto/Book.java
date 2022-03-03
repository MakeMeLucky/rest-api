package org.nntu.restapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Book {

    private String name;
    private String author;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    public Book() {
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Book(String name, String author, String description) {
        this.name = name;
        this.author = author;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book {" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
