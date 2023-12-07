package questapp.forumSite.dataAccess;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import questapp.forumSite.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserName(String userName);

}
