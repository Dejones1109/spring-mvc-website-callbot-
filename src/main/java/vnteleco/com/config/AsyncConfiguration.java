package vnteleco.com.config;


import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@PropertySource("classpath:config.properties")
@Configuration
@EnableAsync
public class AsyncConfiguration {
	
	@Value("${core_pool_size}")
    private int corePoolSize;
	
	@Value("${max_pool_size}")
    private int maxPoolSize;
	
	@Value("${queue_capacity}")
    private int queueCapacity;

	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		System.out.println("Creating Async Task Executor");
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("Thread-");
		executor.initialize();
		return executor;
	}
}
