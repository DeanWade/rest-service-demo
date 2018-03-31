package hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.Customer;
import hello.domain.CustomerJPA;
import hello.repository.CustomerJPARepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired(required=false)
    private CustomerJPARepository repository;
	
	@Override
	public void saveCustomer(Customer customer) {
		repository.save((CustomerJPA) customer);
	}

	@Override
	@Transactional(propagation=Propagation.NEVER)
	public void doInNoTransactional() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void doInTransactional() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
