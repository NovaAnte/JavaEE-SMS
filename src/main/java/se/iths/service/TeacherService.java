package se.iths.service;

import se.iths.entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class TeacherService {

    @PersistenceContext
    EntityManager entityManager;

    public Teacher createTeacher(Teacher teacher) {

        // Adding subjects for demo purposes
        // teacher.addSubject(new Subject("Svenska", teacher));

        entityManager.persist(teacher);
        return teacher;
    }

    public Teacher findTeacherById(Long id) {
        return entityManager.find(Teacher.class, id);
    }

    public void updateTeacher(Teacher teacher) {
        entityManager.merge(teacher);
    }

    public void deleteTeacher(Long id) {
        Teacher foundTeacher = entityManager.find(Teacher.class, id);
        entityManager.remove(foundTeacher);
    }

    public boolean isEmailRegistered(String email)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.email FROM Teacher s WHERE s.email = '");
        sb.append(email);
        sb.append("'");
        List<String> emailResult = entityManager.createQuery(sb.toString(), String.class).getResultList();

        return emailResult.size() > 0;
    }
}
