package ufc.dto.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PrivateUserDetails extends org.springframework.security.core.userdetails.User {

    private Integer id;
    private String lastName;
    private Integer x;
    private Integer y;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PrivateUserDetails(Integer id, Integer x, Integer y) {
        this(id, null, null, true, true, true, true, null, null, x, y);
    }

    public PrivateUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String lastName) {
        this(null, username, password, true, true, true, true, authorities, lastName, null, null);
    }

    public PrivateUserDetails(Integer id, String username, String password, boolean enabled, boolean accountNonExpired,
                              boolean credentialsNonExpired, boolean accountNonLocked,
                              Collection<? extends GrantedAuthority> authorities, String lastName,
                              Integer x, Integer y) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.lastName = lastName;
        this.x = x;
        this.y = y;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
