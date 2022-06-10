package ejb;

import entities.CourseEntity;
import entities.PersonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.io.Serializable;
import java.util.List;

@jakarta.ejb.Stateless(name = "MySessionBeanEJB")
public class MySessionBean implements Serializable {

    @PersistenceContext(unitName = "DADemoPU")
    EntityManager em;

    public MySessionBean() {
    }

    public int doSomethingReallyDifficult(int a, int b) {
        return  a + b;
    }

    public List<PersonEntity> findPersonByName(String name) {
        Query query = em.createQuery("SELECT p FROM PersonEntity p WHERE p.name = :searchname", PersonEntity.class);
        query.setParameter("searchname", "%" + name + "%");
        return query.getResultList();
    }

    public List<CourseEntity> getAllCourses() {
        System.out.println("PRINT MSG: Creating Query");
        Query query = em.createQuery("SELECT c FROM CourseEntity c", CourseEntity.class);
        return query.getResultList();
    }




}