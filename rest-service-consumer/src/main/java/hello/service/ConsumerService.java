package hello.service;

import java.util.concurrent.CompletableFuture;

import hello.domain.Customer;

public interface ConsumerService {

	CompletableFuture<Customer> asyncInvoke();

	String getCustomers();
}
