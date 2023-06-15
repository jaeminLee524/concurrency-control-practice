package com.study.concurrencypractice.infrastructure;

import com.study.concurrencypractice.domain.Stock;
import com.study.concurrencypractice.domain.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockPessimisticLockRepository stockPessimisticLockRepository;
    private final StockOptimisticLockRepository stockOptimisticLockRepository;

    @Transactional
    @Override
    public Stock decreaseV1(Long productId, Integer quantity) {
        final Stock stock = stockRepository.findByProductId(productId);
        stock.deductQuantity(quantity);

        return stock;
    }

    @Override
    public synchronized void decreaseV2(Long productId, Integer quantity) {
        final Stock stock = stockRepository.findByProductId(productId);
        stock.deductQuantity(quantity);
        stockRepository.saveAndFlush(stock);
    }

    @Override
    public Stock retrieveByProductId(Long productId) {
        return stockRepository.findByProductId(productId);
    }

    @Override
    @Transactional
    public void decreaseV3(long productId, Integer quantity) {
        final Stock stock = stockPessimisticLockRepository.findByProductId(productId);
        stock.deductQuantity(quantity);
    }

    @Override
    public void decreaseV4(Long productId, Integer quantity) {
        final Stock stock = stockOptimisticLockRepository.findByProductId(productId);
        stock.deductQuantity(quantity);
    }
}
