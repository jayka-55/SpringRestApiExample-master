package com.gerardo.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={"com.gerardo.test"})
public class RestApiApp 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(RestApiApp.class, args);
    }
}
