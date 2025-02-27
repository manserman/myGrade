package priv.mansour.school.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import priv.mansour.school.entity.Admin;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String>, IFindByEmail<Admin> {


}
