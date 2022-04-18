package se.iths.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String subjectName;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany
    private Set<Student> studentSet = new HashSet<>();

    public Subject(){

    }

    public Subject(String subjectName){
        this.subjectName = subjectName;
    }

    public void addStudent(Student student){
        studentSet.add(student);
    }

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
