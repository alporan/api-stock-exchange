package com.orana.appstockexchange.repository;

import com.orana.appstockexchange.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s WHERE s.name = :name")
    Optional<Stock> findStockByName(String name);

}