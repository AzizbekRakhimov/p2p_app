package uz.azizbek.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CardDto {
    private Long id;

    @NotNull(message = "Username can not be empty")
    private String username;

    private Long cardNumber;

    private Double balance;

    @JsonFormat(pattern = "MM/yy")
    private LocalDate expireDate;

    @NotNull(message = "user can not be empty")
    private Long userId;

    private boolean active;
}
