package com.example.loandecision.exception.response;

import java.util.Comparator;

public record ValidationErrorRow(
        String field, String reason, String message) implements Comparable<ValidationErrorRow> {

    private static final Comparator<String> NULL_SAFE_STRING_COMPARATOR = Comparator.nullsFirst(String::compareToIgnoreCase);

    public String toString() {
        return String.format("ValidationErrorRow(field=%s, reason=%s, message=%s)", field, reason, message);
    }

    public int compareTo(ValidationErrorRow o) {
        return Comparator.nullsFirst(
                Comparator.comparing(ValidationErrorRow::field, NULL_SAFE_STRING_COMPARATOR)
                        .thenComparing(ValidationErrorRow::reason, NULL_SAFE_STRING_COMPARATOR)
                        .thenComparing(ValidationErrorRow::message, NULL_SAFE_STRING_COMPARATOR)).compare(this, o);
    }

}
