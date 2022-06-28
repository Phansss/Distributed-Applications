package ejb;

import Exceptions.AlreadyHasException;
import Interceptors.Loggable;
import entities.CourseEntity;
import entities.PersonEntity;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.logging.Logger;


@Loggable
@jakarta.ejb.Stateless(name = "CourseServiceEJB")
public class CourseServiceBean {

    //@Inject
    //private Logger logger;

    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;

    public CourseServiceBean() {
    }
    public List<CourseEntity> getAllCourses() {
        List<CourseEntity> results = em.createQuery("SELECT c FROM CourseEntity c", CourseEntity.class).getResultList();
        return results;
    }

    public CourseEntity getCourseEntity(Integer courseId) {
        return em.find(CourseEntity.class, courseId);
    }

    public void addCourseToPerson(Integer courseId, Integer personId) {
        em.find(CourseEntity.class, courseId)
                .getFollowedByPersons()
                .add(em.find(PersonEntity.class, personId));
    }


    public void removeCourseFromPerson(Integer courseId, Integer personId) {
        em.find(CourseEntity.class, courseId)
                .getFollowedByPersons()
                .remove(em.find(PersonEntity.class, personId));
    }



}
