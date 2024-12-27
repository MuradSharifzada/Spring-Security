package com.spring.controller;

import com.spring.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {


    List<Student> students = new ArrayList<>(Arrays.asList(
            new Student(1, "Murad", 19),
            new Student(2, "Test", 18)));


    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return students;
    }

    @PostMapping("/students")
    public String createStudent(@RequestBody Student student) {
        boolean isStudentAdded = students.add(student);
        if (isStudentAdded) {
            return "Student Added Successfully";
        }
        return "error occur creating student";
    }
}
