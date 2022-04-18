package se.iths.utils;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton // endast en instans i programmet
@Startup
public class SampleData {

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct // kör denna metod efter att programmet startats
    public void generateData(){
        Teacher teacher1 = new Teacher("Benny", "Larsson", "bennyL@skola.se", "012345");
        Teacher teacher2 = new Teacher("Kenny", "Larsson", "kennyL@skola.se", "123321");

        Subject subject1 = new Subject("English");
        Subject subject2 = new Subject("Swedish");
        Subject subject3 = new Subject("Physics");
        Subject subject4 = new Subject("TestSubject");

        Student student1 = new Student("a123123", "b123123", "a@mail.se", "d123123123123");
        Student student2 = new Student("123123123", "12312312312", "b@mail.se", "d12312123123123123123123");

        student1.addSubject(subject1);
        student1.addSubject(subject2);
        student2.addSubject(subject1);

        entityManager.persist(student1);
        entityManager.persist(student2);

        teacher1.addSubject(subject1);
        teacher2.addSubject(subject2);
        teacher2.addSubject(subject3);


        entityManager.persist(subject1);
        entityManager.persist(subject2);
        entityManager.persist(subject3);
        entityManager.persist(subject4);
        entityManager.persist(teacher1);
        entityManager.persist(teacher2);

    }

}
