package hello.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
	
	@Value("${rest.connection.timeout}")
	private int connectTimeout;
	
	@Value("${rest.read.timeout}")
	private int readTimeout;
	
	private int connectionRequestTimeout = 1000;

	@Bean
	public RestTemplate restTemplate() {
        //SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
	}
}
