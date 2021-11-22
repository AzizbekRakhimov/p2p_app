package uz.azizbek.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import uz.azizbek.model.Card;
import uz.azizbek.model.Users;
import uz.azizbek.payload.CardDto;
import uz.azizbek.service.impl.UsersDetailService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


@Service
public class CardMapper {

    public CardDto toDto(Card card){
        CardDto cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setUsername(card.getUsername());
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setBalance(card.getBalance());
        cardDto.setExpireDate(card.getExpireDate());
        cardDto.setActive(card.isActive());
        cardDto.setUserId(card.getUserId());
        return cardDto;
    }

    public Card toEntity(CardDto cardDto){
        Card card = new Card();
        card.setUserId(cardDto.getUserId());
        card.setUsername(cardDto.getUsername());
        card.setExpireDate(LocalDate.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() + 126230400000L), ZoneId.systemDefault()));
        return card;
    }
}
