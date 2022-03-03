package org.nntu.restapi.persistence;

import org.nntu.restapi.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Component
public class BookDB {

    private static final String CLEAR_DB =
            "DROP ALL OBJECTS [DELETE FILES];";

    private static final String TRUNCATE_TABLE =
            "TRUNCATE TABLE books;";

    private static final String CREATE_TABLE =
            "CREATE TABLE books (name VARCHAR(32), author VARCHAR(32), description VARCHAR(64));";

    private static final String INSERT =
            "INSERT INTO books VALUES (?, ?, ?);";

    private static final String UPDATE =
            "UPDATE books SET name = ?, author = ?, description = ? WHERE name = ? and author = ?;";

    private static final String DELETE =
            "DELETE FROM books WHERE name = ? and author = ?;";

    private static final String GET_BOOK =
            "SELECT * FROM books where name = ? and author = ?";

    private static final String GET_ALL_BOOKS =
            "SELECT * FROM books";

    private final SqlConnection connection;

    @Autowired
    public BookDB(SqlConnection connection) {
        this.connection = connection;
        connection.init();
        createTable();
        fillUpDb();
    }

    public ResultSet getList() throws Exception {
            PreparedStatement query =
                    connection.getConnection().prepareStatement(GET_ALL_BOOKS);
        return query.executeQuery();
    }

    public ResultSet getBook(String name, String author) throws Exception{
        PreparedStatement pstmt =
                connection.getConnection().prepareStatement(GET_BOOK);
        pstmt.setString(1, name);
        pstmt.setString(2, author);
        return pstmt.executeQuery();
    }

    public boolean insert(Book book) throws Exception {

        if (getBook(book.getName(), book.getAuthor()).next()) {
            return false;
        }

        PreparedStatement pstmt = connection.getConnection().prepareStatement(INSERT);
        pstmt.setString(1, book.getName());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getDescription());
        pstmt.execute();
        return true;
    }

    public boolean modify(String name, String author, Book book) throws Exception {

        if (!getBook(name, author).next()) {
            return false;
        }

        PreparedStatement pstmt = connection.getConnection().prepareStatement(UPDATE);
        pstmt.setString(1, book.getName());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getDescription());
        pstmt.setString(4, name);
        pstmt.setString(5, author);
        pstmt.execute();

        return true;
    }

    public boolean delete(String name, String author) throws Exception{

        if (!getBook(name, author).next()) {
            return false;
        }

        PreparedStatement pstmt = connection.getConnection().prepareStatement(DELETE);
        pstmt.setString(1, name);
        pstmt.setString(2, author);
        pstmt.execute();

        return true;
    }

    private void createTable() {
        try {
            try (Statement dataQuery = connection.getConnection().createStatement()) {
                dataQuery.execute(CREATE_TABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillUpDb() {
        List<Book> booksList = new LinkedList<>();
        booksList.add(new Book("Marten Iden", "Jack London", "Awesome book"));
        booksList.add(new Book("Book1", "Jack London", "Awesome book1"));
        booksList.add(new Book("Book2", "Jack London", "Awesome book2"));
        booksList.add(new Book("Book3", "Orwell", "Awesome book3"));
        booksList.add(new Book("Book4", "Orwell", "Awesome book4"));
        try {
            PreparedStatement pstmt = connection.getConnection().prepareStatement(INSERT);
                for (Book book : booksList) {
                    pstmt.setString(1, book.getName());
                    pstmt.setString(2, book.getAuthor());
                    pstmt.setString(3, book.getDescription());
                    pstmt.execute();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
