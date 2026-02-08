package com.basic.adminowner.exception;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(UserNotFoundException.class)
@ResponseBody
public String handleUserNotFound(UserNotFoundException ex) {
    return ex.getMessage();
}

@ExceptionHandler(InvalidLoginException.class)
@ResponseBody
public String handleInvalidLogin(InvalidLoginException ex) {
    return ex.getMessage();
}

}
