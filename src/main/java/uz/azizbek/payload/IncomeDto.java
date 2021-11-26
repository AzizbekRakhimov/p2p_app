package uz.azizbek.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncomeDto {

    private Long id;
    private CardDto fromCard;
    private CardDto toCard;
    private Double amount;
    private Long userId;
    private LocalDateTime date;
}
