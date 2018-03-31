package hello.web;

import hello.domain.Customer;
import hello.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value="/consumer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/all")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/customer/random")
    public Customer randomCustomer() throws Exception{
        return new Customer();
    }
}
