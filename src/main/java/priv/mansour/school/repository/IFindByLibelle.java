package priv.mansour.school.repository;

import java.util.Optional;

public interface IFindByLibelle<T> {
    Optional<T> findByLibelle(String libelle);
}