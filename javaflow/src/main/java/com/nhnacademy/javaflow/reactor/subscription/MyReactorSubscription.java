package com.nhnacademy.javaflow.reactor.subscription;

import java.util.stream.LongStream;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import com.nhnacademy.javaflow.data.MyRoomTemp;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyReactorSubscription implements Subscription {
    private final Subscriber<? super MyRoomTemp> subscriber;
    private final String roomName;

    public MyReactorSubscription(Subscriber<? super MyRoomTemp> subscriber, String roomName) {
        this.subscriber = subscriber;
        this.roomName = roomName;
    }

    @Override
    public void cancel() {
        subscriber.onComplete();
    }

    @Override
    public void request(long n) {
        measureMyRoomTemp(n);
    }

    private void measureMyRoomTemp(long n) {
        LongStream.range(0, n).takeWhile(i -> {
            try {
                MyRoomTemp tempInfo = MyRoomTemp.fetch(roomName);
                log.info("{}", tempInfo);

                subscriber.onNext(tempInfo);
            } catch (RuntimeException e) {
                subscriber.onError(e);
                return false;
            }
            return true;
        }).forEach(value -> {
        });
    }
}
