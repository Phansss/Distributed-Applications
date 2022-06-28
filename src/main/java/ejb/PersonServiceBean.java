package ejb;

import entities.CourseEntity;

import entities.PersonEntity;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@jakarta.ejb.Stateless(name = "PersonServiceEJB")
public class PersonServiceBean implements Serializable {



    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;

    public PersonServiceBean() {
    }

    public List<CourseEntity> getPersonCourses(Integer personId) {
        System.out.println("PRINT MSG: getPersonCourses");
        System.out.println(personId);

        PersonEntity person = em.find(PersonEntity.class, personId);
        for (CourseEntity c : person.getSubscribedCourses()) {
            System.out.println(c.getName());
        }
        return person.getSubscribedCourses();
    }


    @Timeout
    public void optionLogOut() {
        System.out.println("Stay logged in?");
    }
    public PersonEntity getPersonEntity(Integer userId) {
       return em.find(PersonEntity.class, userId);
    }

    /**
     * @param person the person whom wants to subsribe to the given courses
     * @param courses an absolute list of given courses to which the person wants to subscribe.
     * @Post:

     */
    public void subscribeToCourses(PersonEntity person, List<CourseEntity> courses) {
        person.getSubscribedCourses().clear();
        courses.forEach((course) -> {
            person.addCourse(course);
        }) ;
    }



}
