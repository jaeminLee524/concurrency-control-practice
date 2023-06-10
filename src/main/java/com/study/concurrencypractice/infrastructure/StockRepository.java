package com.study.concurrencypractice.infrastructure;

import com.study.concurrencypractice.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByProductId(long productId);
}
