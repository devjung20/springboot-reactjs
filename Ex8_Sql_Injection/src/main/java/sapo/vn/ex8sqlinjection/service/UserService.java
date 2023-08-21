package sapo.vn.ex8sqlinjection.service;

import sapo.vn.ex8sqlinjection.entity.User;
import sapo.vn.ex8sqlinjection.response.UserResponse;

public interface UserService {
    UserResponse loginAttacks(User user);

    UserResponse loginAgainstAttacks(User user);
}
