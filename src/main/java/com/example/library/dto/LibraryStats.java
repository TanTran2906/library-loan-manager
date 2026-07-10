package com.example.library.dto;

/**
 * Three at-a-glance counts for the hero. "On loan" is every copy currently off
 * the shelf (returned_at IS NULL); "overdue" is the subset of those past due.
 */
public class LibraryStats {

    private final long totalBooks;
    private final long onLoan;
    private final long overdue;

    public LibraryStats(long totalBooks, long onLoan, long overdue) {
        this.totalBooks = totalBooks;
        this.onLoan = onLoan;
        this.overdue = overdue;
    }

    public long getTotalBooks() {
        return totalBooks;
    }

    public long getOnLoan() {
        return onLoan;
    }

    public long getOverdue() {
        return overdue;
    }
}
