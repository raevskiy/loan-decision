package com.example.loandecision.domain.decision;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class FinancialHealthService {

    //Hardcoded keys-values, as suggested in the task description
    private final Map<String, FinancialHealthCategory> personalCodeToFinancialHealthCategory = Map.of(
            "49002010965", FinancialHealthCategory.DEBT,
            "49002010976", FinancialHealthCategory.SEGMENT_1,
            "49002010987", FinancialHealthCategory.SEGMENT_2,
            "49002010998", FinancialHealthCategory.SEGMENT_3
    );

    public Optional<FinancialHealthCategory> findFinancialHealthCategory(String personalCode) {
        //Mocking the implementation, as suggested in the task description
        return Optional.ofNullable(personalCodeToFinancialHealthCategory.get(personalCode));
    }
}
