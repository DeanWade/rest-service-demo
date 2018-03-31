package hello.service;

import hello.domain.Customer;

public interface CustomerService {

	void saveCustomer(Customer customer);
	
	void doInNoTransactional();
	
	void doInTransactional();
}
