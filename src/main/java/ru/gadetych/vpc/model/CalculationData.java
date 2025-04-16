package ru.gadetych.vpc.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CalculationData {
    private Double avgSalary;
    private Integer vacationDays;
    private LocalDate startDate;
    private LocalDate endDate;

    public CalculationData(Double avgSalary, Integer vacationDays) {
        this.avgSalary = avgSalary;
        this.vacationDays = vacationDays;
    }
}
