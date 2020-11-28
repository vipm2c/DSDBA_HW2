package dsdbahw2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import dsdbahw2.ignite.IgniteLoader;

@SpringBootApplication
@ComponentScan
public class ApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
        IgniteLoader.startIgnite(args);
    }
}