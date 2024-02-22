package jwei26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173")
@SpringBootApplication(scanBasePackages = {"jwei26"})
@ServletComponentScan(basePackages = "jwei26.filter")
public class ApplicationBootStrap
{
    public static void main( String[] args )
    {
        SpringApplication.run(ApplicationBootStrap.class, args);
    }
}
