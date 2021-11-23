package uz.azizbek.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class OutcomeDto {
    private Long id;
    @NotNull(message = "FromCardId can not be empty")
    private CardDto fromCard;
    @NotNull(message = "ToCardId can not be empty")
    private CardDto toCard;
    @NotNull(message = "Amount can not be empty")
    private Double amount;
    private LocalDateTime date;
    private Double commissionAmount = 0.0035;
}
