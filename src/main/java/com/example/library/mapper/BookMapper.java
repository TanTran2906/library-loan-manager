package com.example.library.mapper;

import com.example.library.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> findAll(@Param("keyword") String keyword);

    Book findById(@Param("id") Long id);

    void insert(Book book);

    void update(Book book);
}
