package priv.mansour.school.controller;

import static priv.mansour.school.utils.Constants.TEACHER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
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
        return ResponseEntity.ok(teacherService.add(teacher));
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        GlobalLogger.infoReadAll(TEACHER);
        return ResponseEntity.ok(teacherService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable String id) {
        GlobalLogger.infoRead(TEACHER, id);
        return ResponseEntity.ok(teacherService.getById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacherById(@PathVariable String id) {
        GlobalLogger.infoDelete(TEACHER, id);
        teacherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
