package com.study.concurrencypractice.domain;

public interface StockService {

    void decreaseV1(Long productId, Integer quantity);

    Stock retrieveByProductId(Long productId);
}
