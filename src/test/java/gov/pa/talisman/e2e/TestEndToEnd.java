package gov.pa.talisman.e2e;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import gov.pa.talisman.bootstrap.RootConfig;
import gov.pa.talisman.controller.TestUserController;
import gov.pa.talisman.controller.TestUtil;
import gov.pa.talisman.fixture.TestUserObject;
import gov.pa.talisman.domain.User;
import gov.pa.talisman.dto.save.SaveUserRequest;
import gov.pa.talisman.service.UserService;
import gov.pa.talisman.util.DtoFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
public class TestEndToEnd {

	@Autowired private UserService userService;
	@Autowired private DtoFactory dtoFactory;
	
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new TestUserController(userService, dtoFactory)).build();
	}

	@Test
	public void test_getUserById() throws Exception {
		TestUserObject f = new TestUserObject();
		User user = f.createKarthik();

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
		Integer badId = -1;
		mockMvc.perform(get("/user/{id}", badId)
				.accept(TestUtil.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().isNotFound())
				.andExpect(content().string("No user found for id: " + badId))
				.andReturn();
	}

	@Test
	public void test_getUserByIdFromParam() throws Exception {
		TestUserObject f = new TestUserObject();
		User user = f.createKarthik();

		mockMvc.perform(get("/user?id={id}", 1)
				.accept(TestUtil.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.fullname", is(user.getFirstName() + " " + user.getLastName())))
				.andReturn();
	}

	// complications with generated keys
	@Test
	public void test_saveUser() throws Exception {
		TestUserObject f = new TestUserObject();
		User user = f.createKarthik();
		user.setId(null);

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
				.andExpect(content().string("4")) // init script creates 3 records
				.andReturn();
	}

}
