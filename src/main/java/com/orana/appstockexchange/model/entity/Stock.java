package com.orana.appstockexchange.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "STOCK")
public class Stock implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "STOCK_SEQUENCE_GENERATOR", sequenceName = "STOCK_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_SEQUENCE_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, precision = 19)
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false, length = 5)
    @NotNull()
    @Size(max = 5)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    @NotNull()
    @Size(max = 100)
    private String description;

    @Column(name = "CURRENT_PRICE", nullable = false, precision = 32, scale = 5)
    @NotNull()
    private BigDecimal currentPrice;

    @Column(name = "LAST_UPDATE", nullable = false)
    @NotNull()
    private Timestamp lastUpdate;

    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<StockExchangeStock> stockExchangeStocks;

    public Stock() {
    }

    public Stock(Long id, String name, String description, BigDecimal currentPrice, Timestamp lastUpdate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentPrice = currentPrice;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set<StockExchangeStock> getStockExchangeStocks() {
        return stockExchangeStocks;
    }

    public void setStockExchangeStocks(Set<StockExchangeStock> stockExchangeStocks) {
        this.stockExchangeStocks = stockExchangeStocks;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currentPrice=" + currentPrice +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(id, stock.id) && Objects.equals(name, stock.name) && Objects.equals(description, stock.description) && Objects.equals(currentPrice, stock.currentPrice) && Objects.equals(lastUpdate, stock.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, currentPrice, lastUpdate);
    }
}
