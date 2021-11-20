package uz.azizbek.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.azizbek.common.ResponseData;
import uz.azizbek.model.Card;
import uz.azizbek.payload.CardDto;
import uz.azizbek.service.CardService;

import java.util.Optional;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<ResponseData<CardDto>> addCard(@RequestBody CardDto cardDto) {
        return ResponseData.response(cardService.createCard(cardDto));
    }

    @GetMapping
    public ResponseEntity<ResponseData<Page<CardDto>>> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseData.response(cardService.findAllDto(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<CardDto>> findOne(@PathVariable Long id){
        Optional<CardDto> result = cardService.findOne(id);
        return result.map(ResponseData::response).orElseGet(
                () -> ResponseData.response("Card does not exist id: " + id, HttpStatus.NOT_FOUND)
        );
    }
}
