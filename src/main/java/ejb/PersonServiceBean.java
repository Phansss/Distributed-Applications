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
        System.out.println("PRINT MSG: getPersonCourses");
        System.out.println(personId);

        PersonEntity person = em.find(PersonEntity.class, personId);
        for (CourseEntity c : person.getSubscribedCourses()) {
            System.out.println(c.getName());
        }
        return person.getSubscribedCourses();
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
