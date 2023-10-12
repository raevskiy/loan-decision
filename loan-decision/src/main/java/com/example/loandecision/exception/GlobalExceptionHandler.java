package com.example.loandecision.exception;

import com.example.loandecision.exception.response.EntityNotFoundErrorResponse;
import com.example.loandecision.exception.response.ValidationErrorResponse;
import com.example.loandecision.exception.response.ValidationErrorRow;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ValidationErrorResponse handleValidationException(HttpServletResponse response, MethodArgumentNotValidException e) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.getRows().addAll(e.getFieldErrors().stream()
                .map(error -> new ValidationErrorRow(
                        error.getField(), error.getCode(), error.getDefaultMessage())).toList());
        response.setContentType("application/json");
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return errorResponse;
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseBody
    public EntityNotFoundErrorResponse handleNotFoundException(HttpServletResponse response, EntityNotFoundException e) {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.NOT_FOUND.value());

        return new EntityNotFoundErrorResponse(e);
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public void handleAccessDeniedExceptionException(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public void handleAnyOtherException(HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
