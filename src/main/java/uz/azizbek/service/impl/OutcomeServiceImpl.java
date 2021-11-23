package uz.azizbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.azizbek.model.Card;
import uz.azizbek.model.Outcome;
import uz.azizbek.payload.OutcomeDto;
import uz.azizbek.repository.OutcomeRepository;
import uz.azizbek.service.CardService;
import uz.azizbek.service.OutcomeService;
import uz.azizbek.service.mapper.OutcomeMapper;

import java.util.Optional;

@Service
public class OutcomeServiceImpl implements OutcomeService {

    @Autowired
    OutcomeRepository outcomeRepository;

    @Autowired
    OutcomeMapper outcomeMapper;

    @Autowired
    CardService cardService;

    @Override
    public Optional<OutcomeDto> sendTo(OutcomeDto outcomeDto, Card toCard, Card fromCard) {
        fromCard.setBalance(fromCard.getBalance() - outcomeDto.getCommissionAmount() * outcomeDto.getAmount() - outcomeDto.getAmount());
        toCard.setBalance(toCard.getBalance() + outcomeDto.getAmount());

        Card fromCardSave = cardService.save(fromCard);
        Card toCardSave = cardService.save(toCard);

        Outcome outcome;
        outcome = outcomeMapper.toEntity(outcomeDto);

        outcome.setFromCardId(fromCardSave);
        outcome.setToCardId(toCardSave);
        return Optional.of(outcomeMapper.toDto(save(outcome)));
    }

    @Override
    public Outcome save(Outcome outcome) {
        return outcomeRepository.save(outcome);
    }
}
