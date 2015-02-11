package ufc.core.service.secondLayer.inpl;

import ufc.core.service.secondLayer.AuthorityServiceL2;
import ufc.persistence.entity.Authority;
import ufc.persistence.entity.User;
import ufc.persistence.repository.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("authorityServiceL2")
public class AuthorityServiceL2Impl implements AuthorityServiceL2 {

    private final AuthorityDao authorityDao;

    @Autowired
    public AuthorityServiceL2Impl(AuthorityDao authorityDao) {
        this.authorityDao= authorityDao;
    }

    @Override
    public List<GrantedAuthority> getAuthorities(String username) {
        List<Authority> authorities = authorityDao.findByUsername(username);
        String[] authorityRoles = new String[authorities.size()];
        for (int i = 0; i < authorities.size(); i++) {
            authorityRoles[i] = authorities.get(i).getAuthority();
        }
        return AuthorityUtils.createAuthorityList(authorityRoles);
    }

    @Override
    public void save(User user, String... authorities) {
        for (String authority: authorities) {
            Authority a = new Authority(user, authority);
            a = authorityDao.save(a);
        }
    }
}
