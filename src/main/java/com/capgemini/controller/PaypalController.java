package com.capgemini.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.model.CompletePaymentRequest;
import com.capgemini.model.PaymentRequest;
import com.capgemini.service.PaypalClient;

@RestController
@RequestMapping(value = "/paypal")
public class PaypalController {
	private final PaypalClient paypalClient;
	
	@Autowired
	PaypalController(PaypalClient paypalClient) {
		this.paypalClient = paypalClient;
	}
	
	@PostMapping(value = "/make/payment")
	public Map<String, Object> makePayment(@RequestBody PaymentRequest paymentRequest) {
		return paypalClient.createPayment(paymentRequest);
	}
	
	@PostMapping(value = "/complete/payment")
	public Map<String, Object> completePayment(@RequestBody CompletePaymentRequest completePaymentRequest){
		Map<String,Object> response = new HashMap<>();
		response = paypalClient.completePayment(completePaymentRequest);
	    return response;
	}
}
