package com.myblog7.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)//@ResponseStatus(HttpStatus.NOT_FOUND): This annotation is applied to the ResourceNotFound class and specifies that when an instance of this exception is thrown, it should result in an HTTP response with a status code of 404 (NOT_FOUND)
public class ResourceNotFound extends RuntimeException{//when i create an object of resource not found i will supply message to this constructor

    public ResourceNotFound(String msg){


        super(msg);//sspring boot automatically understands and diplays the message in postman response
    }
}
