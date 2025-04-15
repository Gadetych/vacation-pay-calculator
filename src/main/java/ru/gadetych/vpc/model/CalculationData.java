package ru.gadetych.vpc.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class CalculationData {
    private Double avgSalary;
    private Integer vacationDays;
}
