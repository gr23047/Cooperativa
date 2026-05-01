/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperativa.cooperativa.persistencia;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.cooperativa.cooperativa.control.Cuenta;
import com.cooperativa.cooperativa.control.Asiento;
import com.cooperativa.cooperativa.control.Partida;
import com.cooperativa.cooperativa.persistencia.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author godofredo
 */
public class PartidaJpaController implements Serializable {

    public PartidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

     public PartidaJpaController(){
        emf=Persistence.createEntityManagerFactory("cooperativaPU");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partida partida) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuenta = partida.getCuenta();
            if (cuenta != null) {
                cuenta = em.getReference(cuenta.getClass(), cuenta.getId());
                partida.setCuenta(cuenta);
            }
            Asiento asiento = partida.getAsiento();
            if (asiento != null) {
                asiento = em.getReference(asiento.getClass(), asiento.getId());
                partida.setAsiento(asiento);
            }
            em.persist(partida);
            if (cuenta != null) {
                cuenta.getPartidas().add(partida);
                cuenta = em.merge(cuenta);
            }
            if (asiento != null) {
                asiento.getPartidas().add(partida);
                asiento = em.merge(asiento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partida partida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partida persistentPartida = em.find(Partida.class, partida.getId());
            Cuenta cuentaOld = persistentPartida.getCuenta();
            Cuenta cuentaNew = partida.getCuenta();
            Asiento asientoOld = persistentPartida.getAsiento();
            Asiento asientoNew = partida.getAsiento();
            if (cuentaNew != null) {
                cuentaNew = em.getReference(cuentaNew.getClass(), cuentaNew.getId());
                partida.setCuenta(cuentaNew);
            }
            if (asientoNew != null) {
                asientoNew = em.getReference(asientoNew.getClass(), asientoNew.getId());
                partida.setAsiento(asientoNew);
            }
            partida = em.merge(partida);
            if (cuentaOld != null && !cuentaOld.equals(cuentaNew)) {
                cuentaOld.getPartidas().remove(partida);
                cuentaOld = em.merge(cuentaOld);
            }
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                cuentaNew.getPartidas().add(partida);
                cuentaNew = em.merge(cuentaNew);
            }
            if (asientoOld != null && !asientoOld.equals(asientoNew)) {
                asientoOld.getPartidas().remove(partida);
                asientoOld = em.merge(asientoOld);
            }
            if (asientoNew != null && !asientoNew.equals(asientoOld)) {
                asientoNew.getPartidas().add(partida);
                asientoNew = em.merge(asientoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partida.getId();
                if (findPartida(id) == null) {
                    throw new NonexistentEntityException("The partida with id " + id + " no longer exists.");
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
            Partida partida;
            try {
                partida = em.getReference(Partida.class, id);
                partida.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partida with id " + id + " no longer exists.", enfe);
            }
            Cuenta cuenta = partida.getCuenta();
            if (cuenta != null) {
                cuenta.getPartidas().remove(partida);
                cuenta = em.merge(cuenta);
            }
            Asiento asiento = partida.getAsiento();
            if (asiento != null) {
                asiento.getPartidas().remove(partida);
                asiento = em.merge(asiento);
            }
            em.remove(partida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partida> findPartidaEntities() {
        return findPartidaEntities(true, -1, -1);
    }

    public List<Partida> findPartidaEntities(int maxResults, int firstResult) {
        return findPartidaEntities(false, maxResults, firstResult);
    }

    private List<Partida> findPartidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partida.class));
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

    public Partida findPartida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partida.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partida> rt = cq.from(Partida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
