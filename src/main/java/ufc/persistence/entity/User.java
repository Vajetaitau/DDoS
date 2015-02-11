package ufc.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Item implements Serializable {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable()
    List<Comment> comments;

    private Integer x;
    private Integer y;

    private User() {
        this(null, null, null, false, null, null);
    }

    public User(Integer id, String username, String password, boolean enabled, Integer x, Integer y) {
        super(id);
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.x = x;
        this.y = y;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }
}
