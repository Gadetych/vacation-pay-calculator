package ru.gadetych.vpc.service;

import ru.gadetych.vpc.dto.VacationPaymentsDto;
import ru.gadetych.vpc.model.CalculationData;

public interface VPCService {
    VacationPaymentsDto calculate(CalculationData calculationData);
}
