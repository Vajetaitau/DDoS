package ufc.core.service.secondLayer;

import ufc.dto.user.PrivateUserDetails;


public interface UserServiceL2 {

    boolean exists(Integer id);

    boolean isValid(PrivateUserDetails privateUserDetails);

    PrivateUserDetails create(String username, String password, String... authorities);

    PrivateUserDetails save(PrivateUserDetails privateUserDetails);

    PrivateUserDetails getUser(String username);

    PrivateUserDetails getUser(int id);

    PrivateUserDetails getCurrentUser();

}
