package uz.azizbek.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.azizbek.common.ResponseData;
import uz.azizbek.model.Users;
import uz.azizbek.payload.CardDto;
import uz.azizbek.service.CardService;
import uz.azizbek.service.impl.AuthService;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    private final AuthService authService;

    public CardController(CardService cardService, AuthService authService) {
        this.cardService = cardService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<ResponseData<CardDto>> addCard(@Valid @RequestBody CardDto cardDto) {
        Users principal = authService.findUserById(cardDto.getUserId());

        if (principal == null )
            return ResponseData.response("User not found", HttpStatus.BAD_REQUEST);

        return ResponseData.response(cardService.createCard(cardDto));
    }

    @GetMapping
    public ResponseEntity<ResponseData<Page<CardDto>>> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseData.response(cardService.findAllDto(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<CardDto>> findOne(@PathVariable Long id) {
        Optional<CardDto> result = cardService.findOne(id);
        return result.map(ResponseData::response).orElseGet(
                () -> ResponseData.response("Card does not exist id: " + id, HttpStatus.NOT_FOUND)
        );
    }

    @GetMapping("/getUserCard/{userId}")
    public ResponseEntity<ResponseData<Page<CardDto>>> findCardsByUserId(@PathVariable Long userId,
                                                                         @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseData.response(cardService.findCardsByUserId(userId, pageable));
    }
}
