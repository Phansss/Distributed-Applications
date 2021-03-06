package ejb;

import entities.ProfessorEntity;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Startup
public class SingletonCacheBean {

    private final Map<Long, Object> cache = new HashMap<>();

    public void addToCache(Long id, Object object) {
        if (!cache.containsKey(id))
            cache.put(id, object);
    }

    public void removeFromCache(Long id) {
        cache.remove(id);
    }
    public Object getFromCache(Long id) {
        if(cache.isEmpty()){
            fillCache();
        }
        if (cache.containsKey(id))
            return cache.get(id);
        else
            return null;
    }

    public void fillCache(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DADemoPU");
        EntityManager singlebean_em = emf.createEntityManager();
        for (ProfessorEntity p: singlebean_em.createQuery("Select P from ProfessorEntity P", ProfessorEntity.class).getResultList()
        ) {
            addToCache((long) p.getId(), p.getPicture());
        }
    }
}
