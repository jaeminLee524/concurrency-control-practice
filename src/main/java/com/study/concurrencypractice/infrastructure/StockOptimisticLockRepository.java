package com.study.concurrencypractice.infrastructure;

import com.study.concurrencypractice.domain.Stock;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface StockOptimisticLockRepository extends JpaRepository<Stock, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Stock findByProductId(Long productId);
}
