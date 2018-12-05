package com.wangchg.example.axon.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author wangchenguang
 * @version 1.0
 * @date 2018/12/5
 */
@Getter
@AllArgsConstructor
@ToString
public class IssueCmd {
    private final String id;
    private final Integer amount;
}
