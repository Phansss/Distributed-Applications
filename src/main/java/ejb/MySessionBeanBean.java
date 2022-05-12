package ejb;

import entities.PersonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@jakarta.ejb.Stateless(name = "MySessionBeanEJB")
public class MySessionBeanBean {

@PersistenceContext(unitName = "DADemoPU")
    EntityManager em;

    public MySessionBeanBean() {
    }

    public int doSomethingReallyDifficult(int a, int b) {
        return  a + b;
    }

    public List<PersonEntity> findPersonByName(String name) {
        Query query = em.createQuery("SELECT p FROM PersonEntity p WHERE p.name = :searchname", PersonEntity.class);
        query.setParameter("searchname", "%" + name + "%");
        return query.getResultList();
    }
}
