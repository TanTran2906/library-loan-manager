package com.example.library.mapper;

import com.example.library.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoanMapper {

    Book findBookWithLoansById(@Param("bookId") Long bookId);
}
