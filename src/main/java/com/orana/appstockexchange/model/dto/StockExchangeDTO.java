package com.orana.appstockexchange.model.dto;

import com.orana.appstockexchange.model.entity.StockExchange;

import java.util.Set;
import java.util.stream.Collectors;

public record StockExchangeDTO(String name, String description, boolean liveInMarket, Set<StockDTO> stocks) {

    public static StockExchangeDTO convert(StockExchange stockExchange) {
        return new StockExchangeDTO(
                stockExchange.getName(),
                stockExchange.getDescription(),
                stockExchange.isLiveInMarket(),
                stockExchange.getStockExchangeStocks().stream()
                        .map(ses -> StockDTO.convert(ses.getStock()))
                        .collect(Collectors.toSet())
        );
    }
}
