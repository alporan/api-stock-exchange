package com.orana.appstockexchange.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orana.appstockexchange.model.entity.Stock;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record StockDTO(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @Min(0)
        @NotNull
        BigDecimal price,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        Timestamp lastUpdate
) {

    public static StockDTO convert(Stock stock) {
        return new StockDTO(
                stock.getName(),
                stock.getDescription(),
                stock.getCurrentPrice(),
                stock.getLastUpdate()
        );
    }
}
