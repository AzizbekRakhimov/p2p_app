package uz.azizbek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.azizbek.common.ResponseData;
import uz.azizbek.config.jwt.JwtProvider;
import uz.azizbek.model.Card;
import uz.azizbek.model.Users;
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
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (toCard.isEmpty() || fromCard.isEmpty())
            return ResponseData.response("FromCard or ToCard does not exist", HttpStatus.BAD_REQUEST);

        if (!fromCard.get().getUserId().equals(users.getId()))
            return ResponseData.response("This is not your card", HttpStatus.BAD_REQUEST);

        if (!cardService.canTransfer(fromCard.get().getId(), outcomeDto.getAmount()))
            return ResponseData.response("Not balance", HttpStatus.BAD_REQUEST);

        if (toCard.get().getId().equals(fromCard.get().getId()))
            return ResponseData.response("Can not transaction", HttpStatus.BAD_REQUEST);

        outcomeDto.setUserId(users.getId());

        Optional<OutcomeDto> result = outcomeService.sendTo(outcomeDto, toCard.get(), fromCard.get());

        return result.map(ResponseData::response).orElseGet(
                () -> ResponseData.response("Transaction failed", HttpStatus.BAD_REQUEST)
        );
    }

    @GetMapping("/findUserOutcome")
    public ResponseEntity<ResponseData<Page<OutcomeDto>>> findByUserId(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Users principal = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseData.response(outcomeService.findByUserId(principal.getId(), pageable));
    }

    @GetMapping("/findUserOutcome/{id}")
    public ResponseEntity<ResponseData<OutcomeDto>> findOneByUserId(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long id) {
        Users principal = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<OutcomeDto> result = outcomeService.findOneByUserId(id, principal.getId());
        return result.map(ResponseData::response).orElseGet(
                () -> ResponseData.response("Outcome does not exist", HttpStatus.BAD_REQUEST)
        );
    }

    @GetMapping
    public ResponseEntity<ResponseData<Page<OutcomeDto>>> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseData.response(outcomeService.findAllDto(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<OutcomeDto>> findOne(@PathVariable Long id){
        Optional<OutcomeDto> result = outcomeService.findOne(id);
        return result.map(ResponseData::response).orElseGet(
                () -> ResponseData.response("Outcome not found", HttpStatus.BAD_REQUEST)
        );
    }
}
