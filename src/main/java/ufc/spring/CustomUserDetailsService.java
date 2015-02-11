package ufc.spring;

import ufc.dto.user.PrivateUserDetails;
import ufc.persistence.entity.User;
import ufc.persistence.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username/password!");
        }
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("ADMIN", "USER");
        return new PrivateUserDetails(user.getUsername(), user.getPassword(), grantedAuthorities, "Surname");
    }
}
