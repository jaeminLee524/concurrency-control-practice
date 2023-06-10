package com.study.concurrencypractice.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

    @DisplayName("요청한 재고만큼 기존 재고에서 차감을 진행한다.")
    @Test
    void deductQuantity() {
        // given
        Integer quantity = 1;
        Stock stock = Stock.create(1L, 1);

        // when
        stock.deductQuantity(quantity);

        // then
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("가지고 있는 재고 수량이 요청받은 재고보다 적은지 확인.")
    @Test
    void isQuantityLessThan() {
        // given
        int quantity = 2;
        Stock stock = Stock.create(1L, 1);

        // when
        boolean result = stock.isQuantityLessThan(2);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("가지고 있는 재고보다 많은 재고 차감 요청의 경우 throw 를 던진다.")
    @Test
    void deductQuantity2() {
        // given
        int quantity = 3;
        Stock stock = Stock.create(1L, 2);

        // when && then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("재고가 부족하여 재고 감소를 할 수 없습니다.");
    }
}