package com.example.loandecision.exception.response;

import lombok.Getter;

import java.util.Set;
import java.util.TreeSet;

@Getter
public class ValidationErrorResponse {
    private final Set<ValidationErrorRow> rows = new TreeSet<>();
}
