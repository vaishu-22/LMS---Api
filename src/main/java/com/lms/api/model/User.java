package com.lms.api.model;


    import jakarta.persistence.*;
    import lombok.Data;

@Data
    @Entity
    @Table(name = "users")
    public class User {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Column(unique = true, nullable = false)
        private String email;

        private String password;

        private String role; // e.g., ADMIN, STUDENT, INSTRUCTOR

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password= password;
    }
    public void setRole(String role){
        this.role=role;
    }
    public String getRole(){
        return role;
    }


}
