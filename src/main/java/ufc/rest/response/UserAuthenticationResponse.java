package ufc.rest.response;

public class UserAuthenticationResponse extends ResponseMessage {

    private String token;

    protected UserAuthenticationResponse() {
    }

    public UserAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
