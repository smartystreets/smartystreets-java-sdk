package com.smartystreets.api.exceptions;

public class NotModifiedException extends SmartyException {
    public NotModifiedException(){
        super();
    }
    public NotModifiedException(String message){
        super(message);
    }
}
