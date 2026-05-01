/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperativa.cooperativa.persistencia;

import com.cooperativa.cooperativa.control.Asiento;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.cooperativa.cooperativa.control.Periodo;
import com.cooperativa.cooperativa.control.Partida;
import com.cooperativa.cooperativa.persistencia.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author godofredo
 */
public class AsientoJpaController implements Serializable {

    public AsientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

     public AsientoJpaController(){
        emf=Persistence.createEntityManagerFactory("cooperativaPU");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asiento asiento) {
        if (asiento.getPartidas() == null) {
            asiento.setPartidas(new ArrayList<Partida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Periodo periodo = asiento.getPeriodo();
            if (periodo != null) {
                periodo = em.getReference(periodo.getClass(), periodo.getId());
                asiento.setPeriodo(periodo);
            }
            List<Partida> attachedPartidas = new ArrayList<Partida>();
            for (Partida partidasPartidaToAttach : asiento.getPartidas()) {
                partidasPartidaToAttach = em.getReference(partidasPartidaToAttach.getClass(), partidasPartidaToAttach.getId());
                attachedPartidas.add(partidasPartidaToAttach);
            }
            asiento.setPartidas(attachedPartidas);
            em.persist(asiento);
            if (periodo != null) {
                periodo.getAsientos().add(asiento);
                periodo = em.merge(periodo);
            }
            for (Partida partidasPartida : asiento.getPartidas()) {
                Asiento oldAsientoOfPartidasPartida = partidasPartida.getAsiento();
                partidasPartida.setAsiento(asiento);
                partidasPartida = em.merge(partidasPartida);
                if (oldAsientoOfPartidasPartida != null) {
                    oldAsientoOfPartidasPartida.getPartidas().remove(partidasPartida);
                    oldAsientoOfPartidasPartida = em.merge(oldAsientoOfPartidasPartida);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asiento asiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asiento persistentAsiento = em.find(Asiento.class, asiento.getId());
            Periodo periodoOld = persistentAsiento.getPeriodo();
            Periodo periodoNew = asiento.getPeriodo();
            List<Partida> partidasOld = persistentAsiento.getPartidas();
            List<Partida> partidasNew = asiento.getPartidas();
            if (periodoNew != null) {
                periodoNew = em.getReference(periodoNew.getClass(), periodoNew.getId());
                asiento.setPeriodo(periodoNew);
            }
            List<Partida> attachedPartidasNew = new ArrayList<Partida>();
            for (Partida partidasNewPartidaToAttach : partidasNew) {
                partidasNewPartidaToAttach = em.getReference(partidasNewPartidaToAttach.getClass(), partidasNewPartidaToAttach.getId());
                attachedPartidasNew.add(partidasNewPartidaToAttach);
            }
            partidasNew = attachedPartidasNew;
            asiento.setPartidas(partidasNew);
            asiento = em.merge(asiento);
            if (periodoOld != null && !periodoOld.equals(periodoNew)) {
                periodoOld.getAsientos().remove(asiento);
                periodoOld = em.merge(periodoOld);
            }
            if (periodoNew != null && !periodoNew.equals(periodoOld)) {
                periodoNew.getAsientos().add(asiento);
                periodoNew = em.merge(periodoNew);
            }
            for (Partida partidasOldPartida : partidasOld) {
                if (!partidasNew.contains(partidasOldPartida)) {
                    partidasOldPartida.setAsiento(null);
                    partidasOldPartida = em.merge(partidasOldPartida);
                }
            }
            for (Partida partidasNewPartida : partidasNew) {
                if (!partidasOld.contains(partidasNewPartida)) {
                    Asiento oldAsientoOfPartidasNewPartida = partidasNewPartida.getAsiento();
                    partidasNewPartida.setAsiento(asiento);
                    partidasNewPartida = em.merge(partidasNewPartida);
                    if (oldAsientoOfPartidasNewPartida != null && !oldAsientoOfPartidasNewPartida.equals(asiento)) {
                        oldAsientoOfPartidasNewPartida.getPartidas().remove(partidasNewPartida);
                        oldAsientoOfPartidasNewPartida = em.merge(oldAsientoOfPartidasNewPartida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asiento.getId();
                if (findAsiento(id) == null) {
                    throw new NonexistentEntityException("The asiento with id " + id + " no longer exists.");
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
            Asiento asiento;
            try {
                asiento = em.getReference(Asiento.class, id);
                asiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asiento with id " + id + " no longer exists.", enfe);
            }
            Periodo periodo = asiento.getPeriodo();
            if (periodo != null) {
                periodo.getAsientos().remove(asiento);
                periodo = em.merge(periodo);
            }
            List<Partida> partidas = asiento.getPartidas();
            for (Partida partidasPartida : partidas) {
                partidasPartida.setAsiento(null);
                partidasPartida = em.merge(partidasPartida);
            }
            em.remove(asiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asiento> findAsientoEntities() {
        return findAsientoEntities(true, -1, -1);
    }

    public List<Asiento> findAsientoEntities(int maxResults, int firstResult) {
        return findAsientoEntities(false, maxResults, firstResult);
    }

    private List<Asiento> findAsientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asiento.class));
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

    public Asiento findAsiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asiento> rt = cq.from(Asiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
