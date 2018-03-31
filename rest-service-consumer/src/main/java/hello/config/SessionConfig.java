package hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@EnableRedisHttpSession 
public class SessionConfig {

//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//            return new LettuceConnectionFactory(); 
//    }
	
    @Bean
    public JedisConnectionFactory connectionFactory() {
            return new JedisConnectionFactory(); 
    }
    
    public class SessionInitializer extends AbstractHttpSessionApplicationInitializer {

    	public SessionInitializer() {
    		super(SessionConfig.class);
    	}
    }
}
