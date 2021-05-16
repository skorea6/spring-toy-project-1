package shop.mshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 생성시, 업데이트시 시간에 대한 것
@SpringBootApplication
public class MshopApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MshopApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MshopApplication.class, args);
	}


	@Bean
	public AuditorAware<String> auditorProvider() {
		return new Auditor();
	}

}
