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

    @Transactional
    @Override
    public void decreaseV1(Long productId, Integer quantity) {
        Stock findStock = stockRepository.findByProductId(productId);
        findStock.deductQuantity(quantity);
    }

    @Override
    public Stock retrieveByProductId(Long productId) {
        return stockRepository.findByProductId(productId);
    }
}
