package br.com.github.repo.scraping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.Contact;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("br.com.github.repo.scraping.controller"))
					.paths(PathSelectors.any())
					.build()
				.apiInfo(apiInfo())
				.pathMapping("/");
	}
	
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title("Challenge Github repository Trustly")
				.description ("Develop an API that returns the total number of lines and the total number of bytes of all the files of a given public Github repository, grouped by file extension.")
				.contact(new Contact("Lucas Davi Matias Siqueira", "linkedin:https://www.linkedin.com/in/lucas-davi/", "eu@lucasdavi.com"))
				.version("1.0")
		        .build();
		return apiInfo;
	}
}
