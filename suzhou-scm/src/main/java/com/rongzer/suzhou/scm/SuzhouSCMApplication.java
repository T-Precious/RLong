package com.rongzer.suzhou.scm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.rongzer.suzhou.scm.dao")
@SpringBootApplication
@ServletComponentScan
public class SuzhouSCMApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SuzhouSCMApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SuzhouSCMApplication.class);
	}

}
