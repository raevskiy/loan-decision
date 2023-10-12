package com.example.loandecision.domain.decision;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/loan/decision")
@RequiredArgsConstructor
@Validated
public class LoanDecisionController {

    private final LoanDecisionCalculator loanDecisionCalculator;

    @GetMapping
    //assuming that the endpoint is used by some bank employee, since it accepts a personal code of any client
    @PreAuthorize("hasRole('ROLE_CLIENT_MANAGER')")
    public BigDecimal getApprovedLoanAmount(
            @ModelAttribute @Valid LoanRequest request
    ) {
        return loanDecisionCalculator.getApprovedLoanAmount(
                request.personalCode(), request.loanPeriodMonths());
    }

}
