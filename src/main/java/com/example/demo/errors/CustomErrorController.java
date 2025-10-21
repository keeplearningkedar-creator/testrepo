package com.example.demo.errors;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

/*    @RequestMapping("/error")
    public ResponseEntity<String> handleError() {
        // Custom logic for handling errors
        return ResponseEntity.status(HttpStatus.OK).body("Error Handerler Response"); // Name of your custom error view (e.g., custom-error-page.html)
    }*/
}
