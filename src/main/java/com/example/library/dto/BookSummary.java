package com.example.library.dto;

public class BookSummary {

    private final Long id;
    private final String title;
    private final String author;
    private final int totalCopies;

    public BookSummary(Long id, String title, String author, int totalCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
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

    public int getTotalCopies() {
        return totalCopies;
    }
}
