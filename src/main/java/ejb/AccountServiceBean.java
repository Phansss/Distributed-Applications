package ejb;

import entities.PersonEntity;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;

import java.util.Calendar;
import java.util.Date;

@jakarta.ejb.Stateless(name = "AccountServiceEJB")
public class AccountServiceBean {

    @Resource
    SessionContext sessionContext;

    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;
    //@Inject Logger logger;

    public AccountServiceBean() {
    }

    public void createAccount(String email, String lastName, String name, String password) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        PersonEntity newAccount = new PersonEntity();
        newAccount.setEmail(email);
        newAccount.setLastName(lastName);
        newAccount.setName(name);
        newAccount.setPassword(password);
        em.persist(newAccount);

        ScheduleExpression changePasswordReminder = new ScheduleExpression().dayOfMonth(calendar.get(Calendar.MONTH))
                .month(calendar.get(Calendar.YEAR));
        sessionContext.getTimerService().createCalendarTimer(changePasswordReminder, new TimerConfig(newAccount, true));
    }

    @Timeout
    public void sendPasswordReminder(Timer timer) {
        PersonEntity newAccount = (PersonEntity) timer.getInfo();
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
