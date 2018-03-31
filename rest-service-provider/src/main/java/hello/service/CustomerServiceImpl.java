package hello.service;

import com.google.common.collect.Lists;
import hello.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.CustomerJPA;
import hello.repository.CustomerJPARepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired(required=false)
    private CustomerJPARepository repository;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Customer saveCustomer(Customer customer) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return repository.save((CustomerJPA) customer);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public List<Customer> getCustomers() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Lists.newArrayList(repository.findAll());
	}

    public Customer updateLastName(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CustomerJPA customer = this.repository.getByFirstName("Alice");
        customer.setLastName("Alice+" + System.currentTimeMillis());
        Customer newCustomer = this.repository.save(customer);
        return newCustomer;
    }

	@Override
	public void initialize(){
		this.repository.deleteAll();

		// save a couple of customers
		this.repository.save(new CustomerJPA("Alice", "Smith"));
		this.repository.save(new CustomerJPA("Bob", "Smith"));
		this.repository.save(new CustomerJPA("Kate", "Smith"));
		this.repository.save(new CustomerJPA("Jack", "Smith"));
		this.repository.save(new CustomerJPA("Tom", "Smith"));
		this.repository.save(new CustomerJPA("Type", "MySQL"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : this.repository.findAll()) {
			System.out.println(customer);
		}
	}
}
