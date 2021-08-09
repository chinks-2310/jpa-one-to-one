package com.example.jpaonetoonedemo.controller;


import com.example.jpaonetoonedemo.model.Student;
import com.example.jpaonetoonedemo.repository.StudentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public Student getStudentByID(@PathVariable Long id) throws NotFoundException {
        Optional opStudent = studentRepository.findById(id);
        if(opStudent.isPresent()) {
            return (Student) opStudent.get();
        } else{
            throw new NotFoundException("Student not found with id "+ id);
        }
    }

    @PostMapping("/create/students")
    public Student createStudent(@RequestBody Student student) {
        return (Student) studentRepository.save(student);
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id,
                                 @RequestBody Student studentUpdated) throws NotFoundException {
        Optional st = studentRepository.findById(id);
        if(st.isPresent()) {
            Student student = (Student) st.get();
            student.setName(studentUpdated.getName());
            student.setAge(studentUpdated.getAge());
            return student;
        }
        else{
            throw new NotFoundException("Student not found with id "+ id);
        }
    }

    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) throws NotFoundException {
        Optional st = studentRepository.findById(id);
        if(st.isPresent()){
            Student student = (Student) st.get();
            studentRepository.delete(student);
            return "Deleted Successfully";
        } else {
            throw new NotFoundException("Student not found with id "+id);
        }
    }
}
