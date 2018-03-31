package hello.web;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.domain.Customer;
import hello.domain.CustomerJPA;
import hello.repository.CustomerJPARepository;
import hello.service.CustomerService;

@RestController
@RequestMapping(value="/provider/", produces = "application/json")
public class CustomerController {
	
	@Autowired(required=false)
    private CustomerJPARepository repository;
	
	@Autowired(required=false)
	private CustomerService customerService;
	
	@GetMapping("/transaction/no")
    public void doInNoTransactional(){
		customerService.doInNoTransactional();
    }
	
	@GetMapping("/transaction/yes")
    public void doInTransactional(){
		customerService.doInNoTransactional();
    }

	@PutMapping("/customer")
    public Customer save(
    		@RequestParam(value="firstname", defaultValue="firstname") String firstname,
    		@RequestParam(value="lastname", defaultValue="lastname") String lastname){
		CustomerJPA customer = createCustomer(firstname, lastname);
    	return getRepository().save(customer);
    }
    
	@GetMapping("/customer/all")
    public Iterable<CustomerJPA> findAll(){
		return getRepository().findAll();
    }
	
	@GetMapping("/customer/memory")
    public List<Customer> memory(){
		List<Customer> customers = new LinkedList<Customer>();
		for(int i =0; i<1000; i++){
			Customer customer = new Customer();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
			customers.add(customer);
		}
		return customers;
    }
	
	@GetMapping("/customer/cpu")
    public void cpu(){
		for(int i =0; i<10; i++){
			try {
				Math.atan(Double.MAX_VALUE);
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
		}
		return;
    }
	
	
	@GetMapping("/customer/lastname")
    public Customer updateLastName(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CustomerJPA customer = this.repository.getByFirstName("Alice");
		customer.setLastName("" + System.currentTimeMillis());
		Customer newCustomer = this.repository.save(customer);
		return newCustomer;
    }

	@GetMapping("/customer/init")
    public String init(){
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
		return "init successfully";
    }
	
	protected CrudRepository<CustomerJPA, String> getRepository() {
		return repository;
	}
	
	protected CustomerJPA createCustomer(String firstName, String lastName) {
		return new CustomerJPA(firstName, lastName);
	}
	
}
