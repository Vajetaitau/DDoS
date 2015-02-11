package ufc.core.service.firstLayer.impl;

import ufc.core.exceptions.GeneralException;
import ufc.core.service.firstLayer.UserService;
import ufc.core.service.secondLayer.UserServiceL2;
import ufc.dto.user.PrivateUserDetails;
import ufc.dto.user.PublicUserDetails;
import ufc.utils.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional()
public class UserServiceImpl implements UserService {

    private final UserServiceL2 userServiceL2;
    private final UserDetailsUtils userDetailsUtils;

    @Autowired
    public UserServiceImpl(UserServiceL2 userServiceL2, UserDetailsUtils userDetailsUtils) {
        this.userServiceL2 = userServiceL2;
        this.userDetailsUtils = userDetailsUtils;
    }

    @Override
    public PrivateUserDetails create(PrivateUserDetails privateUserDetails) throws GeneralException {
        if (!userServiceL2.isValid(privateUserDetails)) {
            throw new GeneralException("User is not valid!");
        }
        if (userServiceL2.exists(privateUserDetails.getId())) {
            throw new GeneralException("User with id = " + privateUserDetails.getId() + " already exists!");
        }
        return userServiceL2.save(privateUserDetails);
    }

    @Override
    public PublicUserDetails getUser(String username) {
        return userDetailsUtils.getNewPublicUserDetails(userServiceL2.getUser(username));
    }

    @Override
    public PublicUserDetails getCurrentUser() {
        return userDetailsUtils.getNewPublicUserDetails(userServiceL2.getCurrentUser());
    }

}
