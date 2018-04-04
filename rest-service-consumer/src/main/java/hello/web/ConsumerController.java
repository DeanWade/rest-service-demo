package hello.web;

import hello.support.RestServiceProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.domain.Greeting;
import hello.service.RestServiceInvoker;

@RestController
@RequestMapping(value="/consumer")
public class ConsumerController {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);
	
	@Autowired(required=false)
	private RestServiceInvoker invoker;
	
	@GetMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String name,
			@RequestParam(value = "async", defaultValue = "false") boolean async,
			@RequestParam(value = "daemon", defaultValue = "false") boolean daemon){
		RestServiceProps restServiceProps = new RestServiceProps();
		restServiceProps.setAsync(async);
		restServiceProps.setDaemon(daemon);
		return invoker.greeting(restServiceProps);
	}
	
	@GetMapping("/greeting2")
	public Greeting greeting2(
    		@RequestParam(value="name", defaultValue="World") String name,
    		@RequestParam(value="lock", defaultValue="lock") boolean lock) {
        RestServiceProps restServiceProps = new RestServiceProps();
        restServiceProps.setLock(lock);
        return invoker.greeting(restServiceProps);
	}
	
    @GetMapping("/exception")
    public Object exception() {
    	return invoker.exception();
    }
	
	@GetMapping("/random")
	public Greeting randomUriPath() {
		return this.randomUriPath2(0);
	}
	
	@GetMapping("/random/{index}")
	public Greeting randomUriPath2(@PathVariable int index) {
		return invoker.randomUriPath(index);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}
	
	@ExceptionHandler(Exception.class)
	public Greeting onException(Exception e){
		logger.error("onException", e);
		Greeting greeting = new Greeting(Long.MAX_VALUE, e.getMessage());
		return greeting;
	}
}
