package gov.pa.talisman.springconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import gov.pa.talisman.bootstrap.DatabaseConfig;

/**
 * Bootstrap for service layer.
 * 
 */
@Configuration
@Import(DatabaseConfig.class)
@ComponentScan(basePackages = { "gov.pa.talisman.dao" })
public class DaoTestConfig {

}
