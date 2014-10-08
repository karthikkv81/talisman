package gov.pa.talisman.fixture;

import gov.pa.talisman.domain.User;

/**
 * Test object for unit tests.
 * 
 */
public class TestUserObject {


	public User createKarthik() {
		User user = new User();
		user.setId(1);
		user.setUserName("karthikkv81");
		user.setFirstName("Karthik");
		user.setLastName("Venkatesan");
		return user;
	}

}
