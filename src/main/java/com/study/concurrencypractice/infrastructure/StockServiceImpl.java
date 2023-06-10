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
    public Stock decreaseV1(Long productId, Integer quantity) {
        Stock stock = stockRepository.findByProductId(productId);
        stock.deductQuantity(quantity);

        return stock;
    }

    @Override
    public Stock retrieveByProductId(Long productId) {
        return stockRepository.findByProductId(productId);
    }
}
