package jwei26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"jwei26"})
@ServletComponentScan(basePackages = "jwei26.filter")
public class ApplicationBootStrap
{
    public static void main( String[] args )
    {
        SpringApplication.run(ApplicationBootStrap.class, args);
    }
}
