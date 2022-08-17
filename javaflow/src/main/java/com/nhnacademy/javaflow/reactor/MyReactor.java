package com.nhnacademy.javaflow.reactor;

import java.util.List;

import org.reactivestreams.Publisher;

import com.nhnacademy.javaflow.reactor.subscription.MyReactorSubscription;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class MyReactor {
    public void runMyReactor() {
        List<Integer> intFlux = Flux.range(1, 4)
                .concatWithValues(0, 1, 2, 3)
                .map((t) -> t >= 3 ? t + 1 : 0)
                .collectList()
                .block();

        log.info("result : {} ", intFlux);
    }

    public void tempRecator() {
        Publisher<? super Object> rPublisher = (subscriber) -> {
            MyReactorSubscription subscription = new MyReactorSubscription(subscriber, "NHN Acadmey class B");
            subscriber.onSubscribe(subscription);
        };

        Flux.from(rPublisher)
                .onBackpressureError()
                .subscribe();
    }
}
