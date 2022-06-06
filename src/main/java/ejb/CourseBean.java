package ejb;

import entities.CourseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;



@jakarta.ejb.Stateless(name = "CourseEJB")
public class CourseBean {

    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;

    public CourseBean() {
    }

    public List<CourseEntity> getAllCourses() {

    }
}
