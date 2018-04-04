package hello.service;

import hello.domain.Order;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceInvoker implements OrderService {

	private static String[] tansactionCode = { "AddToCart", "Checkout", "ConfirmOrder", "DoPayment", "ViewOrder" };
	
	private static String[] channelCodes = { "mobile", "weixin", "alipay", "web" };

	private static String[] productCodes = { "computer", "cloth", "makup", "food", "book", "flight" };
	
	private static Boolean[] results = { Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,Boolean.FALSE };

	@Autowired(required=false)
	private RestTemplate restTemplate;

	@Autowired(required=false)
	private JmsTemplate jmsTemplate;
	
	@Override
	public Order order(String transaction, String channel, String product, int amount) {
		Order order = null;
		if(transaction == null){
			order = randomOrder();
		}else{
			order = new Order(transaction, channel, product, amount, results[RandomUtils.nextInt(7)]);
		}
		doOrder(order.getTransaction(), order.getChannel(), order.getProduct(), order.getAmount(), order.isResult());
		order = doRemoteOrder(order);
		return order;
	}

	@Override
	public Order orderWithJms(String transaction, String channel, String product, int amount) {
		Order order = null;
		if(transaction == null){
			order = randomOrder();
		}else{
			order = new Order(transaction, channel, product, amount, results[RandomUtils.nextInt(7)]);
		}
		doOrder(order.getTransaction(), order.getChannel(), order.getProduct(), order.getAmount(), order.isResult());
		doJmsOrder(order);
		return order;
	}

	public boolean doOrder(String transaction, String channel, String product, int amount, boolean result) {
		try {
			Thread.sleep(amount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean order(Order order) {
		if(order == null || order.getTransaction() == null){
			order = randomOrder();
		}
		order = doRemoteOrder(order);
		return order.isResult();
	}
	
	private Order randomOrder() {
		Order order = new Order(
						tansactionCode[RandomUtils.nextInt(5)],
                        channelCodes[RandomUtils.nextInt(4)],
                        productCodes[RandomUtils.nextInt(6)],
                        RandomUtils.nextInt(200),
                        results[RandomUtils.nextInt(7)]);
		return order;
	}

	public Order doRemoteOrder(Order order) {
		String url = "http://localhost:8090/provider/order/transaction";
		Order result = restTemplate.postForObject(url, order, Order.class);
        return result;
	}

	public void doJmsOrder(Order order) {
		jmsTemplate.convertAndSend("order.transaction", order);
	}

}
