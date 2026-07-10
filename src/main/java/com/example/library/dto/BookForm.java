package com.example.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookForm {

    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(max = 255, message = "Title must be 255 characters or fewer.")
    private String title;

    @NotBlank(message = "Author is required.")
    @Size(max = 160, message = "Author must be 160 characters or fewer.")
    private String author;

    @NotBlank(message = "ISBN is required.")
    @Size(max = 20, message = "ISBN must be 20 characters or fewer.")
    private String isbn;

    // Optional — a book may predate a recorded year. Bean Validation skips
    // @Min/@Max when the value is null, so the field stays optional yet bounded
    // when supplied. Range, not the current year, so it never rots year-on-year.
    @Min(value = 1450, message = "Published year must be 1450 or later.")
    @Max(value = 2100, message = "Published year must be 2100 or earlier.")
    private Integer publishedYear;

    @NotNull(message = "Total copies is required.")
    @Min(value = 1, message = "There must be at least 1 copy.")
    @Max(value = 100000, message = "That is more copies than we can track.")
    private Integer totalCopies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }
}
