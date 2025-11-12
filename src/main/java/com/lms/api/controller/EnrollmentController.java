package com.lms.api.controller;

import com.lms.api.model.Course;
import com.lms.api.model.Enrollment;
import com.lms.api.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // ✅ Enroll one student in one course
    @PostMapping("/enroll/{studentId}/{courseId}")
    public Enrollment enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        return enrollmentService.enrollStudentInCourse(studentId, courseId);
    }

    // ✅ Enroll one student in multiple courses
    @PostMapping("/enroll-multiple/{studentId}")
    public List<Enrollment> enrollMultiple(@PathVariable Long studentId, @RequestBody List<Long> courseIds) {
        return enrollmentService.enrollStudentInMultipleCourses(studentId, courseIds);
    }

    // ✅ Get all enrollments
    @GetMapping("/all")
    public List<Enrollment> getAll() {
        return enrollmentService.getAllEnrollments();
    }

    // ✅ Get all enrollments for one student
    @GetMapping("/student/{studentId}")
    public List<Enrollment> getByStudent(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudentId(studentId);
    }

    // ✅ Get only courses of a student
    @GetMapping("/student/{studentId}/courses")
    public List<Course> getCoursesByStudent(@PathVariable Long studentId) {
        return enrollmentService.getCoursesForStudent(studentId);
    }
}
