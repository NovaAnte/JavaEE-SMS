package se.iths.service;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class SubjectService {

    @PersistenceContext
    EntityManager entityManager;

    public Subject createSubject(Subject subject) {
        entityManager.persist(subject);
        return subject;
    }

    public Subject findSubjectById(Long id) {
        return entityManager.find(Subject.class, id);
    }

    public List<Subject> findAllSubjects(){
        return entityManager.createQuery("SELECT s FROM Subject s", Subject.class).getResultList();
    }

    public void updateSubject(Subject subject) {
        entityManager.merge(subject);
    }

    public void deleteSubject(Long id) {
        Subject foundSubject = entityManager.find(Subject.class, id);
        entityManager.remove(foundSubject);
    }

    public Student findStudentById(Long id) {
        return entityManager.find(Student.class, id);
    }

    public Teacher findTeacherById(Long id) {
        return entityManager.find(Teacher.class, id);
    }

    public void linkStudent(Subject subject, Student student) {
        student.addSubject(subject);
        entityManager.merge(subject);
    }

    public void linkTeacher(Subject subject, Teacher teacher){
        teacher.addSubject(subject);
        entityManager.merge(subject);
    }

}
