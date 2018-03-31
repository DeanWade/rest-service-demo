package hello.web;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import hello.domain.Customer;
import hello.domain.Greeting;
import hello.service.ConsumerService;
import hello.support.RestServiceInvoker;

@RestController
@RequestMapping(value="/consumer")
public class ConsumerController {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ConsumerService consumerService;

	@GetMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String name,
			@RequestParam(value = "async", defaultValue = "false") boolean async,
			@RequestParam(value = "daemon", defaultValue = "false") boolean daemon){
		RestServiceInvoker invoker = new RestServiceInvoker(restTemplate);
		invoker.setAsync(async);
		invoker.setDaemon(daemon);
		invoker.setLock("sync");
		return invoker.greeting();
	}
	
	@GetMapping("/greeting2")
	public Greeting greeting(
    		@RequestParam(value="name", defaultValue="World") String name,
    		@RequestParam(value="lock", defaultValue="lock") String lock) {
		RestServiceInvoker invoker = new RestServiceInvoker(restTemplate);
		invoker.setAsync(false);
		invoker.setDaemon(false);
		invoker.setLock(lock);
		return invoker.greeting();
	}
	
	@GetMapping("/customer/all")
    public String getCustomers(){
		return consumerService.getCustomers();
    }
	
	@GetMapping("/customer/lastname")
    public String updateLastName(){
    	RestServiceInvoker invoker = new RestServiceInvoker(restTemplate);
    	return invoker.updateCustomer();
    }
	
	@GetMapping("/customer/lastname/async")
    public Customer syncUpdateLastName() throws Exception{
		CompletableFuture<Customer> customer = consumerService.asyncInvoke();
        try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {}
        return customer.get();
    }
	
	@PutMapping("/customer")
    public Customer creatCustomer(@RequestBody Customer customer, HttpServletRequest httpRequest) throws Exception{
        return new Customer(customer.getFirstName(), customer.getLastName());
    }
	
    @GetMapping("/exception")
    public Object exception() {
    	RestServiceInvoker invoker = new RestServiceInvoker(restTemplate);
    	return invoker.exception();
    }
	
	
	@GetMapping("/random")
	public Greeting randomUriPath() {
		return this.randomUriPath2(0);
	}
	
	@PostMapping("/random/{index}")
	public Greeting randomUriPath2(@PathVariable int index) {
		RestServiceInvoker invoker = new RestServiceInvoker(restTemplate);
		return invoker.randomUriPath(index);
	}
	
	
	@GetMapping("/random/{index}")
	protected void consumer(@PathVariable int index) throws ServletException, IOException {
		try {
			Random r = new Random();
			index = r.nextInt(50) + 1;
			if(index == 2){
				Thread.sleep(6000);
			}else{
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}
	
	@ExceptionHandler(Exception.class)
	public Greeting onException(Exception e){
		logger.error("onException", e);
		Greeting greeting = new Greeting(Long.MAX_VALUE, e.getMessage());
		return greeting;
	}
}
