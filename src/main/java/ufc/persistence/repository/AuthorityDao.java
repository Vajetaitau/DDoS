package ufc.persistence.repository;

import ufc.persistence.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorityDao extends JpaRepository<Authority, Integer> {

    @Query("select a from Authority as a where a.user.username = :username")
    List<Authority> findByUsername(@Param("username") String username);

}
