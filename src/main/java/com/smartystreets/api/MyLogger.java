package com.smartystreets.api;


public class MyLogger implements Logger{
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
