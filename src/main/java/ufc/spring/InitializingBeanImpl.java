package ufc.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ufc.core.exceptions.GeneralException;
import ufc.core.service.secondLayer.UserServiceL2;
import ufc.dto.user.PrivateUserDetails;
import ufc.utils.entity.UserUtils;

@Component
public class InitializingBeanImpl implements InitializingBean {

    @Autowired
    UserServiceL2 userServiceL2;

    @Autowired
    UserUtils userUtils;

    @Override
    @Transactional(readOnly = false)
    public void afterPropertiesSet() throws GeneralException {
//        PrivateUserDetails admin = userServiceL2.create("admin", "admin", "ADMIN", "USER");
    }

}


