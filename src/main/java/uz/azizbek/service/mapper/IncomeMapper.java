package uz.azizbek.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.azizbek.model.Income;
import uz.azizbek.model.Outcome;
import uz.azizbek.payload.IncomeDto;

import java.time.LocalDateTime;

@Service
public class IncomeMapper {

    @Autowired
    CardMapper cardMapper;

    public IncomeDto toDto(Income income){
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setId(income.getId());
        incomeDto.setFromCard(cardMapper.toDto(income.getFromCard()));
        incomeDto.setToCard(cardMapper.toDto(income.getToCard()));
        incomeDto.setAmount(income.getAmount());
        incomeDto.setDate(income.getDate());
        incomeDto.setUserId(income.getUserId());
        return incomeDto;
    }

    public Income toEntity(IncomeDto incomeDto){
        Income income = new Income();
        income.setAmount(incomeDto.getAmount());
        income.setDate(LocalDateTime.now());
        return income;
    }

    public Income outcomeToIncome(Outcome outcome){
        Income income = new Income();
        income.setFromCard(outcome.getFromCardId());
        income.setToCard(outcome.getToCardId());
        income.setDate(outcome.getDate());
        income.setAmount(outcome.getAmount());
        income.setUserId(outcome.getToCardId().getUserId());
        return income;
    }
}
