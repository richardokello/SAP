package co.ke.spsat.bowip;

import co.ke.spsat.bowip.payment.mpesa.MpesaIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public MpesaIntegration mpesaIntegration() {
		return new MpesaIntegration("yourAppKey", "yourAppSecret");
	}

}
