package ufc.core.service.secondLayer.impl;

import ufc.core.service.secondLayer.AuthorityServiceL2;
import ufc.core.service.secondLayer.UserServiceL2;
import ufc.dto.user.PrivateUserDetails;
import ufc.persistence.entity.User;
import ufc.persistence.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceL2")
public class UserServiceL2Impl implements UserServiceL2 {

    private final UserDao userDao;
    private final AuthorityServiceL2 authorityServiceL2;

    @Autowired
    public UserServiceL2Impl(UserDao userDao, AuthorityServiceL2 authorityServiceL2) {
        this.userDao = userDao;
        this.authorityServiceL2 = authorityServiceL2;
    }

    @Override
    public boolean exists(Integer id) {
        User user = userDao.findOne(id);
        if (user != null)
            return true;
        else
            return false;
    }

    @Override
    public boolean isValid(PrivateUserDetails privateUserDetails) {
        //TODO: papildyti!
        return true;
    }

    @Override
    public PrivateUserDetails create(String username, String password, String... authorities) {
        User user = new User(null, username, password, true, 0, 0);
        user = userDao.save(user);
        authorityServiceL2.save(user, authorities);
        return getUser(user.getId());
    }

    @Override
    public PrivateUserDetails save(PrivateUserDetails privateUserDetails) {
        //TODO: parasyti normalias is vieno i kita konvertavimo klases
        Integer id = privateUserDetails.getId();
        User user = new User(id, privateUserDetails.getUsername(), privateUserDetails.getPassword(), true,
                privateUserDetails.getX(), privateUserDetails.getY());
        user = userDao.save(user);
        return getUser(id);
    }

    @Override
    public PrivateUserDetails getUser(String username) {
        User user = userDao.findByUsername(username);
        List<GrantedAuthority> grantedAuthorities = authorityServiceL2.getAuthorities(user.getUsername());
        Integer x_coord = user.getX() == null ? 0 : user.getX();
        Integer y_coord = user.getY() == null ? 0 : user.getY();
        return new PrivateUserDetails(user.getId(), user.getUsername(), user.getPassword(), true, true, true, true,
                grantedAuthorities,
                "Paksas", x_coord, y_coord);
    }

    @Override
    public PrivateUserDetails getUser(int id) {
        User user = userDao.findOne(id);
        List<GrantedAuthority> grantedAuthorities = authorityServiceL2.getAuthorities(user.getUsername());
        Integer x_coord = user.getX() == null ? 0 : user.getX();
        Integer y_coord = user.getY() == null ? 0 : user.getY();
        return new PrivateUserDetails(id, user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities,
                "Paksas", x_coord, y_coord);
    }

    @Override
    public PrivateUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {

        }
        PrivateUserDetails privateUserDetails = (PrivateUserDetails) authentication.getPrincipal();
        User user = userDao.findByUsername(privateUserDetails.getUsername());
        return new PrivateUserDetails(user.getId(), user.getUsername(), user.getPassword(), true, true, true, true,
                privateUserDetails.getAuthorities(), privateUserDetails.getLastName(), user.getX(), user.getY());
    }
}
