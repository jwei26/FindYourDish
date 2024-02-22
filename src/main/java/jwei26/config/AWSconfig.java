package jwei26.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
public class AWSconfig {
    private Regions defaultRegion = Regions.US_EAST_1;
    @Bean
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder.standard().withRegion(defaultRegion).build();
    }
}
