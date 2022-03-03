package org.nntu.restapi.service;

import org.nntu.restapi.dto.Book;
import org.nntu.restapi.dto.BookResponse;
import org.nntu.restapi.dto.ResultCode;
import org.nntu.restapi.persistence.BookDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookService {

    @Autowired
    BookDB bookDB;

    public List<Book> getBookList() throws Exception {
        List<Book> bookList = new ArrayList<>();
        ResultSet resultSet = bookDB.getList();
        while (resultSet.next()) {
            bookList.add(parseBook(resultSet));
        }
        resultSet.close();
        return bookList;
    }

    public BookResponse createBook(Book book) throws Exception {
        BookResponse response = new BookResponse();
        response.setBook(book);
        try {
            if (bookDB.insert(book)) {
                response.setCode(ResultCode.SUCCESS);
            } else {
                response.setCode(ResultCode.EXISTED);
            }
        } catch (SQLException e) {
            response.setCode(ResultCode.FAILED);
            response.setError(e.getSQLState());
        } catch (Exception ex) {
            throw ex;
        }

        return response;
    }

    public BookResponse getBook(String name, String author) throws Exception {
        BookResponse bookResponse = new BookResponse();
        ResultSet resultSet = bookDB.getBook(name, author);
        if (resultSet.next()) {
            bookResponse.setBook(parseBook(resultSet));
            return bookResponse;
        }
        bookResponse.setBook(new Book(name, author));
        bookResponse.setCode(ResultCode.NOT_FOUND);
        return bookResponse;
    }

    public BookResponse modifyBook(String name, String author, Book book) throws Exception {

        BookResponse response = new BookResponse();
        try {
            if (bookDB.modify(name, author, book)) {
                response.setBook(book);
                response.setCode(ResultCode.SUCCESS);
            } else {
                response.setBook(new Book(name, author));
                response.setCode(ResultCode.NOT_FOUND);
            }
        } catch (SQLException e) {
            response.setCode(ResultCode.FAILED);
            response.setError(e.getSQLState());
        } catch (Exception ex) {
            throw ex;
        }

        return response;
    }

    public boolean deleteBook(String name, String author) throws Exception {
        return bookDB.delete(name, author);
    }

    private Book parseBook(ResultSet resultSet) throws Exception{
        Book book = new Book();
        book.setName(resultSet.getString("name"));
        book.setAuthor(resultSet.getString("author"));
        book.setDescription(resultSet.getString("description"));
        return book;
    }
}
