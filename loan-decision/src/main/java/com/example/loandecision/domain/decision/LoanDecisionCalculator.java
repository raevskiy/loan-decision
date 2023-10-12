package com.example.loandecision.domain.decision;

import com.example.loandecision.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

@Service
@RequiredArgsConstructor
public class LoanDecisionCalculator {

    private final FinancialHealthService financialHealthService;

    public BigDecimal getApprovedLoanAmount(String personalCode, int loanPeriodMonths) {
        return financialHealthService.findFinancialHealthCategory(personalCode)
                .map(category -> category.getCreditModifier().multiply(BigDecimal.valueOf(loanPeriodMonths))
                        .setScale(2, HALF_UP))
                .orElseThrow(() -> new EntityNotFoundException("Client", "Personal Code", personalCode));
    }
}
