package uz.azizbek.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.azizbek.model.Income;
import uz.azizbek.payload.IncomeDto;

import java.util.Optional;

public interface IncomeService {
    Page<IncomeDto> findAllDto(Pageable pageable);

    Page<Income> findAll(Pageable pageable);

    Optional<IncomeDto> findOne(Long id);

    Optional<Income> findById(Long id);

    Page<IncomeDto> findByUserIdDto(Long userId, Pageable pageable);

    Page<Income> findByUserId(Long userId, Pageable pageable);

    Income save(Income income);
}
