package priv.mansour.school.services;

import com.mongodb.DuplicateKeyException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import priv.mansour.school.entity.Teacher;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.repository.TeacherRepository;

import java.util.List;

import static priv.mansour.school.utils.Constants.TEACHER;

@Service
@Validated
public class TeacherServiceImpl implements IUserService<Teacher> {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher add(@Valid Teacher teacher) {
        try {
            return teacherRepository.save(teacher);
        } catch (DuplicateKeyException e) {
            throw new priv.mansour.school.exceptions.DuplicateKeyException("Teacher already in base", e);
        }

    }

    public List<Teacher> getAll() {
        return teacherRepository.findAll();

    }

    public Teacher getById(@NotBlank String id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TEACHER, "READ", "Teacher not found for ID: " + id));
    }

    @Override
    public Teacher update(String s, Teacher updatedEntity) {
        return null;
    }

    public void deleteById(@NotBlank String id) {

        if (!teacherRepository.existsById(id)) {
            throw new ResourceNotFoundException(TEACHER, "DELETE", "Teacher not found for ID: " + id);
        }
        teacherRepository.deleteById(id);
    }

    @Override
    public Teacher findByEmail(String mail) {
        return null;
    }
}
