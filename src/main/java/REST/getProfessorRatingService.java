package REST;

import entities.ProfessorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/getProfessorRating")
public class getProfessorRatingService {

    @PersistenceContext(unitName = "DADemoPU")
    private EntityManager em;

    @GET
    @Produces("text/xml")
    @Path("/{surname:[a-zA-Z]*}")
    public String getProfessorRating(@PathParam("surname") String surname){
        System.out.println("Print Fetching rating for professor: " + surname);
        for (ProfessorEntity p:
                em.createQuery("SELECT p FROM ProfessorEntity p", ProfessorEntity.class).getResultList()) {
            if(p.getSurname().equals(surname)){
                return ((Integer)p.getRating()).toString();
            }
        }
        return "No rating found";
    }
}
