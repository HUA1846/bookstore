package com.ecom.ecommerce.service;

import com.ecom.ecommerce.dao.CustomerRepository;
import com.ecom.ecommerce.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
public class LoginService {

    @Autowired
    CustomerRepository customerRepository;

    public boolean isAuthorizedUser(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);

        return BCrypt.checkpw(password, customer.getPassword());
    }
}
