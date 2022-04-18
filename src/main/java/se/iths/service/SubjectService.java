package se.iths.service;

import se.iths.entity.Subject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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

    public void updateSubject(Subject subject) {
        entityManager.merge(subject);
    }

    public void deleteSubject(Long id) {
        Subject foundSubject = entityManager.find(Subject.class, id);
        entityManager.remove(foundSubject);
    }

}
