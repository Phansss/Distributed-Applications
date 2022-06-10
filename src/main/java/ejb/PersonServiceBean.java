package ejb;

import entities.CourseEntity;

import entities.PersonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@jakarta.ejb.Stateless(name = "PersonServiceEJB")
public class PersonServiceBean {



    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;

    public PersonServiceBean() {
    }

    public List<CourseEntity> getPersonCourses(Integer personId) {
        PersonEntity person = em.find(PersonEntity.class, personId);
        return person.getFollowingCourses();
    }





}
