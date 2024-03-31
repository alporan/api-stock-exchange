package com.orana.appstockexchange.service;

import com.orana.appstockexchange.config.GlobalExceptionHandler;
import com.orana.appstockexchange.exception.DuplicateRecordException;
import com.orana.appstockexchange.exception.NoRecordFoundException;
import com.orana.appstockexchange.model.entity.Stock;
import com.orana.appstockexchange.model.entity.StockExchange;
import com.orana.appstockexchange.model.entity.StockExchangeStock;
import com.orana.appstockexchange.repository.StockExchangeRepository;
import com.orana.appstockexchange.repository.StockExchangeStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockExchangeService {
    private final StockExchangeRepository stockExchangeRepository;
    private final StockExchangeStockRepository stockExchangeStockRepository;
    private final StockService stockService;

    public StockExchangeService(StockExchangeRepository stockExchangeRepository, StockExchangeStockRepository stockExchangeStockRepository, StockService stockService) {
        this.stockExchangeRepository = stockExchangeRepository;
        this.stockExchangeStockRepository = stockExchangeStockRepository;
        this.stockService = stockService;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public StockExchange findStockExchangeByName(String name) {
        return stockExchangeRepository.findStockExchangeByName(name)
                .orElseThrow(() -> new NoRecordFoundException(GlobalExceptionHandler.MESSAGE_STOCK_EXCHANGE_NOT_FOUND));
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public StockExchange addStockToStockExchange(String stockExchangeName, String stockName) {
        StockExchange stockExchange = findStockExchangeByName(stockExchangeName);
        if (stockExchange.getStockExchangeStocks().stream().anyMatch(ses -> ses.getStock().getName().equals(stockName))) {
            throw new DuplicateRecordException(String.format("Stock '%s' already exist in the stock exchange '%s'.", stockName, stockExchangeName));
        }

        Stock stock = stockService.findStockByName(stockName);
        StockExchangeStock stockExchangeStock = stockExchangeStockRepository.save(new StockExchangeStock(null, stockExchange.getId(), stock.getId()));
        stockExchangeStock.setStock(stock);
        stockExchange.getStockExchangeStocks().add(stockExchangeStock);
        if (Boolean.TRUE.equals(!stockExchange.isLiveInMarket()) && stockExchange.getStockExchangeStocks().size() >= StockExchange.LIVE_IN_MARKET_LIMIT) {
            stockExchange.setLiveInMarket(true);
            stockExchange = stockExchangeRepository.save(stockExchange);
        }
        return stockExchange;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public StockExchange deleteStockFromStockExchange(String stockExchangeName, String stockName) {
        StockExchange stockExchange = findStockExchangeByName(stockExchangeName);
        StockExchangeStock stockExchangeStock = stockExchange.getStockExchangeStocks().stream()
                .filter(ses -> ses.getStock().getName().equals(stockName))
                .findAny()
                .orElseThrow(() -> new NoRecordFoundException(GlobalExceptionHandler.MESSAGE_STOCK_NOT_FOUND));

        stockExchangeStockRepository.delete(stockExchangeStock);
        stockExchange.getStockExchangeStocks().remove(stockExchangeStock);
        if (Boolean.TRUE.equals(stockExchange.isLiveInMarket()) && stockExchange.getStockExchangeStocks().size() < StockExchange.LIVE_IN_MARKET_LIMIT) {
            stockExchange.setLiveInMarket(false);
            stockExchange = stockExchangeRepository.save(stockExchange);
        }
        return stockExchange;
    }
}

