package com.example.library.dto;

import java.util.List;

public class BookDetailView {

    private final Long id;
    private final String title;
    private final String author;
    private final String isbn;
    private final Integer publishedYear;
    private final int totalCopies;
    private final int availableCopies;
    private final List<LoanRow> loans;

    public BookDetailView(Long id, String title, String author, String isbn, Integer publishedYear,
                           int totalCopies, int availableCopies, List<LoanRow> loans) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.loans = loans;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public List<LoanRow> getLoans() {
        return loans;
    }
}
