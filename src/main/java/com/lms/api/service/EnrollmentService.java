package com.lms.api.service;

import com.lms.api.model.Course;
import com.lms.api.model.Enrollment;
import com.lms.api.model.User;
import com.lms.api.repository.CourseRepository;
import com.lms.api.repository.EnrollmentRepository;
import com.lms.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    // ✅ Enroll single student in single course
    public Enrollment enrollStudentInCourse(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        boolean alreadyEnrolled = enrollmentRepository.findByStudentId(studentId)
                .stream()
                .anyMatch(e -> e.getCourse().getId().equals(courseId));

        if (alreadyEnrolled) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus("ENROLLED");

        return enrollmentRepository.save(enrollment);
    }

    // ✅ Enroll in multiple courses
    public List<Enrollment> enrollStudentInMultipleCourses(Long studentId, List<Long> courseIds) {
        return courseIds.stream()
                .map(courseId -> enrollStudentInCourse(studentId, courseId))
                .collect(Collectors.toList());
    }

    // ✅ Get all enrollments
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // ✅ Get enrollments for one student
    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    // ✅ Get only the courses for a student
    public List<Course> getCoursesForStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());
    }
}
