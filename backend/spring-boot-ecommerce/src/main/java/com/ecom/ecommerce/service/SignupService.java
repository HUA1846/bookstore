package com.ecom.ecommerce.service;

import com.ecom.ecommerce.dao.CustomerRepository;
import com.ecom.ecommerce.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
public class SignupService {

    @Autowired
    private CustomerRepository customerRepository;


    public void signupCustomer(Customer customer){

        String pw_hash = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
        customer.setPassword(pw_hash);
        customerRepository.save(customer);
    }
}
