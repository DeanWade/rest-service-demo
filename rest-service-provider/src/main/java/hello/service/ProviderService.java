package hello.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import hello.MyException;
import hello.domain.Greeting;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong(1000);
    private final Lock lock = new ReentrantLock();
    
    public Greeting greeting(String name, String lock) {
    	if(lock.equals("lock")){
    		return doGreetingWithLock(name);
    	}
    	if(lock.equals("sync")){
    		return doGreetingWithSync(name);
    	}
    	return doGreeting(name);
    }
    

	public Greeting randomUriPath(int index) {
    	try {
    		if(index > 0 && index % 4 == 0){
    			Thread.sleep(1000 * index);
    		}else{
    			Thread.sleep(1000);
    		}
		} catch (InterruptedException e) {
			//do nothing
		}
    	return doGreeting("RandomUriPath");
	}
    
    private synchronized Greeting doGreetingWithSync(String name){
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			//do nothing
		}
    	return doGreeting(name);
    }
    
	private Greeting doGreetingWithLock(String name){
//		lock.lock();
		boolean locked = false;
    	try {
    		locked = lock.tryLock(2, TimeUnit.SECONDS);
    		if(locked){
    			Thread.sleep(1000);
    			return doGreeting(name);
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
	
	public void exception() {
		throw new MyException("just throw MyException");
	}
    
    private Greeting doGreeting(String name){
    	return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }


}
