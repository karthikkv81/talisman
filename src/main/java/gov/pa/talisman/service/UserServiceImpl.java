package gov.pa.talisman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.pa.talisman.dao.UserDao;
import gov.pa.talisman.domain.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	public User getUserById(Integer id) {
		return userDao.findById(id);
	}

	@Transactional
	public void saveUser(User user) {
		userDao.insert(user);
	}


}
