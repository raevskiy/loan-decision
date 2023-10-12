package com.example.loandecision.domain.decision;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LoanRequest(
        @NotNull
        String personalCode,
        @NotNull
        @Min(2000)
        @Max(10000)
        BigDecimal loanAmount,
        @NotNull
        @Min(12)
        @Max(60)
        Integer loanPeriodMonths) {
}
