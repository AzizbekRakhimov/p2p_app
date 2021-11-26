package uz.azizbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.azizbek.model.Card;
import uz.azizbek.model.Income;
import uz.azizbek.model.Outcome;
import uz.azizbek.payload.OutcomeDto;
import uz.azizbek.repository.OutcomeRepository;
import uz.azizbek.service.CardService;
import uz.azizbek.service.IncomeService;
import uz.azizbek.service.OutcomeService;
import uz.azizbek.service.mapper.IncomeMapper;
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

    @Autowired
    IncomeService incomeService;

    @Autowired
    IncomeMapper incomeMapper;

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
        Income income = incomeMapper.outcomeToIncome(outcome);
        incomeService.save(income);
        return Optional.of(outcomeMapper.toDto(save(outcome)));
    }

    @Override
    public Outcome save(Outcome outcome) {
        return outcomeRepository.save(outcome);
    }

    @Override
    public Page<OutcomeDto> findByUserId(Long id, Pageable pageable) {
        return findByUserIdEntity(id, pageable).map(outcomeMapper::toDto);
    }

    @Override
    public Page<Outcome> findByUserIdEntity(Long id, Pageable pageable) {
        return outcomeRepository.findByUserId(id, pageable);
    }

    @Override
    public Optional<OutcomeDto> findOneByUserId(Long id, Long userId) {
        return findByUserId(id, userId).map(outcomeMapper::toDto);
    }

    @Override
    public Optional<Outcome> findByUserId(Long id, Long userId) {
        return outcomeRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public Page<OutcomeDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(outcomeMapper::toDto);
    }

    @Override
    public Page<Outcome> findAll(Pageable pageable) {
        return outcomeRepository.findAll(pageable);
    }

    @Override
    public Optional<OutcomeDto> findOne(Long id) {
        return findById(id).map(outcomeMapper::toDto);
    }

    @Override
    public Optional<Outcome> findById(Long id) {
        return outcomeRepository.findById(id);
    }
}
