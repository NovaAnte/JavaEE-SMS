package se.iths.service;

import se.iths.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class StudentService {

    @PersistenceContext
    EntityManager entityManager;

    public void addStudent(Student student) {
        entityManager.persist(student);
    }

    public Student findStudentById(Long id) {
        return entityManager.find(Student.class, id);
    }

    public List<Student> getAllStudents() {
        return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public void updateStudent(Student student) {
        entityManager.merge(student);
    }

    public void deleteStudent(Long id) {
        Student foundStudent = entityManager.find(Student.class, id);
        entityManager.remove(foundStudent);
    }

    public List<Student> getStudentByLastName(String lastName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s FROM Student s WHERE s.lastName = '");
        sb.append(lastName);
        sb.append("'");
        return entityManager.createQuery(sb.toString(), Student.class).getResultList();
    }

    public boolean isEmailRegistered(String email) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.email FROM Student s WHERE s.email = '");
        sb.append(email);
        sb.append("'");
        List<String> emailResult = entityManager.createQuery(sb.toString(), String.class).getResultList();

        return emailResult.size() > 0;
    }

}