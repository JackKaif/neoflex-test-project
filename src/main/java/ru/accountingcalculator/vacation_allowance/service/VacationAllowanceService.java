package ru.accountingcalculator.vacation_allowance.service;

import ru.accountingcalculator.vacation_allowance.model.VacationAllowancePayment;

import java.time.LocalDate;

public interface VacationAllowanceService {
    VacationAllowancePayment getVacationAllowance(double averageSalary, long vacationDuration, LocalDate vacationStartsDate);
}
