package ufc.rest.response;

import ufc.dto.user.PublicUserDetails;

public class CurrentUserResponse {

    private PublicUserDetails currentUser;

    public CurrentUserResponse() {
    }

    public CurrentUserResponse(PublicUserDetails currentUser) {
        this.currentUser = currentUser;
    }

    public PublicUserDetails getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(PublicUserDetails currentUser) {
        this.currentUser = currentUser;
    }
}
