package priv.mansour.school.repository;

import priv.mansour.school.entity.Admin;

import java.util.Optional;

public interface IFindByEmail <T>{
    Optional<T> findByMail(String mail);
}
