package producer_customer_program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaDemoApplication implements ApplicationRunner {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String msg) {
		kafkaTemplate.send("demo", msg);
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoApplication.class, args);
	}

	@KafkaListener(topics = "demo", id = "group-id")
	public void listen(String message) {
		System.out.println("Received Messasge in group - group-id: " + message);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (int i = 0; i < 100; i++) {
			sendMessage(String.valueOf(i));
		}
	}
}
