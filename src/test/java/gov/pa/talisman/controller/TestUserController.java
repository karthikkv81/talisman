package gov.pa.talisman.controller;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import gov.pa.talisman.fixture.TestUserObject;
import gov.pa.talisman.domain.User;
import gov.pa.talisman.dto.save.SaveUserRequest;
import gov.pa.talisman.exception.UserNotFoundException;
import gov.pa.talisman.service.UserService;
import gov.pa.talisman.springconfig.ControllerTestConfig;
import gov.pa.talisman.util.DtoFactory;

/**
 * Unit tests the controller, including JSON serialization.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ControllerTestConfig.class })
public class TestUserController {

	@Autowired private UserService mockUserService;
	@Autowired private DtoFactory dtoFactory;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new TestUserController(mockUserService, dtoFactory)).build();
	}

	@Test
	public void test_getUserById() throws Exception {
		TestUserObject f = new TestUserObject();
		User user = f.createKarthik();
		when(mockUserService.getUserById(anyInt())).thenReturn(user);

		mockMvc.perform(get("/user/{id}", 1)
				.accept(TestUtil.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.fullname", is(user.getFirstName() + " " + user.getLastName())))
				.andReturn();
	}

	@Test
	public void test_getUserById_NotFound() throws Exception {
		final String errorMessage = "Mocking 404 message";
		when(mockUserService.getUserById(anyInt())).thenAnswer(new Answer<User>() {
			public User answer(InvocationOnMock invocation) throws Throwable {
				throw new UserNotFoundException(errorMessage);
			}
		});

		mockMvc.perform(get("/user/{id}", -1)
				.accept(TestUtil.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().isNotFound())
				.andExpect(content().string(errorMessage))
				.andReturn();
	}

	@Test
	public void test_getUserByIdFromParam() throws Exception {
		TestUserObject f = new TestUserObject();
		User user = f.createKarthik();
		when(mockUserService.getUserById(anyInt())).thenReturn(user);

		mockMvc.perform(get("/user?id={id}", 1)
				.accept(TestUtil.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.fullname", is(user.getFirstName() + " " + user.getLastName())))
				.andReturn();
	}

	@Test
	public void test_saveUser() throws Exception {
		TestUserObject f = new TestUserObject();
		User user = f.createKarthik();
		final Integer newId = user.getId();
		user.setId(null);
		
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				User p = (User) args[0];
				p.setId(newId); // emulate the successful save populating the id
	            return "called with arguments: " + args;
			}
		}).when(mockUserService).saveUser((User) anyObject());

		SaveUserRequest spr = new SaveUserRequest();
		spr.setUserName(user.getUserName());
		spr.setFirstName(user.getFirstName());
		spr.setLastName(user.getLastName());
		
		mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(spr))
				.accept(TestUtil.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().isOk())
				.andExpect(content().string(newId.toString()))
				.andReturn();
	}
	
}

