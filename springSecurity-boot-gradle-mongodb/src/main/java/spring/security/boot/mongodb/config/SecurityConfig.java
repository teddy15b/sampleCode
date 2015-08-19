/*
 * *
 * * Copyright (c) 2015 Ivan Hristov * <p/> * Licensed under the Apache License, Version 2.0 (the
 * "License"); * you may not use this file except in compliance with the License. * You may obtain a
 * copy of the License at * <p/> * http://www.apache.org/licenses/LICENSE-2.0 * <p/> * Unless
 * required by applicable law or agreed to in writing, software * distributed under the License is
 * distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. * See the License for the specific language governing permissions and * limitations
 * under the License.
 */

package spring.security.boot.mongodb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import spring.security.boot.mongodb.security.MongoDBUserDetailsService;


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
  }
}
