package ejb;

import java.io.Serializable;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import SOAP.professorValidator;
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
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.xml.ws.WebServiceRef;

@ManagedBean(name = "wieiswieBean")
@SessionScoped
public class wieiswieBean implements Serializable {

    /*
    @WebServiceRef
    private static professorValidator professorValidator;
*/
    private int professorId;

    private ProfessorEntity professor;

    public wieiswieBean() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try{
            professorId = (int) session.getAttribute("wieiswie");
        } catch (Exception e){
            professorId = 1;
        }
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DADemoPU");
        EntityManager new_em = factory.createEntityManager();

        // Have the EntityManager find the loggedin user
        professor = new_em.find(ProfessorEntity.class, professorId);
        /*
        if(!professorValidator.isRegisteredProfessor(professor.getName(), professor.getSurname())){
            professor = null;
        }*/
    }

    public String getRatingFromId(){
        URI uri = UriBuilder.fromUri("http://localhost:8080/DADemo_Web_exploded/api/getProfessorRating/" + professorId).build();
        Response response = ClientBuilder.newClient().target(uri).request(MediaType.TEXT_PLAIN).buildGet().invoke();
        return response.readEntity(String.class);
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