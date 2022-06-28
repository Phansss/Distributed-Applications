package SOAP;

import entities.ProfessorEntity;
import jakarta.ejb.Stateless;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


// TODO: WHY CANT I ADD @WEBSERVICE ANYMORE?

@Stateless
public class professorValidator implements Validator {

    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;

    @WebResult(name = "isRegistered")
    public boolean isRegisteredProfessor(String name, String surname){
        for (ProfessorEntity p: em.createQuery("SELECT p FROM ProfessorEntity p", ProfessorEntity.class).getResultList()
        ) {
            if(p.getName().equals(name) && p.getSurname().equals(surname)){
                return true;
            }
        }
        return false;
    }
}

