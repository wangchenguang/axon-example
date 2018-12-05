package com.wangchg.example.axon.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author wangchenguang
 * @version 1.0
 * @date 2018/12/5
 */
@AllArgsConstructor
@Getter
@ToString
public class IssuedEvt {
    @TargetAggregateIdentifier
    private final String id;
    private final Integer amount;
}
