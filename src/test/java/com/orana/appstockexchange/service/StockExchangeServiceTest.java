package com.orana.appstockexchange.service;

import com.orana.appstockexchange.exception.NoRecordFoundException;
import com.orana.appstockexchange.model.entity.StockExchange;
import com.orana.appstockexchange.repository.StockExchangeRepository;
import com.orana.appstockexchange.repository.StockExchangeStockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockExchangeServiceTest {
    @Mock
    private StockExchangeRepository stockExchangeRepository;
    @Mock
    private StockExchangeStockRepository stockExchangeStockRepository;
    @Mock
    private StockService stockService;

    @InjectMocks
    private StockExchangeService stockExchangeService;

    @Test
    void givenCorrectStockName_findStockByName_thenReturnUserDTO() {
        // given
        when(stockExchangeRepository.findStockExchangeByName(anyString())).thenReturn(Optional.of(new StockExchange()));

        // when
        stockExchangeService.findStockExchangeByName("SE1");

        // then
        verify(stockExchangeRepository, times(1)).findStockExchangeByName("SE1");
    }

    @Test
    void givenMissingStockExchange_whenFindStockExchangeByName_thenThrowNoRecordFoundEx() {
        // given
        when(stockExchangeRepository.findStockExchangeByName(anyString())).thenReturn(Optional.empty());

        // when
        NoRecordFoundException exception = assertThrows(NoRecordFoundException.class, () -> stockExchangeService.findStockExchangeByName("SE1"));

        // then
        assertNotNull(exception);
        verify(stockExchangeRepository, times(1)).findStockExchangeByName(anyString());
    }
}