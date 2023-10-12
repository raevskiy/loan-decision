package com.example.loandecision.domain.decision;

import java.math.BigDecimal;

public enum FinancialHealthCategory {

    DEBT(BigDecimal.ZERO),
    SEGMENT_1(BigDecimal.valueOf(100)),
    SEGMENT_2(BigDecimal.valueOf(300)),
    SEGMENT_3(BigDecimal.valueOf(1000));

    private final BigDecimal creditModifier;

    FinancialHealthCategory(BigDecimal creditModifier) {
        this.creditModifier = creditModifier;
    }

    public BigDecimal getCreditModifier() {
        return creditModifier;
    }
}
