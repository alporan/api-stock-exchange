package com.orana.appstockexchange.controller.v1;


import com.orana.appstockexchange.model.dto.ResponseModel;
import com.orana.appstockexchange.model.dto.StockDTO;
import com.orana.appstockexchange.model.dto.StockUpdateRequestDTO;
import com.orana.appstockexchange.service.StockService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("v1/stock")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<ResponseModel<StockDTO>> findStockByName(@PathVariable @NotBlank String name) {
        StockDTO stockExchange = StockDTO.convert(stockService.findStockByName(name));
        return ResponseEntity.ok(new ResponseModel<>("Success", stockExchange));
    }

    @PostMapping("")
    public ResponseEntity<ResponseModel<StockDTO>> createStock(@RequestBody @Valid StockDTO stockDTO) {
        StockDTO stock = StockDTO.convert(stockService.createStock(stockDTO));
        return ResponseEntity.ok(new ResponseModel<>("Added", stock));
    }

    @PutMapping("/{name}")
    public ResponseEntity<ResponseModel<StockDTO>> updateStock(@PathVariable @NotBlank String name, @RequestBody @Valid StockUpdateRequestDTO dto) {
        StockDTO stock = StockDTO.convert(stockService.updateStock(name, dto));
        return ResponseEntity.ok(new ResponseModel<>("Updated", stock));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ResponseModel<StockDTO>> deleteStock(@PathVariable @NotBlank String name) {
        stockService.deleteStock(name);
        return ResponseEntity.ok(new ResponseModel<>("Removed", null));
    }
}
