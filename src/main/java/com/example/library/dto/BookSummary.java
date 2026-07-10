package com.example.library.dto;

public class BookSummary {

    private final Long id;
    private final String title;
    private final String author;
    private final String isbn;
    private final int totalCopies;
    private final int availableCopies;
    private final String status;

    public BookSummary(Long id, String title, String author, String isbn,
                        int totalCopies, int availableCopies, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.status = status;
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

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusLabel() {
        switch (status) {
            case "overdue":
                return "Overdue";
            case "on-loan":
                return "On loan";
            default:
                return "Available";
        }
    }
}
