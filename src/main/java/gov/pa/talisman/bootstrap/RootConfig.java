package gov.pa.talisman.bootstrap;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Bootstrap for service layer.
 * 
 */
@Configuration
@Import(DatabaseConfig.class)
@ComponentScan(basePackages = { "gov.pa.talisman.service", "gov.pa.talisman.dao", "gov.pa.talisman.util" })
public class RootConfig {

}
