package com.orana.appstockexchange.repository;

import com.orana.appstockexchange.model.entity.StockExchange;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT DISTINCT se FROM StockExchange se LEFT JOIN FETCH se.stockExchangeStocks ses LEFT JOIN FETCH ses.stock WHERE se.name = :name")
    Optional<StockExchange> findStockExchangeByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT DISTINCT se FROM StockExchange se LEFT JOIN FETCH se.stockExchangeStocks ses LEFT JOIN FETCH ses.stock s WHERE s.name = :name ORDER BY se.name")
    Set<StockExchange> findStockExchangesByStockName(String name);
}