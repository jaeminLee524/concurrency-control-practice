package com.study.concurrencypractice.application;

import com.study.concurrencypractice.domain.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockOptimisticLockFacade {

    private final StockService stockService;

    public void decreaseStockByOptimisticLock(final Long productId,final Integer quantity) {
        while (true) {
            try {
                stockService.decreaseV4(productId, quantity);
                break;
            } catch (Exception e) {
                log.info("optimistic lock version conflict");
            }
        }
    }
}
