package priv.mansour.school.services;

import jakarta.validation.constraints.Email;
import priv.mansour.school.entity.Admin;

public interface IAdminService extends ICrudService<Admin, String> {
           Admin findByEmail(String email);

}
