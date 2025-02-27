package priv.mansour.school.services;

import priv.mansour.school.entity.User;

public interface IUserService<T extends User> extends ICrudService<T,String>{
    T findByEmail(String mail);
}
