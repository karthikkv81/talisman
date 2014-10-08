package gov.pa.talisman.bootstrap;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Bootstrap for the REST layer. 
 * 
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "gov.pa.talisman.controller")
public class MvcConfig extends WebMvcConfigurerAdapter {

}
