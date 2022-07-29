package com.smartystreets.api.mocks;


import com.smartystreets.api.Sleeper;

import java.util.ArrayList;

public class FakeSleeper implements Sleeper{
    private ArrayList<Long> sleepDurations = new ArrayList<>();

    public void sleep(long seconds) throws InterruptedException {
        this.sleepDurations.add(seconds);
    }

    public ArrayList<Long> getSleepDurations() {
        return this.sleepDurations;
    }
}
