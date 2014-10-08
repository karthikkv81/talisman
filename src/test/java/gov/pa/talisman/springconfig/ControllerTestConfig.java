package gov.pa.talisman.springconfig;

import org.mockito.Mockito;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import gov.pa.talisman.service.UserService;

@Configuration
@ComponentScan(basePackages = { "gov.pa.talisman.util" })
public class ControllerTestConfig {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public UserService mockUserService() {
		return Mockito.mock(UserService.class);
	}
	
}
