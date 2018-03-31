package hello.web;

import java.io.IOException;

import javax.servlet.ServletException;

import hello.domain.Order;
import hello.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/consumer/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/transaction")
	protected boolean transaction(
			@RequestParam(value = "transaction", required=false) String transaction,
			@RequestParam(value = "channel", required=false) String channel,
			@RequestParam(value = "product", required=false) String product,
			@RequestParam(value = "amount", required=false, defaultValue="0") Integer amount) throws ServletException, IOException {
		return orderService.order(transaction, channel, product, amount);
	}
	
	@PostMapping("/transaction")
	protected boolean transaction(
			@RequestBody(required=false) Order order) throws Exception {
		return orderService.order(order);
		
	}
	
	@ExceptionHandler(Exception.class)
	public String onException(Exception e){
		return e.getMessage();
	}
}
