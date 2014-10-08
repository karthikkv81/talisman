package cgov.pa.talisman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import gov.pa.talisman.domain.User;
import gov.pa.talisman.dto.UserDto;
import gov.pa.talisman.dto.save.SaveUserRequest;
import gov.pa.talisman.exception.UserNotFoundException;
import gov.pa.talisman.service.UserService;
import gov.pa.talisman.util.DtoFactory;

/**
 * REST layer for managing people.
 * 
 * 
 */
@Controller
public class UserController {

	private UserService userService;
	private DtoFactory userDtoFactory;

	@Autowired
	public UserController(UserService userService, DtoFactory userDtoFactory) {
		this.userService = userService;
		this.userDtoFactory = userDtoFactory;
	}

	/**
	 * @param id
	 * @return Returns the user with the given id.
	 */
	@RequestMapping("user/{id}")
	@ResponseBody
	public UserDto getUserById(@PathVariable Integer id) {
		return userDtoFactory.createUser(userService.getUserById(id));
	}

	/**
	 * Same as above method, just showing different URL mapping
	 * @param id
	 * @return Returns the user with the given id.
	 */
	@RequestMapping(value = "user", params = "id")
	@ResponseBody
	public UserDto getUserByIdFromParam(@RequestParam Integer id) {
		return userDtoFactory.createUser(userService.getUserById(id));
	}

	/**
	 * Creates a new user.
	 * @param request
	 * @return Returns the id for the new user.
	 */
	@RequestMapping(value = "user", method = RequestMethod.POST)
	@ResponseBody
	public Integer createUser(@RequestBody SaveUserRequest request) {
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUserName(request.getUserName());
		userService.saveUser(user);
		return user.getId();
	}
	
	// --- Error handlers
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public String handleUserNotFoundException(UserNotFoundException e) {
		return e.getMessage();
	}

}
