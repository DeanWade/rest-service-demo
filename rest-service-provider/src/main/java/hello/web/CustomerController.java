package hello.web;

import java.util.LinkedList;
import java.util.List;

import hello.domain.Customer;
import hello.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import hello.domain.CustomerJPA;
import hello.repository.CustomerJPARepository;

@RestController
@RequestMapping(value="/provider/", produces = "application/json")
public class CustomerController {
	
	@Autowired(required=false)
	private CustomerService customerService;

	@PutMapping("/customer")
    public Customer save(
    		@RequestParam(value="firstname", defaultValue="firstname") String firstname,
    		@RequestParam(value="lastname", defaultValue="lastname") String lastname){
		Customer customer = new CustomerJPA(firstname, lastname);
    	return customerService.saveCustomer(customer);
    }
    
	@GetMapping("/customer/all")
    public List<Customer> getCustomers(){
		return customerService.getCustomers();
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
	
	
	@PostMapping("/customer/lastname")
    public Customer updateLastName(){
		return customerService.updateLastName();
    }

	@GetMapping("/customer/initialize")
    public void initialize() {
		this.customerService.initialize();
	}
	
}
