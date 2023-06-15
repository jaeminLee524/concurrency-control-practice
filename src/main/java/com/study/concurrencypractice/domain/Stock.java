package com.study.concurrencypractice.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long productId;

    private Integer quantity;

    @Version
    private Long version;

    @Builder
    private Stock(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static Stock create(Long productId, Integer quantity) {
        return Stock.builder()
            .productId(productId)
            .quantity(quantity)
            .build();
    }

    public void deductQuantity(Integer quantity) {
        if (this.isQuantityLessThan(quantity)) {
            throw new IllegalArgumentException("재고가 부족하여 재고 감소를 할 수 없습니다.");
        }
        this.quantity -= quantity;
    }

    public boolean isQuantityLessThan(int quantity) {
        return this.quantity < quantity;
    }
}
