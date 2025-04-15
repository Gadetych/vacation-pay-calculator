package ru.gadetych.vpc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gadetych.vpc.dto.VacationPaymentsDto;
import ru.gadetych.vpc.mapper.MapperVPC;
import ru.gadetych.vpc.model.CalculationData;
import ru.gadetych.vpc.model.VacationPayments;

@Service
@Slf4j
public class VPCServiceImpl implements VPCService {
    private static final double AVERAGE_NUMBER_OF_DAYS_IN_MONTH = 29.3;

    @Override
    public VacationPaymentsDto calculate(CalculationData calculationData) {
        log.debug("==> calculate: {}", calculationData);
        double amount = calculationData.getAvgSalary() / AVERAGE_NUMBER_OF_DAYS_IN_MONTH * calculationData.getVacationDays();
        VacationPayments vacationPayments = new VacationPayments();
        vacationPayments.setAmount(amount);
        return MapperVPC.modelToDto(vacationPayments);
    }
}
