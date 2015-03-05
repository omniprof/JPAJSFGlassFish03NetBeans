package com.kenfogel.beans;

import com.kenfogel.beans.exceptions.NonexistentEntityException;
import com.kenfogel.beans.exceptions.RollbackFailureException;
import com.kenfogel.entities.Fish;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

/**
 *
 * @author kfogel
 */
@Named
@SessionScoped
public class FishJpaController implements Serializable {

    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext(unitName = "fishiesPU")
    private EntityManager entityManager;

    /**
     * Default constructor
     */
    public FishJpaController() {
    }

    /**
     * Take a new or detached entity and add it as a new record in the table
     * 
     * @param fish
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void create(Fish fish) throws RollbackFailureException, Exception {
        try {
            userTransaction.begin();
            entityManager.persist(fish);
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    /**
     * Take a detached entity and update the matching record in the table
     * 
     * @param fish
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void edit(Fish fish) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            userTransaction.begin();
            fish = entityManager.merge(fish);
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fish.getId();
                if (findFish(id) == null) {
                    throw new NonexistentEntityException("The fish with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    /**
     * Delete the record that matched the primary key. Verify that the record exists before deleting it.
     * 
     * @param id
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            userTransaction.begin();
            Fish fish;
            try {
                fish = entityManager.getReference(Fish.class, id);
                fish.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fish with id " + id + " no longer exists.", enfe);
            }
            entityManager.remove(fish);
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | NonexistentEntityException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    /**
     * Return all the records in the table
     * 
     * @return 
     */
    public List<Fish> findFishEntities() {
        return findFishEntities(true, -1, -1);
    }

    /**
     * Return some of the records from the table. Useful for paginating.
     * 
     * @param maxResults
     * @param firstResult
     * @return 
     */
    public List<Fish> findFishEntities(int maxResults, int firstResult) {
        return findFishEntities(false, maxResults, firstResult);
    }

    /**
     * Either find all or find a group of fish
     * 
     * @param all True means find all, false means find subset
     * @param maxResults Number of records to find
     * @param firstResult Record number to start returning records
     * @return 
     */
    private List<Fish> findFishEntities(boolean all, int maxResults, int firstResult) {
            CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fish.class));
            Query q = entityManager.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
    }

    /**
     * Find a record by primary key
     * 
     * @param id
     * @return 
     */
    public Fish findFish(Integer id) {
            return entityManager.find(Fish.class, id);
    }

    /**
     * Return the number of records in the table
     * 
     * @return 
     */
    public int getFishCount() {
            CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
            Root<Fish> rt = cq.from(Fish.class);
            cq.select(entityManager.getCriteriaBuilder().count(rt));
            Query q = entityManager.createQuery(cq);
            System.out.println("fish count: " + ((Long) q.getSingleResult()).intValue());
            return ((Long) q.getSingleResult()).intValue();
    }
}
