package br.com.wm.desafionubank.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	
	private int status;
    private String message;
    private Instant timestamp;
    private String errorCode;
    private List<ErrorField> errorsField;
    
    public static ErrorResponse validationError(RuntimeException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .errorCode("VALIDATION_ERROR")
                .build();
    }

    public static ErrorResponse invalidArgumentsError(List<FieldError> fieldErrors) {
    	List<ErrorField> errorsField = new ArrayList<>();
    	
    	fieldErrors.forEach(error -> {
    		ErrorField errorField = new ErrorField(error.getField(), error.getDefaultMessage());
    		errorsField.add(errorField);
    	});
    	
        return ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message("Argumento(s) inválido(s)")
                .timestamp(Instant.now())
                .errorCode("INVALID_ARGUMENT")
                .errorsField(errorsField)
                .build();
    }
    
    public static ErrorResponse badRequestError(HttpMessageNotReadableException ex) {
    	return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .errorCode("BAD_REQUEST_ERROR")
                .build();
	}
    
    public static ErrorResponse notFoundError(NoResourceFoundException ex) {
		return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .errorCode("NOT_FOUND_ERROR")
                .build();
	}

	public static ErrorResponse internalServerError(Exception ex) {
		return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .errorCode("INTERNAL_SERVER_ERROR")
                .build();
	}

}
