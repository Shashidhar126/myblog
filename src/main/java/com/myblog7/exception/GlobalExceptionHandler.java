package com.myblog7.exception;

import com.myblog7.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

//handles the exception
@ControllerAdvice//by using this annotatio u are telling spring boot that if any exception occurs in project take that exception and give it to this class
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {//responseexception handler help us to build custom exception
    @ExceptionHandler(ResourceNotFound.class)//This  is for specific  exception
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFound exception, WebRequest webRequest){//response entity returns error objects,error object has timestamp,message,details,
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),//ResourceNotFoundException exception will have exception object address,,it act as catch block,after handling exception it will return custom method
                webRequest.getDescription(false));//,web request has lot of informsation like crete error details object
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);//it help us to know at which url exception has occured
    }
    // global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
