package com.nhnacademy.javaflow.data;

import java.util.Random;

public class MyRoomTemp {
    public static final Random RANDOM = new Random();
    private final String roomName;
    private final int temp;

    public MyRoomTemp(String roomName, int temp) {
        this.roomName = roomName;
        this.temp = temp;
    }

    public static MyRoomTemp fetch(String roomName) {
        if (RANDOM.nextInt(10) == 0) {
            throw new RuntimeException("10분의 1 확률로 에러 발생");
        }

        return new MyRoomTemp(roomName, RANDOM.nextInt(50));
    }

    @Override
    public String toString() {
        return "MyRoomTemp [roomName=" + roomName
                + ", temp=" + temp
                + ", thread=" + Thread.currentThread().getName() + "]";
    }

    public String getRoomName() {
        return roomName;
    }

    public int getTemp() {
        return temp;
    }
}
