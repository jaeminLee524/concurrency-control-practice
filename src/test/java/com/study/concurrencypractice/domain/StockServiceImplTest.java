package com.study.concurrencypractice.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.study.concurrencypractice.infrastructure.StockRepository;
import com.study.concurrencypractice.infrastructure.StockServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockServiceImplTest {

    @Autowired
    private StockServiceImpl stockServiceImpl;
    @Autowired
    private StockRepository stockRepository;

    private final long productID = 1L;
    private final int quantity = 1;

    @BeforeEach
    void setUp() {
        stockRepository.save(Stock.create(productID, 100));
    }

    @AfterEach
    void tearDown() {
        stockRepository.deleteAllInBatch();
    }

    @DisplayName("주어진 수량으로 재고 개수를 차감한다.")
    @Test
    void decreaseStock() {
        // given

        // when
        stockServiceImpl.decreaseV1(productID, quantity);

        // then
        Stock stock = stockServiceImpl.retrieveByProductId(productID);

        final Integer afterQuantity = stock.getQuantity();

        assertThat(afterQuantity).isEqualTo(99L);
    }
}