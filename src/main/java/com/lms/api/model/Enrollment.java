package com.lms.api.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Student who enrolled
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // Course enrolled in
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private String status; // e.g., ENROLLED, COMPLETED

    public void setStudent(User student) {
        this.student = student;
    }
    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse(){
        return course;
    }
    public void setStatus(String status){
        this.status = status;
    }

}
