package uz.azizbek.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.azizbek.model.Card;
import uz.azizbek.payload.CardDto;
import uz.azizbek.repository.CardRepository;
import uz.azizbek.service.CardService;
import uz.azizbek.service.mapper.CardMapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardServiceImpl(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public CardDto createCard(CardDto cardDto) {
        Card card = save(cardMapper.toEntity(cardDto));
        return cardMapper.toDto(card);
    }

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Page<CardDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(cardMapper::toDto);
    }

    @Override
    public Page<Card> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    @Override
    public Optional<CardDto> findOne(Long id) {
        return findById(id).map(cardMapper::toDto);
    }

    @Override
    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public boolean isExpired(Card card) {
        LocalDate currentDate = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate();
        return card.getExpireDate().isBefore(currentDate);
    }
}
