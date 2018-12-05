package com.wangchg.example.axon;

import com.wangchg.example.axon.aggregate.GiftCard;
import com.wangchg.example.axon.command.IssueCmd;
import com.wangchg.example.axon.command.RedeemCmd;
import com.wangchg.example.axon.query.CardSummary;
import com.wangchg.example.axon.query.CardSummaryProjection;
import com.wangchg.example.axon.query.FetchCardSummariesQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;

import java.util.concurrent.ExecutionException;

/**
 * @author wangchenguang
 * @version 1.0
 * @date 2018/12/5
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CardSummaryProjection projection = new CardSummaryProjection();

        EventHandlingConfiguration eventHandlingConfiguration = new EventHandlingConfiguration()
                .registerEventHandler(c -> projection);

        Configuration configuration = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(GiftCard.class)
                .configureEventStore(c -> new EmbeddedEventStore(new InMemoryEventStorageEngine()))
                .registerModule(eventHandlingConfiguration)
                .registerQueryHandler(c -> projection)
                .buildConfiguration();

        configuration.start();

        CommandGateway commandGateway = configuration.commandGateway();
        QueryGateway queryGateway = configuration.queryGateway();

        log.info("执行");
        commandGateway.sendAndWait(new IssueCmd("gc1", 100));

        log.info("执行");
        commandGateway.sendAndWait(new IssueCmd("gc2", 50));

        log.info("执行");
        commandGateway.sendAndWait(new RedeemCmd("gc1", 10));

        log.info("执行");
        commandGateway.sendAndWait(new RedeemCmd("gc2", 20));

        log.info("执行");
        queryGateway.query(new FetchCardSummariesQuery(2, 0), ResponseTypes.multipleInstancesOf(CardSummary.class))
                .get()
                .forEach(System.out::println);
    }
}
