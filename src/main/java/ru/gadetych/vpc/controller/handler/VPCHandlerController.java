package ru.gadetych.vpc.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.gadetych.vpc.dto.ApiError;
import ru.gadetych.vpc.exeption.BadRequestException;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VPCHandlerController {
    private final StringWriter sw = new StringWriter();
    private final PrintWriter pw = new PrintWriter(sw);

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class, BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(RuntimeException e) {
        log.info("==> 400 {}", e.getMessage(), e);
        e.printStackTrace(pw);
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setStatus(HttpStatus.BAD_REQUEST.toString());
        apiError.setReason("Incorrectly made request.");
        apiError.setTimestamp(LocalDateTime.now());
        return apiError;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleServerError(final Exception e) {
        log.info("==> 500 {}", e.getMessage(), e);
        e.printStackTrace(pw);
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        apiError.setReason("Internal Server Error");
        apiError.setTimestamp(LocalDateTime.now());
        return apiError;
    }
}
