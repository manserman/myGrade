package priv.mansour.school.controller;

import static priv.mansour.school.utils.Constants.TEACHER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import priv.mansour.school.entity.Teacher;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.services.IUserService;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final IUserService<Teacher> teacherService;

    @Autowired
    public TeacherController(IUserService<Teacher> teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<Teacher> addTeacher(@Valid @RequestBody Teacher teacher) {
        GlobalLogger.infoCreate(TEACHER, teacher);
        Teacher addedTeacher = teacherService.add(teacher);
        GlobalLogger.infoSuccess("Added", TEACHER, addedTeacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTeacher);
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        GlobalLogger.infoReadAll(TEACHER);
        List<Teacher> teachers = teacherService.getAll();
        GlobalLogger.infoSuccess("Retrieved all ", TEACHER, ". Total: " + teachers.size());
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable String id) {
        GlobalLogger.infoRead(TEACHER, id);
        Teacher teacher = teacherService.getById(id);
        GlobalLogger.infoSuccess("Retrieved by ID:", TEACHER, teacher.getId());
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/by-mail/{mail}")
    public ResponseEntity<Teacher> getTeacherByMail(@PathVariable @Email String mail) {
        GlobalLogger.infoRead(TEACHER, mail);
        Teacher teacher = teacherService.findByEmail(mail);
        GlobalLogger.infoSuccess("Retrieved by Mail:", TEACHER, mail);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacherById(@PathVariable String id) {
        GlobalLogger.infoDelete(TEACHER, id);
        teacherService.deleteById(id);
        GlobalLogger.infoSuccess("Deleted by ID:", TEACHER, id);
        return ResponseEntity.noContent().build();
    }
}
