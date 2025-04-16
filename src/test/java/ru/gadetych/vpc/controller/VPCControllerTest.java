package ru.gadetych.vpc.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.gadetych.vpc.dto.VacationPaymentsDto;
import ru.gadetych.vpc.model.CalculationData;
import ru.gadetych.vpc.service.VPCServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VPCController.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class VPCControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private final VPCServiceImpl service;

    private static final String BASE_URL = "/vpc";

    private MockHttpServletRequestBuilder setRequestHeaders(MockHttpServletRequestBuilder builder) {
        return builder
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON);
    }

    @Test
    void calculate_shouldReturnStatusOk() throws Exception {
        double avgSalary = 30_000.0;
        int vacationDays = 14;
        CalculationData input = new CalculationData(avgSalary, vacationDays);

        double expectedAmount = 14_334.470989761;
        VacationPaymentsDto result = new VacationPaymentsDto(expectedAmount);

        when(service.calculate(input)).thenReturn(result);

        mockMvc.perform(setRequestHeaders(get(BASE_URL + "/calculate")
                        .param("avgSalary", String.valueOf(avgSalary))
                        .param("vacationDays", String.valueOf(vacationDays))
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(expectedAmount));
    }

    @Test
    void calculate_shouldReturnStatusOkWithDate() throws Exception {
        double avgSalary = 30_000.0;
        int vacationDays = 14;
        LocalDate startDate = LocalDate.of(2025, 11, 1);
        LocalDate endDate = LocalDate.of(2025, 11, 14);
        CalculationData input = new CalculationData(avgSalary, vacationDays, startDate, endDate);

        double expectedAmount = 14_334.470989761;
        VacationPaymentsDto result = new VacationPaymentsDto(expectedAmount);

        when(service.calculate(input)).thenReturn(result);

        mockMvc.perform(setRequestHeaders(get(BASE_URL + "/calculate")
                        .param("avgSalary", String.valueOf(avgSalary))
                        .param("vacationDays", String.valueOf(vacationDays))
                        .param("startDate", startDate.format(DateTimeFormatter.ofPattern(VPCController.PATTERN_DATE)))
                        .param("endDate", endDate.format(DateTimeFormatter.ofPattern(VPCController.PATTERN_DATE)))
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(expectedAmount));
    }

    @ParameterizedTest
    @MethodSource("provideRequestParameters")
    void calculate_shouldReturnStatusBadRequest(String avgSalary, String vacationDays, String startDate, String endDate) throws Exception {
        mockMvc.perform(setRequestHeaders(get(BASE_URL + "/calculate")
                        .param("avgSalary", avgSalary)
                        .param("vacationDays", vacationDays)
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                ))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> provideRequestParameters() {
        return Stream.of(
                Arguments.of(null, "14", null, null),
                Arguments.of("-100.0", "14", null, null),
                Arguments.of("30000.0", null, null, null),
                Arguments.of("30000.0", "-5", null, null),
                Arguments.of("0.0", "14", null, null),
                Arguments.of("30000.0", "0", null, null),
                Arguments.of("30000.0", "1.4", null, null),
                Arguments.of("30000.0", null, "2025-03-10", null),
                Arguments.of("30000.0", null, null, "2025-03-20")
        );
    }
}