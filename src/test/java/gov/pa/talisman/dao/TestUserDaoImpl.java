package gov.pa.talisman.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.pa.talisman.fixture.TestUserObject;
import gov.pa.talisman.domain.User;
import gov.pa.talisman.exception.UserNotFoundException;
import gov.pa.talisman.springconfig.DaoTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DaoTestConfig.class })
public class TestUserDaoImpl {

	@Autowired private UserDaoImpl userDao;

	@Test
	public void test_findById() {
		Integer id = 1;
		User p1 = userDao.findById(id);
		assertEquals(p1.getId(), id);
	}

	@Test(expected = UserNotFoundException.class)
	public void test_findById_NotFound() {
		Integer id = -1;
		userDao.findById(id);
	}
	
	@Test
	public void test_insert() {
		TestUserObject f = new TestUserObject();
		User user = f.createKarthik();
		
		// function not supported!? the generated key stuff...
		userDao.insert(user);
	}
	
	@Test
	public void test_update() {
		TestUserObject f = new TestUserObject();
		User user = f.createKarthik();
		
		String newFirstName = "Fred";
		
		user.setFirstName(newFirstName);
		userDao.update(user);
		
		User updatedUser = userDao.findById(user.getId());
		assertEquals(newFirstName, updatedUser.getFirstName());
	}

}
