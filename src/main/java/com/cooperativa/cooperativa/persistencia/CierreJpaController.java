/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperativa.cooperativa.persistencia;

import com.cooperativa.cooperativa.control.Cierre;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.cooperativa.cooperativa.control.Periodo;
import com.cooperativa.cooperativa.persistencia.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author godofredo
 */
public class CierreJpaController implements Serializable {

    public CierreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

     public CierreJpaController(){
        emf=Persistence.createEntityManagerFactory("cooperativaPU");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cierre cierre) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Periodo periodo = cierre.getPeriodo();
            if (periodo != null) {
                periodo = em.getReference(periodo.getClass(), periodo.getId());
                cierre.setPeriodo(periodo);
            }
            em.persist(cierre);
            if (periodo != null) {
                periodo.getCierres().add(cierre);
                periodo = em.merge(periodo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cierre cierre) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cierre persistentCierre = em.find(Cierre.class, cierre.getId());
            Periodo periodoOld = persistentCierre.getPeriodo();
            Periodo periodoNew = cierre.getPeriodo();
            if (periodoNew != null) {
                periodoNew = em.getReference(periodoNew.getClass(), periodoNew.getId());
                cierre.setPeriodo(periodoNew);
            }
            cierre = em.merge(cierre);
            if (periodoOld != null && !periodoOld.equals(periodoNew)) {
                periodoOld.getCierres().remove(cierre);
                periodoOld = em.merge(periodoOld);
            }
            if (periodoNew != null && !periodoNew.equals(periodoOld)) {
                periodoNew.getCierres().add(cierre);
                periodoNew = em.merge(periodoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cierre.getId();
                if (findCierre(id) == null) {
                    throw new NonexistentEntityException("The cierre with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cierre cierre;
            try {
                cierre = em.getReference(Cierre.class, id);
                cierre.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cierre with id " + id + " no longer exists.", enfe);
            }
            Periodo periodo = cierre.getPeriodo();
            if (periodo != null) {
                periodo.getCierres().remove(cierre);
                periodo = em.merge(periodo);
            }
            em.remove(cierre);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cierre> findCierreEntities() {
        return findCierreEntities(true, -1, -1);
    }

    public List<Cierre> findCierreEntities(int maxResults, int firstResult) {
        return findCierreEntities(false, maxResults, firstResult);
    }

    private List<Cierre> findCierreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cierre.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cierre findCierre(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cierre.class, id);
        } finally {
            em.close();
        }
    }

    public int getCierreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cierre> rt = cq.from(Cierre.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
