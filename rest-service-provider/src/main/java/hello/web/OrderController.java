package hello.web;

import hello.domain.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.MyException;

@RestController
@RequestMapping(value="/provider/order", produces = "application/json")
public class OrderController {

	@PostMapping("/transaction")
	protected Order transaction(@RequestBody Order order){
		try {
			Thread.sleep(order.getAmount());
		} catch (Exception e) {
		}
		if(!order.isResult()){
			throw new MyException("Order Exception");
		}
		return order;
		
	}
	
}
