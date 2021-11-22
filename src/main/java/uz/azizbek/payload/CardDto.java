package uz.azizbek.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import uz.azizbek.model.Users;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CardDto {
    private Long id;

    @NotNull(message = "Username can not be empty")
    private String username;

    @NotNull(message = "Number can not be empty")
    private Long cardNumber;

    private Double balance;

    @JsonFormat(pattern = "MM/yy")
    private LocalDate expireDate;

    private Users users;

    private boolean active;
}
