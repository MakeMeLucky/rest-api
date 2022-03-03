package org.nntu.restapi.controller;

import org.nntu.restapi.dto.Book;
import org.nntu.restapi.dto.BookResponse;
import org.nntu.restapi.dto.DefaultResponse;
import org.nntu.restapi.dto.ResultCode;
import org.nntu.restapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @Autowired
    BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<?> getList() throws Exception {
        try {
            return makeResponse(bookService.getBookList(), HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/book")
    public ResponseEntity<?> getBook(@RequestParam String name, @RequestParam String author) throws Exception {
        try {
            BookResponse bookResponse = bookService.getBook(name, author);
            return makeResponse(bookResponse, bookResponse.getCode() == ResultCode.NOT_FOUND ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/books")
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        try {
            return makeResponse(bookService.createBook(book), HttpStatus.OK);
        } catch (Exception e) {
            return makeResponse(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books")
    public ResponseEntity<?> modifyBook(@RequestParam String name, @RequestParam String author, @RequestBody Book book) {
        try {
            BookResponse bookResponse = bookService.modifyBook(name, author, book);
            return makeResponse(bookResponse, bookResponse.getCode() == ResultCode.NOT_FOUND ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return makeResponse(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/books")
    public ResponseEntity<?> deleteBook(@RequestParam String name, @RequestParam String author) throws Exception {
        try {
            DefaultResponse defaultResponse = new DefaultResponse();
            boolean isDeleted = bookService.deleteBook(name, author);
            defaultResponse.setCode(isDeleted ? ResultCode.SUCCESS : ResultCode.NOT_FOUND);
            return makeResponse(defaultResponse, isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw e;
        }
    }

    private <T> ResponseEntity<MappingJacksonValue> makeResponse(T body, HttpStatus status) {
        HttpHeaders httpHeaders = new HttpHeaders();
        MappingJacksonValue mappingJacksonValue = null;
        if (body != null) {
            mappingJacksonValue = new MappingJacksonValue(body);
        }

        return makeResponse(mappingJacksonValue, status, httpHeaders);
    }

    private <T> ResponseEntity<T> makeResponse(T body, HttpStatus status, HttpHeaders httpHeaders) {
        return new ResponseEntity<T>(body, httpHeaders, status);
    }

}
