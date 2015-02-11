package ufc.core.service.secondLayer;

import ufc.persistence.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


public interface AuthorityServiceL2 {

    List<GrantedAuthority> getAuthorities(String username);

    void save(User user, String... authorities);

}
