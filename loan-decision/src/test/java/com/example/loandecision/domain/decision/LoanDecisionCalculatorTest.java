package com.example.loandecision.domain.decision;

import com.example.loandecision.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanDecisionCalculatorTest {

    @InjectMocks
    private LoanDecisionCalculator calculator;
    @Mock
    private FinancialHealthService financialHealthService;

    @Test
    void onFinancialHealthServiceReturnsNoCategoryShouldThrowEntityNotFoundException() {
        String personalCode = "123456";
        when(financialHealthService.findFinancialHealthCategory(personalCode)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> calculator.getApprovedLoanAmount(personalCode, 42));
    }

    @Test
    void onFinancialHealthServiceReturnsDebtorCategoryShouldReturnZero() {
        String personalCode = "123456";
        when(financialHealthService.findFinancialHealthCategory(personalCode)).thenReturn(Optional.of(FinancialHealthCategory.DEBT));

        assertEquals(new BigDecimal("0.00"), calculator.getApprovedLoanAmount(personalCode, 42));
    }

    @Test
    void onFinancialHealthServiceReturnsNonDebtorCategoryShouldReturnNonZero() {
        String personalCode = "123456";
        when(financialHealthService.findFinancialHealthCategory(personalCode)).thenReturn(Optional.of(FinancialHealthCategory.SEGMENT_3));

        assertEquals(new BigDecimal("42000.00"), calculator.getApprovedLoanAmount(personalCode, 42));
    }
}
