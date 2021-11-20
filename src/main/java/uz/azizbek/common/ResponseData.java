package uz.azizbek.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    @JsonProperty(value = "data")
    T data;

    @JsonProperty(value = "errorMessage")
    String errorMessage;

    @JsonProperty(value = "timestamp")
    Long timestamp = System.currentTimeMillis();

    public ResponseData(T data, String errorMessage) {
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public ResponseData(T data){
        this.data = data;
        this.errorMessage = "";
    }

    public ResponseData() {
        this.errorMessage = "";
    }

    public static <T> ResponseEntity<ResponseData<T>> response(T data){
        return ResponseEntity.ok(new ResponseData<>(data));
    }

    public static  <T> ResponseEntity<ResponseData<T>> response(ResponseData<T> responseData, HttpStatus httpStatus){
        return new ResponseEntity<>(responseData, httpStatus);
    }

    public static <T> ResponseEntity<ResponseData<T>> response(String errorMessage, HttpStatus status){
        return new ResponseEntity<>(new ResponseData<T>( null, errorMessage),status);
    }
}
