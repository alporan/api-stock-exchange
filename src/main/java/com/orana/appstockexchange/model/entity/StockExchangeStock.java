package com.orana.appstockexchange.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "STOCK_EXCHANGE_STOCK")
public class StockExchangeStock implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "STOCK_EXCHANGE_STOCK_SEQUENCE_GENERATOR", sequenceName = "STOCK_EXCHANGE_STOCK_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_EXCHANGE_STOCK_SEQUENCE_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, precision = 19)
    private Long id;

    @Column(name = "STOCK_EXCHANGE_ID", nullable = false, precision = 19)
    @NotNull()
    private Long stockExchangeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_EXCHANGE_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    private StockExchange stockExchange;

    @Column(name = "STOCK_ID", nullable = false, precision = 19)
    @NotNull()
    private Long stockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    private Stock stock;

    public StockExchangeStock() {
    }

    public StockExchangeStock(Long id, Long stockExchangeId, Long stockId) {
        this.id = id;
        this.stockExchangeId = stockExchangeId;
        this.stockId = stockId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockExchangeId() {
        return stockExchangeId;
    }

    public void setStockExchangeId(Long stockExchangeId) {
        this.stockExchangeId = stockExchangeId;
    }

    public StockExchange getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "StockExchangeStock{" +
                "id=" + id +
                ", stockExchangeId=" + stockExchangeId +
                ", stockExchange=" + stockExchange +
                ", stockId=" + stockId +
                ", stock=" + stock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockExchangeStock that = (StockExchangeStock) o;
        return Objects.equals(id, that.id) && Objects.equals(stockExchangeId, that.stockExchangeId) && Objects.equals(stockExchange, that.stockExchange) && Objects.equals(stockId, that.stockId) && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stockExchangeId, stockExchange, stockId, stock);
    }
}
