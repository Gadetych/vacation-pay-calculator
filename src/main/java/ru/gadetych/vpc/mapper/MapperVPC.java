package ru.gadetych.vpc.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.gadetych.vpc.dto.VacationPaymentsDto;
import ru.gadetych.vpc.model.VacationPayments;

@UtilityClass
@Slf4j
public class MapperVPC {
    public VacationPaymentsDto modelToDto(VacationPayments model) {
        log.debug("==> modelToDto model: {}", model);
        VacationPaymentsDto dto = new VacationPaymentsDto(model.getAmount());
        log.debug("<== modelToDto dto: {}", dto);
        return dto;
    }
}
