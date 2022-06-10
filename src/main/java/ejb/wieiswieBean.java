package ejb;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entities.PersonEntity;
import entities.ProfessorEntity;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;

@ManagedBean(name = "wieiswieBean")
@SessionScoped
public class wieiswieBean implements Serializable {

    private int professorId;

    private ProfessorEntity professor;

    public wieiswieBean() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        professorId = (int) session.getAttribute("wieiswie");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DADemoPU");
        EntityManager new_em = factory.createEntityManager();

        // Have the EntityManager find the loggedin user
        professor = new_em.find(ProfessorEntity.class, professorId);
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public ProfessorEntity getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorEntity professor) {
        this.professor = professor;
    }
}