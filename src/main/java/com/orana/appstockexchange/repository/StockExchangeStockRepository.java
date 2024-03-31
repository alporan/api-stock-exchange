package com.orana.appstockexchange.repository;

import com.orana.appstockexchange.model.entity.StockExchangeStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockExchangeStockRepository extends JpaRepository<StockExchangeStock, Long> {

}