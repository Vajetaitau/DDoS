package ufc.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import ufc.constants.StringLength;

/**
 *
 * @author K
 */
@Entity
@Table(name = "authorities", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username", "authority"})
})
public class Authority extends Item {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false, referencedColumnName = "username")
    private User user;

    @Column(nullable = false, length = StringLength.SHORT)
    private String authority;

    private Authority() {
    }

    public Authority(User user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
