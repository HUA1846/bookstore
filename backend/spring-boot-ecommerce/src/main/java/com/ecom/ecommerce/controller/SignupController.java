package com.ecom.ecommerce.controller;


import com.ecom.ecommerce.entity.Customer;
import com.ecom.ecommerce.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignupController {

    @Autowired
    private SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/signup")
    public void signupCustomer(@RequestBody Customer customer) {
        signupService.signupCustomer(customer);
    }
}
