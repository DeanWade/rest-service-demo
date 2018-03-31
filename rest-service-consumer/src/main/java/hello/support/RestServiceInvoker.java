package hello.support;

import java.util.Random;

import org.springframework.web.client.RestTemplate;

import hello.domain.Greeting;

public class RestServiceInvoker {

	private RestTemplate restTemplate;
	
	private boolean daemon;
	
	private boolean async;
	
	private String lock;
	
	public RestServiceInvoker(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public Greeting randomUriPath(int index) {
		if(index == 0){
			Random r = new Random();
			index = r.nextInt(10) + 1;
		}
		String url = "http://localhost:8090/provider/random/{index}";
		Greeting greeting = restTemplate.getForObject(url, Greeting.class, index);
		return greeting;
	}
	
	public String updateCustomer() {
		String url = "http://localhost:8090/provider/customer/lastname";
		String result1 = restTemplate.getForObject(url, String.class);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		String result2 = restTemplate.getForObject(url, String.class);
		return result1 + result2;
	}
	
	public Object exception() {
		String url = "http://localhost:8090/provider/exception";
		return restTemplate.getForObject(url, Object.class);
	}

	public Greeting greeting(){
		if(this.async){
			WorkerThread worker = new WorkerThread(this);
			worker.setDaemon(daemon);
			return (Greeting) worker.sendAndWait();
		}
		return doGreet();
	}
	
	public Greeting doGreet(){
		String url = "http://localhost:8090/provider/greeting" + "?lock=" + this.lock;
		Greeting greeting = restTemplate.getForObject(url, Greeting.class);
		return greeting;
	}


	public boolean isDaemon() {
		return daemon;
	}


	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}


	public boolean isAsync() {
		return async;
	}


	public void setAsync(boolean async) {
		this.async = async;
	}


	public String getLock() {
		return lock;
	}


	public void setLock(String lock) {
		this.lock = lock;
	}
	
	
}
