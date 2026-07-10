package com.example.library.mapper;

import com.example.library.domain.Book;
import com.example.library.dto.BookSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BookMapper {

    List<BookSummary> findAll(@Param("keyword") String keyword, @Param("today") LocalDate today);

    long countAll();

    Book findById(@Param("id") Long id);

    void insert(Book book);

    void update(Book book);
}
