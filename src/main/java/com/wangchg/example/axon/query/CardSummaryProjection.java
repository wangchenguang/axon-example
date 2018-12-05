package com.wangchg.example.axon.query;

import com.wangchg.example.axon.event.IssuedEvt;
import com.wangchg.example.axon.event.RedeemedEvt;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author wangchenguang
 * @version 1.0
 * @date 2018/12/5
 */
@Slf4j
public class CardSummaryProjection {
    private final List<CardSummary> cardSummaries = new CopyOnWriteArrayList<>();

    @EventHandler
    public void on(IssuedEvt evt) {
        log.info(evt.toString());
        CardSummary cardSummary = new CardSummary(evt.getId(), evt.getAmount(), evt.getAmount());
        cardSummaries.add(cardSummary);
    }

    @EventHandler
    public void on(RedeemedEvt evt) {
        log.info(evt.toString());
        cardSummaries.stream()
                .filter(cs -> evt.getId().equals(cs.getId()))
                .findFirst()
                .ifPresent(cardSummary -> {
                    CardSummary updatedCardSummary = cardSummary.deductAmount(evt.getAmount());
                    cardSummaries.remove(cardSummary);
                    cardSummaries.add(updatedCardSummary);
                });
    }

    @QueryHandler
    public List<CardSummary> fetch(FetchCardSummariesQuery query) {
        log.info(query.toString());
        return cardSummaries.stream()
                .skip(query.getOffset())
                .limit(query.getSize())
                .collect(Collectors.toList());
    }
}
