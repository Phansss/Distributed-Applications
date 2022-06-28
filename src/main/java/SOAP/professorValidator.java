package SOAP;

import entities.ProfessorEntity;
import jakarta.ejb.Stateless;
import jakarta.jws.WebMethod;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

import static jakarta.jws.soap.SOAPBinding.Style.RPC;
import static jakarta.jws.soap.SOAPBinding.Use.LITERAL;


// TODO: WHY CANT I ADD @WEBSERVICE ANYMORE?

@Stateless
@WebService
@SOAPBinding(style = RPC, use = LITERAL)
public class professorValidator implements Validator {

    @WebMethod
    @WebResult(name = "isRegistered")
    public boolean isRegisteredProfessor(String name, String surname){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DADemoPU");
        EntityManager em = emf.createEntityManager();
        System.out.println("Print Checking professor: " + name + " " + surname);
        for (ProfessorEntity p: em.createQuery("SELECT p FROM ProfessorEntity p", ProfessorEntity.class).getResultList()
        ) {
            if(p.getName().equals(name) && p.getSurname().equals(surname)){
                System.out.println("Print Professor Exists");
                return true;
            }
        }
        System.out.println("Print Professor does not exist");
        return false;
    }
}

