package hello.config;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Profile("scheduling")
@EnableScheduling
public class SchedulingConfig {
}
