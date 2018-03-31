package hello.service;

import hello.domain.Customer;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    List<Customer> getCustomers();

    Customer updateLastName();

    default void initialize() {};
}
