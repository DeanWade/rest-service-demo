package hello.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="/external")
public class ExternalServiceController {

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/{path}")
	protected String ip(@PathVariable String path,
						@RequestParam(value = "wait", defaultValue = "100") int waitTime) throws IOException {
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
		}
		return restTemplate.getForObject("http://httpbin.org/" + path, String.class);
		
	}
}
