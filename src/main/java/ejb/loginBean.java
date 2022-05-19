package ejb;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entities.PersonEntity;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.UserTransaction;

@ManagedBean(name = "loginBean")
@SessionScoped
public class loginBean implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dbPassword;
    private String dbName;
    @PersistenceContext(unitName = "DADemoPU")
    EntityManager em;
    @Inject
    UserTransaction ut;
    public loginBean() {
        // SETUP DB CONNECTION
    }
    public String getDbPassword() {
        return dbPassword;
    }
    public String getDbName() {
        return dbName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String name) {
        this.firstName = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Try to add a user based on information they have entered. Return Success or No Success based on the outcome to control navigation
    public String add() {
        boolean success = false;
        if (firstName != null && lastName != null && email != null && password != null) {
            try {

                ut.begin();
                em.joinTransaction();
                em.createNativeQuery("INSERT INTO Person (name, lastname, email, password) VALUES (?,?,?,?)")
                        .setParameter(1, firstName)
                        .setParameter(2, lastName)
                        .setParameter(3, email)
                        .setParameter(4, password)
                        .executeUpdate();
                ut.commit();

                success = true;

            } catch (Exception e) {
                System.out.println(e);
                firstName = e.toString();
            }
        }
        if (success) {
            return "success";
        } else
            return "no success";
    }

    public String login() {

        Query query = em.createQuery("SELECT p FROM PersonEntity p WHERE p.email = :email AND p.password = :password", PersonEntity.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<PersonEntity> resultList = (List<PersonEntity>) query.getResultList();
        for (PersonEntity p: resultList) {
            dbName = p.getEmail();
            dbPassword = p.getPassword();
        }

        if (email.equals(dbName) && password.equals(dbPassword)) {
            return "success";
        } else
            return "no success";
        // TODO: MAKE THE USER ABLE TO SEE THAT THEY WROTE SOMETHING WRONG
    }

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        FacesContext.getCurrentInstance()
                .getApplication().getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml");
    }
}