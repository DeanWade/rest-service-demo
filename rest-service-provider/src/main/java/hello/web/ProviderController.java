package hello.web;

import hello.domain.Greeting;
import hello.service.ProviderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

	private final ProviderService providerService;
	
	public ProviderController(ProviderService providerService){
		this.providerService = providerService;
	}
	
	@GetMapping("/provider/greeting")
    public Greeting greeting(
    		@RequestParam(value="name", defaultValue="provider") String name,
    		@RequestParam(value="lock", defaultValue="none") String lock) {
    	return providerService.greeting(name, lock);
    }
    
    @GetMapping("/provider/exception")
    public void exception() {
    	providerService.exception();
    }
    
	@GetMapping("/provider/random/{index}")
	public Greeting randomUriPath(@PathVariable int index) {
		return providerService.randomUriPath(index);
	}
}
