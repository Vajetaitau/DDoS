package ufc.utils;

import ufc.dto.user.PrivateUserDetails;
import ufc.dto.user.PublicUserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsUtils {

    public PublicUserDetails getNewPublicUserDetails(PrivateUserDetails privateUserDetails) {
        String fullName = new StringBuilder(privateUserDetails.getUsername()).append(privateUserDetails.getLastName())
                .toString();//TODO: username - firstName
//        UserRole userRole = privateUserDetails.getAuthorities();
        return new PublicUserDetails(privateUserDetails.getId(), fullName);
    }

}
