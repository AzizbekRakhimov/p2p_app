package uz.azizbek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.azizbek.model.Income;
import uz.azizbek.model.Outcome;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {
}
