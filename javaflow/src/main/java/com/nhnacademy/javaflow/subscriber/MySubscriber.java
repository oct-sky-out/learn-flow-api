package com.nhnacademy.javaflow.subscriber;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import com.nhnacademy.javaflow.data.MyRoomTemp;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySubscriber implements Subscriber<MyRoomTemp> {
    private Subscription myRoomSubscription;

    @Override
    public void onComplete() {
        log.info("Done execute : {}", Thread.currentThread().getName());
        // 여기선 정상종료를 수행한 뒤의 또 다른 이벤트 또는 구독 종료.
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("ERROR execute : {}", Thread.currentThread().getName());
        // 여기선 에러 관리를 위한 이벤트를 처리, 구독 종료
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
