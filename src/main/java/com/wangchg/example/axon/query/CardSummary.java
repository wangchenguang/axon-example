package com.wangchg.example.axon.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author wangchenguang
 * @version 1.0
 * @date 2018/12/5
 */
@AllArgsConstructor
@Getter
@ToString
public class CardSummary {
    private final String id;
    private final Integer initialAmount;
    private final Integer remainingAmount;

    public CardSummary deductAmount(Integer toBeDeducted) {
        return new CardSummary(id, initialAmount, remainingAmount - toBeDeducted);
    }

}
