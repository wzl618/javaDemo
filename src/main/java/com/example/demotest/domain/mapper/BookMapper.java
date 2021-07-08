package com.example.demotest.domain.mapper;

import com.example.demotest.domain.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    //获取所有数据
    List<Book> findAll();

    //根据书名去匹配数据
    Book findByName(String name);

    //添加书籍
    Long addBook (Book book);

    int deleteBook(Long id);

    int updateBook(Book book);
}
