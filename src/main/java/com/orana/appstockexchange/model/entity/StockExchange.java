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
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "STOCK_EXCHANGE")
public class StockExchange implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final int LIVE_IN_MARKET_LIMIT = 5;

    @Id
    @SequenceGenerator(name = "STOCK_EXCHANGE_SEQUENCE_GENERATOR", sequenceName = "STOCK_EXCHANGE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_EXCHANGE_SEQUENCE_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, precision = 19)
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false, length = 50)
    @NotNull()
    @Size(max = 50)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    @NotNull()
    @Size(max = 100)
    private String description;

    @Column(name = "LIVE_IN_MARKET", nullable = false, length = 1)
    @NotNull()
    private Boolean liveInMarket;

    @OneToMany(mappedBy = "stockExchange", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<StockExchangeStock> stockExchangeStocks;

    public StockExchange() {
    }

    public StockExchange(Long id, String name, String description, Boolean liveInMarket, Set<StockExchangeStock> stockExchangeStocks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.liveInMarket = liveInMarket;
        this.stockExchangeStocks = stockExchangeStocks;
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

    public Boolean isLiveInMarket() {
        return liveInMarket;
    }

    public void setLiveInMarket(Boolean liveInMarket) {
        this.liveInMarket = liveInMarket;
    }

    public Set<StockExchangeStock> getStockExchangeStocks() {
        return stockExchangeStocks;
    }

    public void setStockExchangeStocks(Set<StockExchangeStock> stockExchangeStocks) {
        this.stockExchangeStocks = stockExchangeStocks;
    }

    @Override
    public String toString() {
        return "StockExchange{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", liveInMarket=" + liveInMarket +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockExchange that = (StockExchange) o;
        return liveInMarket == that.liveInMarket && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, liveInMarket);
    }
}
