package uz.azizbek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.azizbek.common.ResponseData;
import uz.azizbek.payload.IncomeDto;
import uz.azizbek.service.IncomeService;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @GetMapping
    public ResponseEntity<ResponseData<Page<IncomeDto>>> findAllDto(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseData.response(incomeService.findAllDto(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<IncomeDto>> findOne(@PathVariable Long id) {
        Optional<IncomeDto> result = incomeService.findOne(id);
        return result.map(ResponseData::response).orElseGet(
                () -> ResponseData.response("Income not found", HttpStatus.BAD_REQUEST)
        );
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<ResponseData<Page<IncomeDto>>> findByUserIdDto(@PathVariable Long userId,
                                                                         @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseData.response(incomeService.findByUserIdDto(userId, pageable));
    }
}
