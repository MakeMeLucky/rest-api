package org.nntu.restapi.dto;

public class BookResponse extends DefaultResponse{

    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookResponse {" +
                "book=" + book +
                '}';
    }
}
