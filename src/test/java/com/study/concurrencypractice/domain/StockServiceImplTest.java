package com.study.concurrencypractice.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.study.concurrencypractice.infrastructure.StockRepository;
import com.study.concurrencypractice.infrastructure.StockServiceImpl;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("test")
@SpringBootTest
class StockServiceImplTest {

    @Autowired
    private StockServiceImpl stockServiceImpl;
    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void tearDown() {
        stockRepository.deleteAllInBatch();
    }

    @DisplayName("주어진 수량으로 재고 개수를 차감한다.")
    @Test
    void decreaseStock() {
        // given
        final long productId = 1L;
        final int quantity = 1;

        stockRepository.save(Stock.create(productId, 100));

        // when
        Stock stock = stockServiceImpl.decreaseV1(productId, quantity);

        // then
        assertThat(stock.getQuantity()).isEqualTo(99L);
    }

    @DisplayName("재고 감소 V1 - 동시에 100개의 요청이 들어왔을 때의 재고 감소 현황")
    @Test
    void stock_decreaseV1_concurrency() throws InterruptedException {
        // given
        final long productId = 1L;
        final int quantity = 1;
        stockRepository.save(Stock.create(productId, 100));

        final int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        IntStream.range(0, 100).forEach(e -> executorService.submit(() -> {
            try {
                stockServiceImpl.decreaseV1(productId, quantity);
            } finally {
                countDownLatch.countDown();
            }
        }));

        countDownLatch.await();

        // then
        final Integer afterQuantity = stockRepository.findByProductId(productId).getQuantity();
        System.out.println("### afterQuantity=" + afterQuantity);
        assertThat(afterQuantity).isNotZero();
    }

    @DisplayName("재고 감소 V2 - 동시에 100개의 요청이 들어왔을 때의 재고 감소 현황")
    @Test
    void stock_decreaseV2_concurrency() throws InterruptedException {
        // given
        final long productId = 1L;
        final int quantity = 1;
        stockRepository.save(Stock.create(productId, 100));

        final int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        IntStream.range(0, 100).forEach(e -> executorService.submit(() -> {
           try {
               stockServiceImpl.decreaseV2(productId, quantity);
           } finally {
               countDownLatch.countDown();
           }
        }));

        countDownLatch.await();

        // then
        Stock stock = stockRepository.findByProductId(productId);
        assertThat(stock.getQuantity()).isZero();
    }

    
}