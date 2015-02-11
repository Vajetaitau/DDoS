package ufc.utils.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ufc.core.service.secondLayer.UserServiceL2;

@Component("userUtils")
public class UserUtils {

    private UserServiceL2 userServiceL2;

    @Autowired
    public UserUtils(UserServiceL2 userServiceL2) {
        this.userServiceL2 = userServiceL2;
    }

}
