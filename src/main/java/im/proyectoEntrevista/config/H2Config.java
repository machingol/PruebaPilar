package im.proyectoEntrevista.config;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;


@Configuration
public class H2Config {
	
	@Async("asyncExecutor")
	public static void scheduleDelayTask1(boolean parar)
	{
		
		    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		    Runnable task1 = () -> System.out.println("Test job executed at " + new Date());   
		    ScheduledFuture<?> controlar = service.scheduleAtFixedRate(task1, 5, 1, TimeUnit.SECONDS);
		    if(parar) {
		    	controlar.cancel(false);
		    	service.shutdown();
		    }
	
	}
}
