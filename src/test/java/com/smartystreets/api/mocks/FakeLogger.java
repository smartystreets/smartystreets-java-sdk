package com.smartystreets.api.mocks;


import com.smartystreets.api.Logger;

import java.util.ArrayList;

public class FakeLogger implements Logger{
    private ArrayList<String> log = new ArrayList<String>();

    public void log(String message) {
        this.log.add(message);
    }

    public ArrayList<String> getLog() {
        return log;
    }
}
