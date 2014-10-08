package gov.pa.talisman.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.pa.talisman.dao.UserDao;
import gov.pa.talisman.domain.User;
import gov.pa.talisman.fixture.TestUserObject;
import gov.pa.talisman.springconfig.ServiceTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceTestConfig.class })
public class TestUserServiceImpl {

	@Autowired private UserService userService;
	@Autowired private UserDao mockUserDao;

	@Test
	public void test_getUserById() {
		TestUserObject f = new TestUserObject();
		User userExists = f.createKarthik();

		when(mockUserDao.findById(anyInt())).thenReturn(userExists);

		User userFound = userService.getUserById(userExists.getId());

		assertEquals(userExists.getId(), userFound.getId());
		assertEquals(userExists, userFound);
	}

	@Test
	public void test_saveUser() {
		TestUserObject f = new TestUserObject();
		User userExists = f.createKarthik();
		userExists.setId(null);

		final Holder invokeCounter = new Holder(); // since we can't use verify() on void method
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				invokeCounter.value = invokeCounter.value + 1;
				return null;
			}
		}).when(mockUserDao).insert((User) anyObject());

		userService.saveUser(userExists);

		assertEquals(1, invokeCounter.value);
	}

	private class Holder {
		public int value = 0;
	}

}
