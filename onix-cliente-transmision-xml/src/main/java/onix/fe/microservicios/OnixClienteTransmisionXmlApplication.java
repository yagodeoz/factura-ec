package onix.fe.microservicios;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class OnixClienteTransmisionXmlApplication
{

	public static void main(String[] args) {
		SpringApplication.run(OnixClienteTransmisionXmlApplication.class, args);
	}
}
