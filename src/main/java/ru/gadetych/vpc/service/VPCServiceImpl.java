package ru.gadetych.vpc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gadetych.vpc.dto.VacationPaymentsDto;
import ru.gadetych.vpc.mapper.MapperVPC;
import ru.gadetych.vpc.model.CalculationData;
import ru.gadetych.vpc.model.VacationPayments;

import java.time.LocalDate;
import java.util.Set;

@Service
@Slf4j
public class VPCServiceImpl implements VPCService {
    private static final double AVERAGE_NUMBER_OF_DAYS_IN_MONTH = 29.3;
    private final Set<LocalDate> holidays = Set.of(
            LocalDate.of(2025, 1, 1),
            LocalDate.of(2025, 1, 2),
            LocalDate.of(2025, 1, 3),
            LocalDate.of(2025, 1, 4),
            LocalDate.of(2025, 1, 5),
            LocalDate.of(2025, 1, 6),
            LocalDate.of(2025, 1, 7),
            LocalDate.of(2025, 2, 23),
            LocalDate.of(2025, 3, 8),
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 9),
            LocalDate.of(2025, 6, 12),
            LocalDate.of(2025, 11, 4)
    );

    @Override
    public VacationPaymentsDto calculate(CalculationData calculationData) {
        log.debug("==> calculate: {}", calculationData);
        double amount;
        int vacationDays;
        if (calculationData.getStartDate() == null || calculationData.getEndDate() == null) {
            vacationDays = calculationData.getVacationDays();
        } else {
            LocalDate date = calculationData.getStartDate();
            LocalDate endDate = calculationData.getEndDate();
            vacationDays = calculateVacationDays(date, endDate);
        }
        amount = (calculationData.getAvgSalary() / AVERAGE_NUMBER_OF_DAYS_IN_MONTH) * vacationDays;
        VacationPayments vacationPayments = new VacationPayments(amount);
        return MapperVPC.modelToDto(vacationPayments);
    }

    private boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }

    private int calculateVacationDays(LocalDate date, LocalDate endDate) {
        int vacationDays = 0;
        while (!date.isAfter(endDate)) {
            if (!isHoliday(date)) {
                vacationDays++;
            }
            date = date.plusDays(1);
        }
        return vacationDays;
    }
}
