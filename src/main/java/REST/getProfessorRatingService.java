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
    @Produces("text/plain")
    @Path("/{id: \\d+}")
    public String getProfessorRating(@PathParam("id") int id){
        return ((Integer)((ProfessorEntity) em.find(ProfessorEntity.class, id)).getRating()).toString();
    }
}
