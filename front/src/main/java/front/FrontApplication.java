package front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class FrontApplication {

        protected static ApplicationContext applicationContext;
        public static void main(String[] args) {
            applicationContext = SpringApplication.run(FrontApplication.class, args);
        }

}
