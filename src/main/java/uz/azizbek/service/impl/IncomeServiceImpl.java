package uz.azizbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.azizbek.model.Income;
import uz.azizbek.payload.IncomeDto;
import uz.azizbek.repository.IncomeRepository;
import uz.azizbek.service.IncomeService;
import uz.azizbek.service.mapper.IncomeMapper;

import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    IncomeMapper incomeMapper;

    @Override
    public Page<IncomeDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(incomeMapper::toDto);
    }

    @Override
    public Page<Income> findAll(Pageable pageable) {
        return incomeRepository.findAll(pageable);
    }

    @Override
    public Optional<IncomeDto> findOne(Long id) {
        return findById(id).map(incomeMapper::toDto);
    }

    @Override
    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    @Override
    public Page<IncomeDto> findByUserIdDto(Long userId, Pageable pageable) {
        return findByUserId(userId, pageable).map(incomeMapper::toDto);
    }

    @Override
    public Page<Income> findByUserId(Long userId, Pageable pageable) {
        return incomeRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public Income save(Income income) {
        return incomeRepository.save(income);
    }
}
