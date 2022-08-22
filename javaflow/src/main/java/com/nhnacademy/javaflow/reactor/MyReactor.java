package com.nhnacademy.javaflow.reactor;

import java.time.Duration;
import java.util.List;

import org.reactivestreams.Publisher;
import com.nhnacademy.javaflow.data.MyRoomTemp;
import com.nhnacademy.javaflow.reactor.subscription.MyReactorSubscription;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class MyReactor {
    public void runMyReactor() {
        Flux<Integer> flux = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(500))
                .concatWithValues(0, 1, 2, 3, 4, 5)
                .map((t) -> t >= 3 ? t + 1 : 0)
                .filter((t) -> t >= 5);

        flux.subscribe((t) -> log.info("result : {} ", t));

        List<Integer> integers = flux.delayElements(Duration.ofMillis(500))
                .collectList()
                .block();
        log.info("result : {} ", integers);
    }

    public void tempRecator() {
        Publisher<MyRoomTemp> roomPublisher = (subscriber) -> {
            MyReactorSubscription subscription = new MyReactorSubscription(subscriber, "NHN Acadmey class B");
            subscriber.onSubscribe(subscription);
        };

        Flux.from(roomPublisher)
                .parallel(2)
                .runOn(Schedulers.parallel())
                .subscribe();
    }
}
