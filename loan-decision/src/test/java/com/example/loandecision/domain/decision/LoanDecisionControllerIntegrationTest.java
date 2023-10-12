package com.example.loandecision.domain.decision;

import com.example.loandecision.LoanDecisionApplication;
import com.example.loandecision.config.SpringSecurityWebAuxTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(
        classes = {LoanDecisionApplication.class, SpringSecurityWebAuxTestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@SpringJUnitConfig(LoanDecisionApplication.class)
class LoanDecisionControllerIntegrationTest {

    private static final String URI = "/v1/loan/decision";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void onMissingUserDetailsShouldReturnUnauthorized() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(URI)
                .param("personalCode", "123456")
                .param("loanAmount", "3000")
                .param("loanPeriodMonths", "12")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("user@company.com")
    void onMissingClientManagerRoleShouldReturnForbidden() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(URI)
                .param("personalCode", "123456")
                .param("loanAmount", "3000")
                .param("loanPeriodMonths", "12")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("manager@company.com")
    void onMissingRequestFieldsShouldReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(URI)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rows", hasSize(3)))
                .andExpect(jsonPath("$.rows[0].field").value("loanAmount"))
                .andExpect(jsonPath("$.rows[0].reason").value("NotNull"))
                .andExpect(jsonPath("$.rows[0].message").value("must not be null"))
                .andExpect(jsonPath("$.rows[1].field").value("loanPeriodMonths"))
                .andExpect(jsonPath("$.rows[1].reason").value("NotNull"))
                .andExpect(jsonPath("$.rows[1].message").value("must not be null"))
                .andExpect(jsonPath("$.rows[2].field").value("personalCode"))
                .andExpect(jsonPath("$.rows[2].reason").value("NotNull"))
                .andExpect(jsonPath("$.rows[2].message").value("must not be null"));
    }

    @Test
    @WithUserDetails("manager@company.com")
    void onRequestedAmountTooLowPeriodTooShortShouldReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(URI)
                .param("personalCode", "123456")
                .param("loanAmount", "1234")
                .param("loanPeriodMonths", "11")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rows", hasSize(2)))
                .andExpect(jsonPath("$.rows[0].field").value("loanAmount"))
                .andExpect(jsonPath("$.rows[0].reason").value("Min"))
                .andExpect(jsonPath("$.rows[0].message").value("must be greater than or equal to 2000"))
                .andExpect(jsonPath("$.rows[1].field").value("loanPeriodMonths"))
                .andExpect(jsonPath("$.rows[1].reason").value("Min"))
                .andExpect(jsonPath("$.rows[1].message").value("must be greater than or equal to 12"));
    }

    @Test
    @WithUserDetails("manager@company.com")
    void onRequestedAmountTooHighPeriodTooLongShouldReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(URI)
                .param("personalCode", "123456")
                .param("loanAmount", "12345")
                .param("loanPeriodMonths", "61")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rows", hasSize(2)))
                .andExpect(jsonPath("$.rows[0].field").value("loanAmount"))
                .andExpect(jsonPath("$.rows[0].reason").value("Max"))
                .andExpect(jsonPath("$.rows[0].message").value("must be less than or equal to 10000"))
                .andExpect(jsonPath("$.rows[1].field").value("loanPeriodMonths"))
                .andExpect(jsonPath("$.rows[1].reason").value("Max"))
                .andExpect(jsonPath("$.rows[1].message").value("must be less than or equal to 60"));
    }

    @Test
    @WithUserDetails("manager@company.com")
    void onValidRequestForNonExistingClientShouldReturnNotFound() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(URI)
                .param("personalCode", "trololo")
                .param("loanAmount", "3000")
                .param("loanPeriodMonths", "12")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.entity").value("Client"))
                .andExpect(jsonPath("$.field").value("Personal Code"))
                .andExpect(jsonPath("$.value").value("trololo"));
    }

    @Test
    @WithUserDetails("manager@company.com")
    void onValidRequestForSegment1ClientShouldReturnNonZeroResponse() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(URI)
                .param("personalCode", "49002010976")
                .param("loanAmount", "3000")
                .param("loanPeriodMonths", "12")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1200.00"));
    }

    @Test
    @WithUserDetails("manager@company.com")
    void onValidRequestForSegment2ClientShouldReturnNonZeroResponse() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(URI)
                .param("personalCode", "49002010987")
                .param("loanAmount", "3000")
                .param("loanPeriodMonths", "12")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("3600.00"));
    }

    @Test
    @WithUserDetails("manager@company.com")
    void onValidRequestForSegment3ClientShouldReturnNonZeroResponse() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(URI)
                .param("personalCode", "49002010998")
                .param("loanAmount", "3000")
                .param("loanPeriodMonths", "12")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("12000.00"));
    }

    @Test
    @WithUserDetails("manager@company.com")
    void onValidRequestForDebtorShouldReturnZeroResponse() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(URI)
                .param("personalCode", "49002010965")
                .param("loanAmount", "3000")
                .param("loanPeriodMonths", "12")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0.00"));
    }
}
