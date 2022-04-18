package se.iths.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher {

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Subject> subjectList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String phoneNumber;
    @Size (min = 2)
    @NotEmpty
    private String firstName;
    @Size (min = 2)
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Column(unique = true)
    private String email;

    public Teacher (){

    }

    public Teacher(String firstName, String lastName, String email, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public void addSubject(Subject subject) {
        subjectList.add(subject);
        subject.setTeacher(this);
    }

    @JsonbTransient
    public List<Subject> getSubjectList() {
        return subjectList;
    }


    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String department) {
        this.email = department;
    }
}
