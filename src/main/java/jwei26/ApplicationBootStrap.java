package jwei26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"jwei26"})
public class ApplicationBootStrap
{
    public static void main( String[] args )
    {
        SpringApplication.run(ApplicationBootStrap.class, args);
    }
}
