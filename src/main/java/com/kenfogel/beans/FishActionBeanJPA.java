package com.kenfogel.beans;

import com.kenfogel.entities.Fish;
import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * A magic bean that returns a list of fish
 *
 * @author Ken
 *
 */
@Named
@RequestScoped
public class FishActionBeanJPA implements Serializable {

    @PersistenceContext(unitName = "fishiesPU")
    private EntityManager em;
    
    public List<Fish> getAll() {
        
        // Object oriented criteria builder
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Fish> cq = cb.createQuery(Fish.class);
        Root<Fish> fish = cq.from(Fish.class);
        cq.select(fish);
        TypedQuery<Fish> query = em.createQuery(cq);

        // Using a named query
//        TypedQuery<Fish> query =  entityManager.createNamedQuery("Fish.findAll", Fish.class);

        // Execute the query
        List<Fish> fishies = query.getResultList();

        return fishies;
    }
}
