package com.myblog7.payload;

import java.util.Date;//payload information is given to response of postman

public class ErrorDetails {//Error details will hold the message that yoy want to dsplay in postman
    //these three details are communicated back in response of postman,if ypou want some  other information add other variable into object
    private Date timestamp;//@ what time exception occured
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {//this will initialize variables when i create object of ErrorDetails
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
