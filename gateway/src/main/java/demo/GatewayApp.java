package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/3/9 10:06
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApp {

    public static void main(final String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
}