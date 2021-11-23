package uz.azizbek.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.azizbek.model.Outcome;
import uz.azizbek.payload.OutcomeDto;

import java.time.LocalDateTime;

@Service
public class OutcomeMapper {

    @Autowired
    CardMapper cardMapper;

    public OutcomeDto toDto(Outcome outcome){
        OutcomeDto outcomeDto = new OutcomeDto();
        outcomeDto.setId(outcome.getId());
        outcomeDto.setFromCard(cardMapper.toDto(outcome.getFromCardId()));
        outcomeDto.setToCard(cardMapper.toDto(outcome.getToCardId()));
        outcomeDto.setCommissionAmount(outcome.getCommissionAmount());
        outcomeDto.setDate(outcome.getDate());
        outcomeDto.setAmount(outcome.getAmount());
        return outcomeDto;
    }

    public Outcome toEntity(OutcomeDto outcomeDto){
        Outcome outcome = new Outcome();

        outcome.setCommissionAmount(outcomeDto.getCommissionAmount());
        outcome.setDate(LocalDateTime.now());
        outcome.setAmount(outcomeDto.getAmount());
        return outcome;
    }
}
