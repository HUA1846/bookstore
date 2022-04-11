package com.ecom.ecommerce.controller;

import com.ecom.ecommerce.entity.Customer;
import com.ecom.ecommerce.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public boolean loginUser(@RequestBody Customer customer) {
        String email = customer.getEmail();
        String password = customer.getPassword();
        return loginService.isAuthorizedUser(email, password);
    }
}
