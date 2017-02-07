package com.smartystreets.api;


public class MySleeper implements Sleeper{

    public void sleep(long seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }
}
