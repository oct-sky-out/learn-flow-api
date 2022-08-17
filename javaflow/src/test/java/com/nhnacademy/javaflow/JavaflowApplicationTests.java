package com.nhnacademy.javaflow;

import java.util.concurrent.Flow.Publisher;
import org.junit.jupiter.api.Test;
import com.nhnacademy.javaflow.data.MyRoomTemp;
import com.nhnacademy.javaflow.reactor.MyReactor;
import com.nhnacademy.javaflow.subscriber.MySubscriber;
import com.nhnacademy.javaflow.subscription.MySubscription;

class JavaflowApplicationTests {

	@Test
	public void test1() throws InterruptedException {
		Publisher<MyRoomTemp> myRoomTempPublisher = (subscriber) -> subscriber
				.onSubscribe(new MySubscription(subscriber, "NHN Acacdemy A Class"));

		MySubscriber subscriber1 = new MySubscriber();
		MySubscriber subscriber2 = new MySubscriber();
		MySubscriber subscriber3 = new MySubscriber();

		myRoomTempPublisher.subscribe(subscriber2);
		myRoomTempPublisher.subscribe(subscriber1);
		myRoomTempPublisher.subscribe(subscriber3);
	}

	@Test
	public void test2() {
		MyReactor myReactor = new MyReactor();
		myReactor.runMyReactor();
	}

	@Test
	public void test3() {
		MyReactor myReactor = new MyReactor();
		myReactor.tempRecator();
	}
}
