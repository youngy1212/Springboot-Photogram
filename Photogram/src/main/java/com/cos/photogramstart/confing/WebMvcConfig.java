package com.cos.photogramstart.confing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{ //web 설정파일
	
	@Value("${file.path}")
	private String uploadFolder;
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		//file:///C:/~~~~/~~~/upload/ 로 변경해주는 것.
		registry
			.addResourceHandler("/upload/**") //jsp 페이지에서 /upload/** 이런 주소패턴이 나오면 발동
			.addResourceLocations("file:///"+uploadFolder)
			.setCachePeriod(60*10*6) //1시간 동안 이미지를 캐싱
			.resourceChain(true)
			.addResolver(new PathResourceResolver());
	}
	
}
