package dsdbahw2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import dsdbahw2.ignite.IgniteLoader;
/**
 * Main class
 * Start Spring application and
 * Load data from Cassandra to Ignite
 */

@SpringBootApplication
@ComponentScan
public class ApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
        IgniteLoader.startIgnite(args);
    }
}