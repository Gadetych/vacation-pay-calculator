package ru.gadetych.vpc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gadetych.vpc.dto.VacationPaymentsDto;
import ru.gadetych.vpc.exeption.IncorrectNumberOfVacationDaysException;
import ru.gadetych.vpc.model.CalculationData;
import ru.gadetych.vpc.service.VPCService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/vpc", produces = "application/json")
@RequiredArgsConstructor
@Validated
@Slf4j
public class VPCController {
    private final VPCService service;
    public static final String PATTERN_DATE = "yyyy-MM-dd";

    @GetMapping("/calculate")
    public VacationPaymentsDto calculate(@RequestParam(value = "avgSalary", required = false)
                                             @NotNull
                                             @Positive
                                             Double avgSalary,
                                         @RequestParam(value = "vacationDays", required = false)
                                             @Positive
                                             Integer vacationDays,
                                         @RequestParam(value = "startDate", required = false)
                                             @DateTimeFormat(pattern = PATTERN_DATE)
                                             LocalDate startDate,
                                         @RequestParam(value = "endDate", required = false)
                                             @DateTimeFormat(pattern = PATTERN_DATE)
                                             LocalDate endDate) {
        log.info("==> calculate: avgSalary={}, vacationDays={}, startDate={}, endDate={}", avgSalary, vacationDays, startDate, endDate);
        if ((startDate == null || endDate == null) && vacationDays == null) {
            throw new IncorrectNumberOfVacationDaysException("You must enter either the number of vacation days or the exact days you are taking leave.");
        }
        CalculationData calculationData = new CalculationData(avgSalary, vacationDays, startDate, endDate);
        return service.calculate(calculationData);
    }
}
