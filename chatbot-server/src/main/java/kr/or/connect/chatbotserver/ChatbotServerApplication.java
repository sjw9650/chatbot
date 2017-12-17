package kr.or.connect.chatbotserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ChatbotServerApplication {
	ApplicationContext applicationContext;
	public synchronized ApplicationContext getApplicationContext(){
		if(applicationContext==null){
			applicationContext = SpringApplication.run(ChatbotServerApplication.class);
		}
		return applicationContext;
	}
	public static void main(String[] args) {
		SpringApplication.run(ChatbotServerApplication.class, args);
	}
}
