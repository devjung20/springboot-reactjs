package sapo.vn.ex8sqlinjection.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sapo.vn.ex8sqlinjection.entity.User;
import sapo.vn.ex8sqlinjection.response.UserResponse;
import sapo.vn.ex8sqlinjection.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final JdbcTemplate jdbcTemplate;

    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserResponse loginAttacks(User user) {
        String usernameInput = user.getUsername();
        String passwordInput = user.getPassword();
        String sql = "SELECT * FROM users WHERE username = '" + usernameInput + "' AND password = '" + passwordInput + "'";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        UserResponse userResponse = new UserResponse();

        try {
            User resultUser = jdbcTemplate.queryForObject(sql, rowMapper);
            if (resultUser != null) {
                userResponse.setCode(String.valueOf(HttpStatus.OK));
                userResponse.setMessage("Login Successfully!");
            }
        } catch (EmptyResultDataAccessException e) {
            userResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            userResponse.setMessage("Invalid username or password!");
        }
        return userResponse;
    }

    @Override
    public UserResponse loginAgainstAttacks(User user) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        UserResponse userResponse = new UserResponse();

        try {
            User resultUser = jdbcTemplate.queryForObject(sql, rowMapper, user.getUsername(), user.getPassword());
            if (resultUser != null) {
                userResponse.setCode(String.valueOf(HttpStatus.OK));
                userResponse.setMessage("Login Successfully!");
            }
        } catch (EmptyResultDataAccessException e) {
            userResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
            userResponse.setMessage("Invalid username or password!");
        }
        return userResponse;
    }
}
