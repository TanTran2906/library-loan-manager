package com.example.library.dto;

import java.time.LocalDate;

public class LoanRow {

    private final Long id;
    private final String memberFullName;
    private final LocalDate borrowedAt;
    private final LocalDate dueAt;
    private final LocalDate returnedAt;
    private final boolean overdue;

    public LoanRow(Long id, String memberFullName, LocalDate borrowedAt, LocalDate dueAt,
                    LocalDate returnedAt, boolean overdue) {
        this.id = id;
        this.memberFullName = memberFullName;
        this.borrowedAt = borrowedAt;
        this.dueAt = dueAt;
        this.returnedAt = returnedAt;
        this.overdue = overdue;
    }

    public Long getId() {
        return id;
    }

    public String getMemberFullName() {
        return memberFullName;
    }

    public LocalDate getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDate getDueAt() {
        return dueAt;
    }

    public LocalDate getReturnedAt() {
        return returnedAt;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public String getStatus() {
        if (returnedAt != null) {
            return "available";
        }
        return overdue ? "overdue" : "on-loan";
    }

    public String getStatusLabel() {
        if (returnedAt != null) {
            return "Returned";
        }
        return overdue ? "Overdue" : "On loan";
    }
}
