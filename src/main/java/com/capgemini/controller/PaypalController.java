package com.capgemini.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.model.CompletePaymentRequest;
import com.capgemini.model.CompletePaymentResponse;
import com.capgemini.model.PaymentRequest;
import com.capgemini.model.PaymentResponse;
import com.capgemini.service.PaypalClient;
import com.paypal.api.payments.Payment;

@RestController
@RequestMapping(value = "/paypal")
public class PaypalController {
	private final PaypalClient paypalClient;
	private Map<String,Object> response = new HashMap<>();
	
	@Autowired
	PaypalController(PaypalClient paypalClient) {
		this.paypalClient = paypalClient;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/make/payment")
	public ResponseEntity<?> makePayment(@RequestBody PaymentRequest paymentRequest) {
		response = paypalClient.createPayment(paymentRequest);
		return new ResponseEntity<PaymentResponse>(new PaymentResponse((String)response.get("redirect_url"), (String)response.get("status")), 
        		HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/complete/payment")
	public ResponseEntity<?> completePayment(@RequestBody CompletePaymentRequest completePaymentRequest){
		response = paypalClient.completePayment(completePaymentRequest);
		return new ResponseEntity<CompletePaymentResponse>(new CompletePaymentResponse((String)response.get("status"), (Payment)response.get("payment")), 
        		HttpStatus.OK);
	}
}
