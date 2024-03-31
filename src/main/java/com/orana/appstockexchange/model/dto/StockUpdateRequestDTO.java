package com.orana.appstockexchange.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record StockUpdateRequestDTO(@NotNull @Min(0) BigDecimal price) {
}
