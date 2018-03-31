package hello.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import hello.MyException;
import hello.support.RestServiceConfig;
import hello.support.WorkerThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hello.domain.Greeting;

@Component
public class RestServiceInvoker {

	@Autowired
	private RestTemplate restTemplate;

	public Greeting randomUriPath(int index) {
		if(index == 0){
			Random r = new Random();
			index = r.nextInt(10) + 1;
		}
		String url = "http://localhost:8090/provider/random/{index}";
		Greeting greeting = restTemplate.getForObject(url, Greeting.class, index);
		return greeting;
	}
	

	
	public Object exception() {
		String url = "http://localhost:8090/provider/exception";
		return restTemplate.getForObject(url, Object.class);
	}

	public Greeting greeting(RestServiceConfig restServiceConfig){
		if(restServiceConfig.isAsync()){
			return doInAsync(restServiceConfig);
		}else{
			if(restServiceConfig.isLock()){
				doInLock();
			}else {
				return doGreeting();
			}
		}
        return doGreeting();
	}

	public Greeting doInAsync(RestServiceConfig restServiceConfig){
		WorkerThread worker = new WorkerThread(this, restServiceConfig.isDaemon());;
		return (Greeting) worker.sendAndWait();
	}

	public Greeting doInLock(){
		ReentrantLock lock = new ReentrantLock();
		boolean locked = false;
		try {
			locked = lock.tryLock(2, TimeUnit.SECONDS);
			if(locked){
				Thread.sleep(1000);
				return doGreeting();
			}
		} catch (InterruptedException e) {
			throw new MyException(e);
		}finally{
			if(locked){
				lock.unlock();
			}
		}
		throw new MyException("tryLock timeout");

	}

	public Greeting doGreeting() {
		String url = "http://localhost:8090/provider/greeting";
		Greeting greeting = restTemplate.getForObject(url, Greeting.class);
		return greeting;
	}
}
