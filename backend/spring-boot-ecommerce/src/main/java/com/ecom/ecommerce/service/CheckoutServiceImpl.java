package com.ecom.ecommerce.service;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import com.ecom.ecommerce.dao.CustomerRepository;
import com.ecom.ecommerce.dto.PaymentInfo;
import com.ecom.ecommerce.dto.Purchase;
import com.ecom.ecommerce.dto.PurchaseResponse;
import com.ecom.ecommerce.entity.Address;
import com.ecom.ecommerce.entity.Customer;
import com.ecom.ecommerce.entity.Order;
import com.ecom.ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService{
    private CustomerRepository customerRepository;

  // @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        /* billing/shipping address
        *  4. populate customer with order
        *  5. save to the database
        *  6. return a response
        * */
        // retrieve the order info from dto
        Order order = purchase.getOrder();
        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //populate order with order items
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // set billing/shipping address
        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());

        // populate customer with order and check if
        // the customer exists based on the email
        // if exists, assign it to the customer variable
        Customer customer = purchase.getCustomer();
        String email = customer.getEmail();
        Customer customerByEmail = customerRepository.findByEmail(email);

        if(customerByEmail != null) {
            customer = customerByEmail;
        }

        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
/* from stripe*/
//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(paymentInfo.getAmount())
//                        .setCurrency(paymentInfo.getCurrency())
//                        .addPaymentMethodType("card")
//                        .setStatementDescriptor("Custom descriptor")
//                        .build();
//
//        PaymentIntent paymentIntent = PaymentIntent.create(params);
//        return paymentIntent;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);

    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID (version 4)
        return UUID.randomUUID().toString();
    }


}
