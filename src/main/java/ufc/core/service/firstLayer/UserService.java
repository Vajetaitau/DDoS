package ufc.core.service.firstLayer;

import ufc.core.exceptions.GeneralException;
import ufc.dto.user.PrivateUserDetails;
import ufc.dto.user.PublicUserDetails;

public interface UserService {

    public PrivateUserDetails create(PrivateUserDetails privateUserDetails) throws GeneralException;
    public PublicUserDetails getUser(String username);
    public PublicUserDetails getCurrentUser();

}
