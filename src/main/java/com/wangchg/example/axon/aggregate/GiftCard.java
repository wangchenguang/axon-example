package com.wangchg.example.axon.aggregate;

import com.wangchg.example.axon.command.IssueCmd;
import com.wangchg.example.axon.command.RedeemCmd;
import com.wangchg.example.axon.event.IssuedEvt;
import com.wangchg.example.axon.event.RedeemedEvt;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;

/**
 * @author wangchenguang
 * @version 1.0
 * @date 2018/12/5
 */
@NoArgsConstructor
@ToString
@Slf4j
public class GiftCard {
    @AggregateIdentifier
    private String id;
    private int remainingValue;

    @CommandHandler
    public GiftCard(IssueCmd cmd) {
        log.info(cmd.toString());
        if (cmd.getAmount() <= 0) {
            throw new IllegalArgumentException("amount <= 0");
        }
        AggregateLifecycle.apply(new IssuedEvt(cmd.getId(), cmd.getAmount()));
    }

    @EventSourcingHandler
    public void on(IssuedEvt evt) {
        log.info(evt.toString());
        id = evt.getId();
        remainingValue = evt.getAmount();
    }

    @CommandHandler
    public void handle(RedeemCmd cmd) {
        log.info(cmd.toString());
        if (cmd.getAmount() <= 0) {
            throw new IllegalArgumentException("amount <= 0");
        }
        if (cmd.getAmount() > remainingValue) {
            throw new IllegalStateException("amount > remaining value");
        }
        AggregateLifecycle.apply(new RedeemedEvt(id, cmd.getAmount()));
    }

    @EventSourcingHandler
    public void on(RedeemedEvt evt) {
        log.info(evt.toString());
        remainingValue -= evt.getAmount();
    }
}
