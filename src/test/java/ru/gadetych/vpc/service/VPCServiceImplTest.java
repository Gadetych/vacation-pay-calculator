package ru.gadetych.vpc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gadetych.vpc.dto.VacationPaymentsDto;
import ru.gadetych.vpc.model.CalculationData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class VPCServiceImplTest {
    @InjectMocks
    private VPCServiceImpl service;
    private static final double DELTA = 0.01;

    @Test
    void calculate_shouldReturnCorrectAmountForStandardInput() {
        double avgSalary = 30_000.0;
        int vacationDays = 14;
        CalculationData input = new CalculationData(avgSalary, vacationDays);
        VacationPaymentsDto result = service.calculate(input);

        assertNotNull(result);
        // Расчет: 30000 / 29.3 * 14 = 14334.47
        assertEquals(14_334.47, result.getAmount(), DELTA);
    }

    @Test
    void calculate_shouldHandleZeroVacationDays() {
        double avgSalary = 50_000.0;
        int vacationDays = 0;
        CalculationData input = new CalculationData(avgSalary, vacationDays);
        VacationPaymentsDto result = service.calculate(input);

        assertNotNull(result);
        assertEquals(0.0, result.getAmount(), DELTA);
    }

    @Test
    void calculate_shouldHandleLargeSalary() {
        double avgSalary = 1_000_000.0;
        int vacationDays = 28;
        CalculationData input = new CalculationData(avgSalary, vacationDays);
        VacationPaymentsDto result = service.calculate(input);

        assertNotNull(result);
        // Расчет: 1000000 / 29.3 * 28 = 955631.40
        assertEquals(955_631.40, result.getAmount(), DELTA);
    }

    @Test
    void calculate_shouldHandleDate() {
        double avgSalary = 50_000.0;
        LocalDate start = LocalDate.of(2025, 4, 7);
        LocalDate end = LocalDate.of(2025, 4, 20);
        CalculationData input = new CalculationData(avgSalary, null, start, end);
        VacationPaymentsDto result = service.calculate(input);

        assertNotNull(result);
        // Расчет: 50000 / 29.3 * 14 = 23_890.79
        assertEquals(23_890.79, result.getAmount(), DELTA);
    }

    @Test
    void calculate_shouldHandleAllData() {
        double avgSalary = 50_000.0;
        int vacationDays = 14;
        LocalDate start = LocalDate.of(2025, 4, 7);
        LocalDate end = LocalDate.of(2025, 4, 20);
        CalculationData input = new CalculationData(avgSalary, vacationDays, start, end);
        VacationPaymentsDto result = service.calculate(input);

        assertNotNull(result);
        // Расчет: 50000 / 29.3 * 14 = 23_890.79
        assertEquals(23_890.79, result.getAmount(), DELTA);
    }

    @Test
    void calculate_shouldHandleDateWithHoliday() {
        double avgSalary = 50_000.0;
        LocalDate start = LocalDate.of(2025, 3, 3);
        LocalDate end = LocalDate.of(2025, 3, 16);
        CalculationData input = new CalculationData(avgSalary, null, start, end);
        VacationPaymentsDto result = service.calculate(input);

        assertNotNull(result);
        // Расчет: 50000 / 29.3 * 13 = 22_184.30
        assertEquals(22_184.30, result.getAmount(), DELTA);
    }
}