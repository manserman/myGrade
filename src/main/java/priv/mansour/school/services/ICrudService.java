package priv.mansour.school.services;

import java.util.List;

public interface ICrudService<T, ID> {
    T add(T t);
    List<T> getAll();
    T getById(ID id);
    T update(ID id, T updatedEntity);
    void deleteById(ID id);
}
