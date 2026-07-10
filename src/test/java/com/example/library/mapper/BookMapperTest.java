package com.example.library.mapper;

import com.example.library.domain.Book;
import com.example.library.dto.BookSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Runs against the real, already-migrated `library` MySQL schema — no mocks.
 * Each test is wrapped in a transaction that's rolled back afterwards, so the
 * seed data from V2__seed.sql stays intact for the next run.
 */
@SpringBootTest
@Transactional
class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    @Test
    void findAllWithoutKeywordReturnsEverySeededBook() {
        List<BookSummary> books = bookMapper.findAll(null, LocalDate.now());

        assertThat(books).hasSize(25);
    }

    @Test
    void findAllWithKeywordFiltersByTitleOrAuthor() {
        List<BookSummary> books = bookMapper.findAll("Clean", LocalDate.now());

        assertThat(books).extracting(BookSummary::getTitle).containsExactly("Clean Code");
    }

    @Test
    void findByIdReturnsTheSeededBook() {
        Book book = bookMapper.findById(1L);

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo("The Pragmatic Programmer");
        assertThat(book.getTotalCopies()).isEqualTo(3);
    }

    @Test
    void insertAssignsGeneratedIdAndPersistsRow() {
        Book book = new Book();
        book.setTitle("Test Driven Book");
        book.setAuthor("Test Author");
        book.setIsbn("0000000000000");
        book.setPublishedYear(2024);
        book.setTotalCopies(2);

        bookMapper.insert(book);

        assertThat(book.getId()).isNotNull();
        Book reloaded = bookMapper.findById(book.getId());
        assertThat(reloaded.getTitle()).isEqualTo("Test Driven Book");
        assertThat(reloaded.getIsbn()).isEqualTo("0000000000000");
    }

    @Test
    void updateChangesTheStoredRow() {
        Book book = bookMapper.findById(2L);
        book.setTotalCopies(99);

        bookMapper.update(book);

        Book reloaded = bookMapper.findById(2L);
        assertThat(reloaded.getTotalCopies()).isEqualTo(99);
    }
}
