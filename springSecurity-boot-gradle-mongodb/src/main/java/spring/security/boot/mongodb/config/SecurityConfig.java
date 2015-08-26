package spring.security.boot.mongodb.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import spring.security.boot.mongodb.domain.Account;
import spring.security.boot.mongodb.security.MongoDBUserDetailsService;

/**
 * @author teddy
 *
 */

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
// @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private MongoDBUserDetailsService mongoDBUserDetailsService;

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/js/**", "/css/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.formLogin().defaultSuccessUrl("/resource").and().logout().and().authorizeRequests()
        .antMatchers("/index.html", "/home.html", "/login.html", "/", "/access", "/logout")
        .permitAll().anyRequest().authenticated().and().csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(mongoDBUserDetailsService);
    if (!mongoDBUserDetailsService.userExists("admin")) {
      List<String> roles = new ArrayList<String>();
      roles.add("ROLE_ADMIN");
      mongoDBUserDetailsService.createUser(new Account("admin","pass", roles));
    }      
  }
}
