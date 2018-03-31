package hello.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Serializable {

	private String transaction;
	
	private String channel;
	
	private String product;
	
	private int amount;
	
	private boolean result;

	public Order(){

	}
	public Order(String transaction, String channel, String product, int amount, boolean result) {
		super();
		this.transaction = transaction;
		this.channel = channel;
		this.product = product;
		this.amount = amount;
		this.result = result;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Order{" +
				"transaction='" + transaction + '\'' +
				", channel='" + channel + '\'' +
				", product='" + product + '\'' +
				", amount=" + amount +
				", result=" + result +
				'}';
	}
}
