package com.greenmile.info.provider.configuration;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author caioalbmelo
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
         .antMatchers("/RoutesInfo/**");
  }

}
