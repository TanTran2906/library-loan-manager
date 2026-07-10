package com.example.library.mapper;

import com.example.library.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

@Mapper
public interface LoanMapper {

    Book findBookWithLoansById(@Param("bookId") Long bookId);

    long countOnLoan();

    long countOverdue(@Param("today") LocalDate today);
}
