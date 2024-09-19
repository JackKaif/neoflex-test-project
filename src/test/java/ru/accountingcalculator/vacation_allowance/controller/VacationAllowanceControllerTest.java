package ru.accountingcalculator.vacation_allowance.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VacationAllowanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPaymentWoDate() throws Exception {
        double averageSalary = 40000;
        int vacationDuration = 14;
        double expected = 16627.99;
        var request = get("/vacation-allowance-calculator")
                .param("averageSalary", String.valueOf(averageSalary))
                .param("vacationDuration", String.valueOf(vacationDuration));
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                v -> v.node("amount").isEqualTo(expected)
        );
    }

    @Test
    public void getPaymentWithDate() throws Exception {
        double averageSalary = 40000;
        int vacationDuration = 14;
        double expectedWithOneHoliday = 15440.27;
        var request1 = get("/vacation-allowance-calculator")
                .param("averageSalary", String.valueOf(averageSalary))
                .param("vacationDuration", String.valueOf(vacationDuration))
                .param("vacationStartsDate", "2024-05-02");
        var result1 = mockMvc.perform(request1)
                .andExpect(status().isOk())
                .andReturn();
        var body1 = result1.getResponse().getContentAsString();

        assertThatJson(body1).and(
                v -> v.node("amount").isEqualTo(expectedWithOneHoliday)
        );

        double expectedWithTwoHolidays = 14252.56;
        var request2 = get("/vacation-allowance-calculator")
                .param("averageSalary", String.valueOf(averageSalary))
                .param("vacationDuration", String.valueOf(vacationDuration))
                .param("vacationStartsDate", "2024-04-30");
        var result2 = mockMvc.perform(request2)
                .andExpect(status().isOk())
                .andReturn();
        var body2 = result2.getResponse().getContentAsString();

        assertThatJson(body2).and(
                v -> v.node("amount").isEqualTo(expectedWithTwoHolidays)
        );

        double expectedWoHolidays = 16627.99;
        var request3 = get("/vacation-allowance-calculator")
                .param("averageSalary", String.valueOf(averageSalary))
                .param("vacationDuration", String.valueOf(vacationDuration))
                .param("vacationStartsDate", "2024-08-01");
        var result3 = mockMvc.perform(request3)
                .andExpect(status().isOk())
                .andReturn();
        var body3 = result3.getResponse().getContentAsString();

        assertThatJson(body3).and(
                v -> v.node("amount").isEqualTo(expectedWoHolidays)
        );
    }
}