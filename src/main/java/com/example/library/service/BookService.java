package com.example.library.service;

import com.example.library.domain.Book;
import com.example.library.domain.Loan;
import com.example.library.dto.BookDetailView;
import com.example.library.dto.BookForm;
import com.example.library.dto.BookSummary;
import com.example.library.dto.LibraryStats;
import com.example.library.dto.LoanRow;
import com.example.library.mapper.BookMapper;
import com.example.library.mapper.LoanMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookMapper bookMapper;
    private final LoanMapper loanMapper;

    public BookService(BookMapper bookMapper, LoanMapper loanMapper) {
        this.bookMapper = bookMapper;
        this.loanMapper = loanMapper;
    }

    @Transactional(readOnly = true)
    public LibraryStats getStats() {
        long totalBooks = bookMapper.countAll();
        long onLoan = loanMapper.countOnLoan();
        long overdue = loanMapper.countOverdue(LocalDate.now());
        return new LibraryStats(totalBooks, onLoan, overdue);
    }

    @Transactional(readOnly = true)
    public List<BookSummary> findAll(String keyword) {
        return bookMapper.findAll(keyword, LocalDate.now());
    }

    /** A short slice of the catalogue for the hero preview table. */
    @Transactional(readOnly = true)
    public List<BookSummary> findPreview(int limit) {
        return bookMapper.findAll(null, LocalDate.now()).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDetailView getDetail(Long id) {
        Book book = loanMapper.findBookWithLoansById(id);
        if (book == null) {
            throw new NoSuchElementException("No book with id " + id);
        }

        LocalDate today = LocalDate.now();
        List<Loan> loans = book.getLoans();

        long onLoan = loans.stream().filter(loan -> loan.getReturnedAt() == null).count();
        int availableCopies = (int) (book.getTotalCopies() - onLoan);

        List<LoanRow> loanRows = loans.stream()
                .map(loan -> new LoanRow(
                        loan.getId(),
                        loan.getMember().getFullName(),
                        loan.getBorrowedAt(),
                        loan.getDueAt(),
                        loan.getReturnedAt(),
                        loan.getReturnedAt() == null && loan.getDueAt().isBefore(today)))
                .collect(Collectors.toList());

        boolean anyOverdue = loanRows.stream().anyMatch(LoanRow::isOverdue);
        String status = anyOverdue ? "overdue" : (availableCopies <= 0 ? "on-loan" : "available");

        return new BookDetailView(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublishedYear(),
                book.getTotalCopies(),
                availableCopies,
                status,
                loanRows);
    }

    @Transactional(readOnly = true)
    public BookForm getForm(Long id) {
        Book book = bookMapper.findById(id);
        if (book == null) {
            throw new NoSuchElementException("No book with id " + id);
        }
        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setTitle(book.getTitle());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());
        form.setPublishedYear(book.getPublishedYear());
        form.setTotalCopies(book.getTotalCopies());
        return form;
    }

    @Transactional
    public Long create(BookForm form) {
        Book book = new Book();
        book.setTitle(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setPublishedYear(form.getPublishedYear());
        book.setTotalCopies(form.getTotalCopies());
        try {
            bookMapper.insert(book);
        } catch (DuplicateKeyException e) {
            throw new DuplicateIsbnException(form.getIsbn(), e);
        }
        return book.getId();
    }

    @Transactional
    public void update(Long id, BookForm form) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setPublishedYear(form.getPublishedYear());
        book.setTotalCopies(form.getTotalCopies());
        try {
            bookMapper.update(book);
        } catch (DuplicateKeyException e) {
            throw new DuplicateIsbnException(form.getIsbn(), e);
        }
    }
}
