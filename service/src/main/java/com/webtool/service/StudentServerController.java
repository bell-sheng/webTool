package com.webtool.service;


import com.webtool.api.StudentServerApi;
import com.webtool.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StudentServerController implements StudentServerApi {
    @Override
    public ResponseEntity<Void> addStudent(Student body) {
        log.info("start to add student:{}", body);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Student> showStudentById(Integer id) {
        log.info("start to get student:{}", id);
        Student student = new Student();
        student.setId(id);
        student.age(18);
        student.setSex(Student.SexEnum.BOY);
        return ResponseEntity.ok(student);
    }
}
