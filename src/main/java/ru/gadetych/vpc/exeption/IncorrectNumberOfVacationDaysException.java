package ru.gadetych.vpc.exeption;

public class IncorrectNumberOfVacationDaysException extends BadRequestException {
    public IncorrectNumberOfVacationDaysException(String message) {
        super(message);
    }
}
