package ru.gadetych.vpc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gadetych.vpc.dto.VacationPaymentsDto;
import ru.gadetych.vpc.model.CalculationData;
import ru.gadetych.vpc.service.VPCService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(produces = "application/json")
@RequiredArgsConstructor
@Validated
@Slf4j
public class VPCController {
    private final VPCService service;

    @GetMapping("/calculate")
    public VacationPaymentsDto calculate(@RequestParam(value = "avgSalary", required = false)
                                         @NotNull
                                         @Positive
                                         Double avgSalary,
                                         @RequestParam(value = "vacationDays", required = false)
                                         @NotNull
                                         @Positive
                                         Integer vacationDays) {
        log.info("==> calculate: avgSalary={}, vacationDays={}", avgSalary, vacationDays);
        CalculationData calculationData = new CalculationData();
        calculationData.setAvgSalary(avgSalary);
        calculationData.setVacationDays(vacationDays);
        return service.calculate(calculationData);
    }
}
