package dev.fringe;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.script.ScriptTemplateConfigurer;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import reactor.netty.http.server.HttpServer;

@ComponentScan(basePackages = { "dev.fringe.controller", "com.mkyong.reactive" })
@Configuration
@EnableWebFlux
@Log4j2
public class App implements WebFluxConfigurer, InitializingBean {

	@Value("${server.port:8082}")
	private int port = 8082;

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class)) {
			context.getBean(HttpServer.class).bindNow().onDispose().block();
		}
	}

	@Bean
	public HttpServer nettyHttpServer(ApplicationContext context) {
		HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();
		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
		HttpServer httpServer = HttpServer.create().host("localhost").port(this.port);
		return httpServer.handle(adapter);
	}

	@SneakyThrows
	public void afterPropertiesSet() {
		log.info("server started");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.scriptTemplate().suffix(".html");
	}

	@Bean
	public ScriptTemplateConfigurer scrptTemplateConfigurer() {
		ScriptTemplateConfigurer configurer = new ScriptTemplateConfigurer();
		configurer.setEngineName("nashorn");
		configurer.setScripts("mustache.js");
		configurer.setRenderObject("Mustache");
		configurer.setResourceLoaderPath("classpath:/public/");
		configurer.setRenderFunction("render");
		return configurer;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/public/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/public/js/");
	}
}
