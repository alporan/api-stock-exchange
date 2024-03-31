package com.orana.appstockexchange.service;

import com.orana.appstockexchange.model.entity.Stock;
import com.orana.appstockexchange.repository.StockExchangeRepository;
import com.orana.appstockexchange.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {
    @Mock
    private StockRepository stockRepository;
    @Mock
    private StockExchangeRepository stockExchangeRepository;
    @InjectMocks
    private StockService stockService;

    @Test
    void givenCorrectStockName_whenFindStockByName_thenReturnUserDTO() {
        // given
        when(stockRepository.findStockByName(anyString())).thenReturn(Optional.of(new Stock()));

        // when
        stockService.findStockByName("STOCK");

        // then
        verify(stockRepository, times(1)).findStockByName("STOCK");
    }

    @Test
    void givenMissingStock_whenFindStockByName_thenThrowUsernameNotFoundEx() {
        // given
        when(stockRepository.findStockByName(anyString())).thenReturn(Optional.empty());

        // when
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> stockService.findStockByName("STOCK"));

        // then
        assertNotNull(exception);
        verify(stockRepository, times(1)).findStockByName(anyString());
    }
}