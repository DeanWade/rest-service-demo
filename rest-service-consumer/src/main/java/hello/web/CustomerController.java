package hello.web;

import hello.domain.Customer;
import hello.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value="/consumer/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/random")
    public Customer randomCustomer() throws Exception{
        return new Customer();
    }
}
