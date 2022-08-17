package com.nhnacademy.javaflow.reactor.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import com.nhnacademy.javaflow.data.MyRoomTemp;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyReactorSubscriber implements Subscriber<MyRoomTemp> {
    private Subscription myRoomSubscription;

    @Override
    public void onComplete() {
        log.info("Done execute : {}", Thread.currentThread().getName());
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("ERROR execute : {}", Thread.currentThread().getName());
    }

    @Override
    public void onNext(MyRoomTemp item) {
        myRoomSubscription.request(1);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.myRoomSubscription = subscription;
        myRoomSubscription.request(1);
    }
}
