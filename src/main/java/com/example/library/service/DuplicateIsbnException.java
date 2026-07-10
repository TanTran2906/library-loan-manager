package com.example.library.service;

/**
 * Thrown by the service when a book's ISBN collides with the {@code UNIQUE}
 * constraint on {@code book.isbn}. The service catches Spring's low-level
 * {@link org.springframework.dao.DuplicateKeyException} and translates it to
 * this domain exception, so the controller never sees a persistence type and
 * the user never sees a stack trace — just a field-level error on ISBN.
 */
public class DuplicateIsbnException extends RuntimeException {

    private final String isbn;

    public DuplicateIsbnException(String isbn, Throwable cause) {
        super("Duplicate ISBN: " + isbn, cause);
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
}
