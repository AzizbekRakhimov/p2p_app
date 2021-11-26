package uz.azizbek.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.azizbek.model.Card;
import uz.azizbek.model.Outcome;
import uz.azizbek.payload.OutcomeDto;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OutcomeService {
    Optional<OutcomeDto> sendTo(OutcomeDto outcomeDto, Card toCard, Card fromCard);

    Outcome save(Outcome outcome);

    Page<OutcomeDto> findByUserId(Long id, Pageable pageable);

    Page<Outcome> findByUserIdEntity(Long id, Pageable pageable);

    Optional<OutcomeDto> findOneByUserId(Long id, Long userId);

    Optional<Outcome> findByUserId(Long id, Long userId);

    Page<OutcomeDto> findAllDto(Pageable pageable);

    Page<Outcome> findAll(Pageable pageable);

    Optional<OutcomeDto> findOne(Long id);

    Optional<Outcome> findById(Long id);
}
