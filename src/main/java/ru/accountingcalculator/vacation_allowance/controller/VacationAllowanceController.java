package ru.accountingcalculator.vacation_allowance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.accountingcalculator.vacation_allowance.model.VacationAllowancePayment;
import ru.accountingcalculator.vacation_allowance.service.VacationAllowanceService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VacationAllowanceController {
    final VacationAllowanceService vacationAllowanceService;

    @GetMapping("/vacation-allowance-calculator")
    public VacationAllowancePayment getAllowancePayment(
            @RequestParam("averageSalary") double averageSalary,
            @RequestParam("vacationDuration") int vacationDuration,
            @RequestParam(value = "vacationStartsDate", required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> vacationStartsDate
    ) {
        return vacationAllowanceService.getVacationAllowance(averageSalary, vacationDuration, vacationStartsDate);
    }
}
