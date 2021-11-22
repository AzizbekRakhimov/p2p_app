package uz.azizbek.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.azizbek.common.ResponseData;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleGenericException(final IllegalArgumentException e, final WebRequest webRequest, final HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new ResponseData<>( null, "Type not match or not found"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ObjectError errorObj = ex.getAllErrors().stream().findFirst().orElse(null);
        String error = errorObj.getDefaultMessage();
        return  ResponseEntity.badRequest().body(new ResponseData<>(null,error));
    }
}
