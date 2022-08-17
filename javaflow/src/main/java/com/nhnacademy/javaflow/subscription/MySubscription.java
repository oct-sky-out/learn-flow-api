package com.nhnacademy.javaflow.subscription;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.stream.LongStream;
import com.nhnacademy.javaflow.data.MyRoomTemp;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySubscription implements Subscription {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);
    private final Subscriber<? super MyRoomTemp> subscriber;
    private final String roomName;

    public MySubscription(Subscriber<? super MyRoomTemp> subscriber, String roomName) {
        this.subscriber = subscriber;
        this.roomName = roomName;
    }

    @Override
    public void cancel() {
        subscriber.onComplete();
    }

    @Override
    public void request(long n) {
        if (n > 10) {
            // 반드시 request의 요청 수 n 이하의 요청의 수만 처리 가능하다. (Flow API 규약)
            subscriber.onError(new RuntimeException("최대 처리 요청은 10입니다."));
        }

        EXECUTOR_SERVICE.submit(() -> measureMyRoomTemp(n));
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
