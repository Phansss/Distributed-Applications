package ejb;

import Exceptions.AlreadyHasException;
import entities.CourseEntity;
import entities.PersonEntity;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
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
