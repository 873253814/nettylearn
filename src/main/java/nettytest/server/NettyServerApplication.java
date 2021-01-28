package nettytest.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
    }

}
