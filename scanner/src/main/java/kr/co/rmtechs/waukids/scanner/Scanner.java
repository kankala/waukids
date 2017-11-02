package kr.co.rmtechs.waukids.scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("file:./config/scanner.properties")
@EnableScheduling
@ImportResource("file:./config/schedule.xml")
public class Scanner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(Scanner.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        logger.info("Scanner started...");
    }
}
