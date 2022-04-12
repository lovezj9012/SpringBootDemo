package com.zj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 
 * SpringBoot启动
 *
 */
@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class SpringBootAppServer {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootAppServer.class, args);
	}
}
