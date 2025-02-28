package priv.mansour.school.controller;

import static priv.mansour.school.utils.Constants.STUDENT;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import priv.mansour.school.entity.Student;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.services.IStudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final IStudentService studentService;

    @Autowired
    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
        GlobalLogger.infoCreate(STUDENT, "Creating new student: " + student.getId());
        Student createdStudent = studentService.add(student);
        GlobalLogger.infoSuccess("Created", STUDENT, "Student ID: " + createdStudent.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        GlobalLogger.infoReadAll(STUDENT);
        List<Student> students = studentService.getAll();
        GlobalLogger.infoSuccess("Fetched all", STUDENT, "Total students: " + students.size());
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable @NotBlank String id) {
        GlobalLogger.infoRead(STUDENT, "Fetching student by ID: " + id);
        Student student = studentService.getById(id);
        GlobalLogger.infoSuccess("Fetched by ID", STUDENT, "Student ID: " + student.getId());
        return ResponseEntity.ok(student);
    }

    @GetMapping("/by-mail/{mail}")
    public ResponseEntity<Student> getStudentByMail(@PathVariable @Email String mail) {
        GlobalLogger.infoRead(STUDENT, "Fetching student by Email: " + mail);
        Student student = studentService.findByEmail(mail);
        GlobalLogger.infoSuccess("Fetched by Email", STUDENT, "Student Email: " + mail);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable @NotBlank String id, @Valid @RequestBody Student studentToUpdate) {
        GlobalLogger.infoUpdate(STUDENT, id, studentToUpdate);
        Student updatedStudent = studentService.update(id, studentToUpdate);
        GlobalLogger.infoSuccess("Updated", STUDENT, " ID: " + id);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable @NotBlank String id) {
        GlobalLogger.infoDelete(STUDENT, "Deleting student ID: " + id);
        studentService.deleteById(id);
        GlobalLogger.infoSuccess("Deleted", STUDENT, "Student ID: " + id);
        return ResponseEntity.noContent().build();
    }
}
