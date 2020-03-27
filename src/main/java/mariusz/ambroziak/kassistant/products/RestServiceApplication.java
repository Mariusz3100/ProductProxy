package mariusz.ambroziak.kassistant.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import org.hibernate.SessionFactory;

@SpringBootApplication
public class RestServiceApplication {


	
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RestServiceApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8085"));
        app.run(args);
    }
}