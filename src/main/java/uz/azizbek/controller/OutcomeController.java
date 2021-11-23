package uz.azizbek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.azizbek.common.ResponseData;
import uz.azizbek.config.jwt.JwtProvider;
import uz.azizbek.model.Card;
import uz.azizbek.payload.OutcomeDto;
import uz.azizbek.service.CardService;
import uz.azizbek.service.OutcomeService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {

    @Autowired
    OutcomeService outcomeService;

    @Autowired
    CardService cardService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<ResponseData<OutcomeDto>> sendTo(@Valid @RequestBody OutcomeDto outcomeDto) {

        Optional<Card> toCard = cardService.findById(outcomeDto.getToCard().getId());
        Optional<Card> fromCard = cardService.findById(outcomeDto.getFromCard().getId());

        if (toCard.isEmpty() || fromCard.isEmpty())
            return ResponseData.response("FromCard or ToCard does not exist", HttpStatus.BAD_REQUEST);

        if (!cardService.canTransfer(fromCard.get().getId(), outcomeDto.getAmount()) || toCard.get().getId() == fromCard.get().getId())
            return ResponseData.response("Can not", HttpStatus.BAD_REQUEST);


        Optional<OutcomeDto> result = outcomeService.sendTo(outcomeDto, toCard.get(), fromCard.get());

        return result.map(ResponseData::response).orElseGet(
                () -> ResponseData.response("Transaction failed", HttpStatus.BAD_REQUEST)
        );
    }
}
