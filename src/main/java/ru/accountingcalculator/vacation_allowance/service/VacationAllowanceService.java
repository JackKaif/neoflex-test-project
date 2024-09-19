package ru.accountingcalculator.vacation_allowance.service;

import org.springframework.stereotype.Service;
import ru.accountingcalculator.vacation_allowance.model.VacationAllowancePayment;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class VacationAllowanceService {
    private final double AVERAGE_NONHOLIDAYS_IN_MONTH = 29.3;
    private final double INCOME_TAX = 0.13;
    static final HashMap<Month, List<Integer>> holidays = new HashMap<>();
    static {
        holidays.put(Month.JANUARY, List.of(1,2,3,4,5,6,7,8));
        holidays.put(Month.FEBRUARY, List.of(23));
        holidays.put(Month.MARCH, List.of(8));
        holidays.put(Month.MAY, List.of(1,9));
        holidays.put(Month.JUNE, List.of(12));
        holidays.put(Month.NOVEMBER, List.of(4));
    }

    private boolean isHoliday(LocalDate date) {
        return Optional.ofNullable(holidays.get(date.getMonth()))
                .map(days -> days.contains(date.getDayOfMonth()))
                .orElse(false);
    }

    public VacationAllowancePayment getVacationAllowance(double averageSalary,
                                                         int vacationDuration,
                                                         Optional<LocalDate> vacationStartsDate) {
        long vacationDurationWoHolidays = vacationDuration;
        if (vacationStartsDate.isPresent()) {
            vacationDurationWoHolidays = vacationStartsDate.get().datesUntil(vacationStartsDate.get().plusDays(vacationDuration))
                    .filter(date -> !isHoliday(date))
                    .count();
        }
        var vacationAllowanceAmount = averageSalary / AVERAGE_NONHOLIDAYS_IN_MONTH * vacationDurationWoHolidays * (1 - INCOME_TAX);
        var vacationAllowanceAmountRounded = Math.round(vacationAllowanceAmount * 100.00) / 100.00;
        return new VacationAllowancePayment(vacationAllowanceAmountRounded);
    }

}
