package priv.mansour.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import priv.mansour.school.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
