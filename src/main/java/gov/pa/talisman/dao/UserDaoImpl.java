package gov.pa.talisman.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.WebApplicationInitializer;

import gov.pa.talisman.domain.User;
import gov.pa.talisman.exception.UserNotFoundException;

@Repository
public class UserDaoImpl implements UserDao {
	
	private static final Logger logger = LoggerFactory.getLogger(WebApplicationInitializer.class);

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public UserDaoImpl(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public User findById(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		List<User> list = jdbcTemplate.query("select * from user where id = :id", params, new UserRowMapper());
		if (list.isEmpty()) {
			throw new UserNotFoundException("No user found for id: " + id);
		} else {
			return list.get(0);
		}
	}

	public void insert(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		logger.debug("inserting user into database");
		jdbcTemplate.update(
				"insert into user (user_name, first_name, last_name) values (:userName, :firstName, :lastName)",
				new BeanPropertySqlParameterSource(user), keyHolder);

		Integer newId = keyHolder.getKey().intValue();

		// populate the id
		user.setId(newId);
	}

	public void update(User user) {
		int numRowsAffected = jdbcTemplate.update(
				"update user set user_name = :userName, first_name = :firstName, last_name = :lastName where id = :id",
				new BeanPropertySqlParameterSource(user));
		
		if (numRowsAffected == 0) {
			throw new UserNotFoundException("No user found for id: " + user.getId());
		}
	}

	private static class UserRowMapper implements RowMapper<User> {
		public User mapRow(ResultSet res, int rowNum) throws SQLException {
			User p = new User();
			p.setId(res.getInt("id"));
			p.setUserName(res.getString("user_name"));
			p.setFirstName(res.getString("first_name"));
			p.setLastName(res.getString("last_name"));
			return p;
		}
	}

}
