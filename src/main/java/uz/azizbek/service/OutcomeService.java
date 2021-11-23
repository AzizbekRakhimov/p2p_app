package uz.azizbek.service;

import uz.azizbek.model.Card;
import uz.azizbek.model.Outcome;
import uz.azizbek.payload.OutcomeDto;

import java.util.Optional;

public interface OutcomeService {
    Optional<OutcomeDto> sendTo(OutcomeDto outcomeDto, Card toCard, Card fromCard);

    Outcome save(Outcome outcome);
}
