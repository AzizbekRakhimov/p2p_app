package uz.azizbek.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.azizbek.model.Income;
import uz.azizbek.model.Outcome;

import java.util.Optional;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

    Page<Outcome> findByUserId(Long userId, Pageable pageable);

    Optional<Outcome> findByIdAndUserId(Long id, Long userId);
}
