package hello.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import hello.domain.Customer;

@Service
public class ConsumerServiceImpl implements ConsumerService{

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@Async
	public CompletableFuture<Customer> asyncInvoke() {
		String url = "http://localhost:8090/provider/customer/lastname";
		Customer customer = restTemplate.getForObject(url, Customer.class);
        return CompletableFuture.completedFuture(customer);
	}

	@Override
	public String getCustomers() {
		String url = "http://localhost:8090/provider/customer/all";
		return restTemplate.getForObject(url, String.class);
	}
	
	
}
