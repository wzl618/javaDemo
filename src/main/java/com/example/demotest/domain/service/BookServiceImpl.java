package com.example.demotest.domain.service;
import com.example.demotest.domain.entity.Book;
import com.example.demotest.domain.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookMapper bookMapper;
    public List<Book> findAll(){
        return bookMapper.findAll();
    }

    public Book findByName(String name)
    {
        return bookMapper.findByName(name);
    }

    public Long addBook(Book book){
        return  bookMapper.addBook(book);
    }

    public int deleteBook(Long id)
    {
        return bookMapper.deleteBook(id);
    }

    public int updateBook(Book book)
    {
        return bookMapper.updateBook(book);
    }
}
