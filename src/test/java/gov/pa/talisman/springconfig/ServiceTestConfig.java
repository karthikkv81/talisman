package gov.pa.talisman.springconfig;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import gov.pa.talisman.dao.UserDao;

/**
 * Bootstrap for service layer.
 * 
 */
@Configuration
@ComponentScan(basePackages = { "gov.pa.talisman.service" })
public class ServiceTestConfig {

	@Bean
	public UserDao mockUserDao() {
		return Mockito.mock(UserDao.class);
	}
	
}
