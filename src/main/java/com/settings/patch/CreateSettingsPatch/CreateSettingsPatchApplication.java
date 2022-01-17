package com.settings.patch.CreateSettingsPatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class CreateSettingsPatchApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(CreateSettingsPatchApplication.class, args);
	}

}
