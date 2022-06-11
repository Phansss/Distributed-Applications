package ejb;

import entities.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;


@jakarta.ejb.Stateless(name = "CourseServiceEJB")
public class CourseServiceBean {

    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;

    public CourseServiceBean() {
    }


    @Loggable
    public List<CourseEntity> getAllCourses() {
        List<CourseEntity> results = em.createQuery("SELECT c FROM CourseEntity c", CourseEntity.class).getResultList();
        return results;
    }





}
