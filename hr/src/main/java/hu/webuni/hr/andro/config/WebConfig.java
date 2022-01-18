package hu.webuni.hr.andro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hu.webuni.hr.andro.converter.StringToPositionDtoConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	StringToPositionDtoConverter stringToPositionDtoConverter;
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(stringToPositionDtoConverter);
	}
	
}
