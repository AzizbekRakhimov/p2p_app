package uz.azizbek.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.azizbek.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Page<Card> findAllByUserId(Long userId, Pageable pageable);
}
