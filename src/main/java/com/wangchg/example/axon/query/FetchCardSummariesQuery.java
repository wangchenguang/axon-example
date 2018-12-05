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
public class FetchCardSummariesQuery {
    private final Integer size;
    private final Integer offset;
}
