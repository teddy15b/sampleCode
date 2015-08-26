package spring.security.boot.mongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter{
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    // registry.addViewController("/login").setViewName("login");
    registry.addRedirectViewController("/", "/resource");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/auth/**").addResourceLocations(
        "classpath:/auth/");
  }
}
