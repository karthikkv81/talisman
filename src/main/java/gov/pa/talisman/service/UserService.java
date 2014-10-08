package gov.pa.talisman.service;

import gov.pa.talisman.domain.User;

/**
 * Service for managing user.
 * 
 */
public interface UserService {

	/**
	 * @param id
	 * @return Returns the user with the given id.
	 */
	public User getUserById(Integer id);

	/**
	 * Creates a new User and populates the <code>id</code> property with the generated id.
	 * @param user All non-id properties are used to create the new user.
	 */
	public void saveUser(User user);
	

}
