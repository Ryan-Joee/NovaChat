package com.ryan.novachat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NovachatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovachatServerApplication.class, args);

        StringBuilder sb = new StringBuilder();
        sb.append("=======================================================================");
        sb.append("\n");
        sb.append("========================== SERVER START SUCCESS！！ ===================");
        sb.append("\n");
        sb.append("=======================================================================");
        System.out.println(sb);
    }

}
