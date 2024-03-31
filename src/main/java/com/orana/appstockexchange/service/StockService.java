package com.orana.appstockexchange.service;

import com.orana.appstockexchange.config.GlobalExceptionHandler;
import com.orana.appstockexchange.model.dto.StockDTO;
import com.orana.appstockexchange.model.dto.StockUpdateRequestDTO;
import com.orana.appstockexchange.model.entity.Stock;
import com.orana.appstockexchange.model.entity.StockExchange;
import com.orana.appstockexchange.repository.StockExchangeRepository;
import com.orana.appstockexchange.repository.StockRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Set;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final StockExchangeRepository stockExchangeRepository;

    public StockService(StockRepository stockRepository, StockExchangeRepository stockExchangeRepository) {
        this.stockRepository = stockRepository;
        this.stockExchangeRepository = stockExchangeRepository;
    }

    public Stock findStockByName(String name) {
        return stockRepository.findStockByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(GlobalExceptionHandler.MESSAGE_STOCK_NOT_FOUND));
    }

    public Stock createStock(StockDTO stockDTO) {
        Stock stock = new Stock(
                null,
                stockDTO.name(),
                stockDTO.description(),
                stockDTO.price(),
                new Timestamp(System.currentTimeMillis())
        );
        return stockRepository.save(stock);
    }

    public Stock updateStock(String name, StockUpdateRequestDTO dto) {
        Stock stock = findStockByName(name);
        stock.setCurrentPrice(dto.price());
        stock.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        return stockRepository.save(stock);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public void deleteStock(String name) {
        Set<StockExchange> stockExchanges = stockExchangeRepository.findStockExchangesByStockName(name);
        Stock stock = findStockByName(name);
        stockRepository.delete(stock);

        stockExchanges.stream()
                .filter(StockExchange::isLiveInMarket)
                .filter(se -> se.getStockExchangeStocks().size() <= StockExchange.LIVE_IN_MARKET_LIMIT)
                .forEach(se -> {
                    se.setLiveInMarket(false);
                    stockExchangeRepository.save(se);
                });
    }
}

