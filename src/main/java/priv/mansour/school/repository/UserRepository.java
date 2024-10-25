package priv.mansour.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import priv.mansour.school.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
