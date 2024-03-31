package com.orana.appstockexchange.controller.v1;


import com.orana.appstockexchange.model.dto.ResponseModel;
import com.orana.appstockexchange.model.dto.StockExchangeDTO;
import com.orana.appstockexchange.service.StockExchangeService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("v1/stock-exchange")
public class StockExchangeController {
    private final StockExchangeService stockExchangeService;

    public StockExchangeController(StockExchangeService stockExchangeService) {
        this.stockExchangeService = stockExchangeService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<ResponseModel<StockExchangeDTO>> findStockExchangeByName(@PathVariable @NotBlank String name) {
        StockExchangeDTO stockExchange = StockExchangeDTO.convert(stockExchangeService.findStockExchangeByName(name));
        return ResponseEntity.ok(new ResponseModel<>("Success", stockExchange));
    }

    @PostMapping("/{stockExchangeName}/stock/{stockName}")
    public ResponseEntity<ResponseModel<StockExchangeDTO>> addStockToStockExchange(@PathVariable @NotBlank String stockExchangeName, @PathVariable @NotBlank String stockName) {
        StockExchangeDTO stockExchange = StockExchangeDTO.convert(stockExchangeService.addStockToStockExchange(stockExchangeName, stockName));
        return ResponseEntity.ok(new ResponseModel<>("Added", stockExchange));
    }

    @DeleteMapping("/{stockExchangeName}/stock/{stockName}")
    public ResponseEntity<ResponseModel<StockExchangeDTO>> deleteStockFromStockExchange(@PathVariable @NotBlank String stockExchangeName, @PathVariable @NotBlank String stockName) {
        StockExchangeDTO stockExchange = StockExchangeDTO.convert(stockExchangeService.deleteStockFromStockExchange(stockExchangeName, stockName));
        return ResponseEntity.ok(new ResponseModel<>("Removed", stockExchange));
    }
}
