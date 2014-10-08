package gov.pa.talisman.util;

import org.springframework.stereotype.Component;

import gov.pa.talisman.domain.User;
import gov.pa.talisman.dto.UserDto;

/**
 * Factory methods for creating DTOs.
 * 
 */
@Component
public class DtoFactory {

	/**
	 * Converts a domain entity to a dto.
	 * @param domain
	 * @return
	 */
	public UserDto createUser(User domain) {
		// TODO convert to dozer
		UserDto dto = new UserDto();
		dto.setId(domain.getId());
		dto.setFullname(domain.getFirstName() + " " + domain.getLastName());
		return dto;
	}

}
