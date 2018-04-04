package hello.service;

import hello.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceInvoker implements CustomerService{

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Customer saveCustomer(Customer customer) {
		String url = "http://localhost:8090/provider/customer";
		return restTemplate.postForObject(url, customer, Customer.class);
	}

	@Override
	public List<Customer> getCustomers() {
		String url = "http://localhost:8090/provider/customer/all";
		return restTemplate.getForObject(url, List.class);
	}

    @Override
    public Customer updateLastName() {
        String url = "http://localhost:8090/provider/custome";
        return restTemplate.getForObject(url, Customer.class);
    }

    public String updateCustomer() {
        String url = "http://localhost:8090/provider/customer/lastname";
        String result1 = restTemplate.getForObject(url, String.class);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        String result2 = restTemplate.getForObject(url, String.class);
        return result1 + result2;
    }
}
