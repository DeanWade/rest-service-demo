package hello.service;

import hello.domain.Order;

public interface OrderService {

	Order order(String transaction, String channel, String product, int amount);
	
    Order orderWithJms(String transaction, String channel, String product, int amount);

	boolean order(Order order);
}
