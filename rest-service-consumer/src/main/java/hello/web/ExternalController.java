package hello.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping(value = "/consumer")
public class ExternalController {

    @Autowired(required=false)
    private RestTemplate restTemplate;

    @GetMapping("/external/{path}")
    protected String ip(@PathVariable(required=false) String path,
                        @RequestParam(value = "wait", defaultValue = "100") int waitTime) throws IOException {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
        }
        return restTemplate.getForObject("http://httpbin.org/" + path, String.class);

    }
}
