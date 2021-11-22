package uz.azizbek.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.azizbek.model.Card;
import uz.azizbek.payload.CardDto;

import java.util.Optional;

public interface CardService {
    CardDto createCard(CardDto cardDto);

    Card save(Card card);

    Page<CardDto> findAllDto(Pageable pageable);

    Page<Card> findAll(Pageable pageable);

    Optional<CardDto> findOne(Long id);

    Optional<Card> findById(Long id);

    boolean isExpired(Card card);

    Page<CardDto> findCardsByUserId(Long userId, Pageable pageable);

    Page<Card> findCardsByUserIdEntity(Long userId, Pageable pageable);
}
