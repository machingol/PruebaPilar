package im.proyectoEntrevista;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.tomcat.util.threads.TaskThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import im.proyectoEntrevista.config.H2Config;



@SpringBootApplication
@EnableAsync
public class EntrevistaOdiloApplication implements CommandLineRunner{

	public static void main(String[] args) {

		SpringApplication.run(EntrevistaOdiloApplication.class, args);

	}
	
	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
			executor.setCorePoolSize(2);
			executor.setMaxPoolSize(2);
			executor.setThreadNamePrefix("Hilo de Jorge creado-");
			executor.initialize();
			
			return executor;
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
			H2Config.scheduleDelayTask1(false);
	}



	
	
	

}
