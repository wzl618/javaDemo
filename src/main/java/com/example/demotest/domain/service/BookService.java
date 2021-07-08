package com.example.demotest.domain.service;

import com.example.demotest.domain.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findByName(String name);

    Long addBook(Book book);

    int deleteBook(Long id);

    int updateBook(Book book);
}
