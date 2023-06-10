package com.study.concurrencypractice.domain;

public interface StockService {

    Stock decreaseV1(Long productId, Integer quantity);

    Stock retrieveByProductId(Long productId);
}
