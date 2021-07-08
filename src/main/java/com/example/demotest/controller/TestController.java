package com.example.demotest.controller;

import com.example.demotest.domain.entity.Book;
import com.example.demotest.domain.service.BookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api("testapi")
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private BookService bookService;

    @GetMapping("hello")
    public String hello(){
        return "hello world !";
    }

    @GetMapping("GetBookList")
    public ArrayList<Book> GetBookList()
    {
        ArrayList<Book> books=new ArrayList<Book>();
        Book a=new Book(0,"bookA","aaa");
        Book b=new Book(0,"bookB","bbb");
        books.add(a);
        books.add(b);
        return books;
    }

    @GetMapping("GetAllBooks")
    public List<Book> GetAllBooks()
    {
        return bookService.findAll();
    }
    @GetMapping("FindByName")
    public ResponseEntity FindByName(@RequestParam("name") String name)
    {
        Book book= bookService.findByName(name);
        return ResponseEntity.ok(book);
    }

    @GetMapping("AddBook")
    public ResponseEntity AddBook(@RequestParam("name") String name,@RequestParam("description") String description)
    {
        Book book=new Book(0,name,description);
        long id= bookService.addBook(book);
        return ResponseEntity.ok(book.getId());
    }

    @GetMapping("DeleteBook")
    public ResponseEntity DeleteBook(@RequestParam("id") long id)
    {
        int result =bookService.deleteBook(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("UpdateBook")
    public ResponseEntity UpdateBook(@RequestParam("id") long id,@RequestParam("name") String name,@RequestParam("description") String description)
    {
        Book book=new Book(id,name,description);
        int result= bookService.updateBook(book);
        return ResponseEntity.ok(result);
    }
}
