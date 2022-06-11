package ejb;

import entities.PersonEntity;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;

@jakarta.ejb.Stateless(name = "AccountServiceEJB")
public class AccountServiceBean {

    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;
    //@Inject Logger logger;

    public AccountServiceBean() {
    }

    public void createAccount(String email, String lastName, String name, String password) {
        PersonEntity newAccount = new PersonEntity();
        newAccount.setEmail(email);
        newAccount.setLastName(lastName);
        newAccount.setName(name);
        newAccount.setPassword(password);
        em.persist(newAccount);
    }

    @PostConstruct
    public void printer() {
        System.out.println("Created account EJB");
    }

    //invocationContext interface @page 46 of the course.
    /*@AroundInvoke
    private Object logMethod(InvocationContext ic) throws Exception {
        logger.entering(ic.getTarget().toString(), ic.getMethod().getName());
        try {
            return ic.proceed();
        } finally {
            logger.exiting(ic.getTarget().toString(), ic.getMethod().getName());
        }
    }*/
}
