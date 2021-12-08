package com.example.producingwebservice;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import producingwebservice.example.com.calculator.*;

@Endpoint
public class CalculatorEndpoint {
	private static final String NAMESPACE_URI = "http://com.example.producingwebservice/Calculator";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRequest")
	@ResponsePayload
	public AddResponse add(@RequestPayload AddRequest input) {

		AddResponse result = new AddResponse();
		result.setResult(input.getParam1().add(input.getParam2()));

		return result;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "subtractionRequest")
	@ResponsePayload
	public SubtractionResponse subtraction(@RequestPayload SubtractionRequest input) {

		SubtractionResponse result = new SubtractionResponse();
		result.setResult(input.getParam1().subtract(input.getParam2()));

		return result;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "divideRequest")
	@ResponsePayload
	public DivideResponse divide(@RequestPayload DivideRequest input) {

		DivideResponse result = new DivideResponse();
		result.setResult(input.getParam1().divide(input.getParam2()));

		return result;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "multiplyRequest")
	@ResponsePayload
	public MultiplyResponse multiply(@RequestPayload MultiplyRequest input) {

		MultiplyResponse result = new MultiplyResponse();
		result.setResult(input.getParam1().multiply(input.getParam2()));

		return result;
	}
}
