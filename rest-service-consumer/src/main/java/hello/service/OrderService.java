package hello.service;

import hello.domain.Order;

public interface OrderService {

	boolean order(String transaction, String channel, String product, int amount);
	
	boolean order(Order order);
	
}
