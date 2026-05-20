/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cooperativa.cooperativa.persistencia;

import com.cooperativa.cooperativa.control.Cuenta;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.cooperativa.cooperativa.control.Partida;
import com.cooperativa.cooperativa.persistencia.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author godofredo
 */
public class CuentaJpaController implements Serializable {

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public CuentaJpaController() {
        emf = Persistence.createEntityManagerFactory("cooperativaPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuenta cuenta) {
        if (cuenta.getPartidas() == null) {
            cuenta.setPartidas(new ArrayList<Partida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Partida> attachedPartidas = new ArrayList<Partida>();
            for (Partida partidasPartidaToAttach : cuenta.getPartidas()) {
                partidasPartidaToAttach = em.getReference(partidasPartidaToAttach.getClass(), partidasPartidaToAttach.getId());
                attachedPartidas.add(partidasPartidaToAttach);
            }
            cuenta.setPartidas(attachedPartidas);
            em.persist(cuenta);
            for (Partida partidasPartida : cuenta.getPartidas()) {
                Cuenta oldCuentaOfPartidasPartida = partidasPartida.getCuenta();
                partidasPartida.setCuenta(cuenta);
                partidasPartida = em.merge(partidasPartida);
                if (oldCuentaOfPartidasPartida != null) {
                    oldCuentaOfPartidasPartida.getPartidas().remove(partidasPartida);
                    oldCuentaOfPartidasPartida = em.merge(oldCuentaOfPartidasPartida);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getId());
            List<Partida> partidasOld = persistentCuenta.getPartidas();
            List<Partida> partidasNew = cuenta.getPartidas();
            List<Partida> attachedPartidasNew = new ArrayList<Partida>();
            for (Partida partidasNewPartidaToAttach : partidasNew) {
                partidasNewPartidaToAttach = em.getReference(partidasNewPartidaToAttach.getClass(), partidasNewPartidaToAttach.getId());
                attachedPartidasNew.add(partidasNewPartidaToAttach);
            }
            partidasNew = attachedPartidasNew;
            cuenta.setPartidas(partidasNew);
            cuenta = em.merge(cuenta);
            for (Partida partidasOldPartida : partidasOld) {
                if (!partidasNew.contains(partidasOldPartida)) {
                    partidasOldPartida.setCuenta(null);
                    partidasOldPartida = em.merge(partidasOldPartida);
                }
            }
            for (Partida partidasNewPartida : partidasNew) {
                if (!partidasOld.contains(partidasNewPartida)) {
                    Cuenta oldCuentaOfPartidasNewPartida = partidasNewPartida.getCuenta();
                    partidasNewPartida.setCuenta(cuenta);
                    partidasNewPartida = em.merge(partidasNewPartida);
                    if (oldCuentaOfPartidasNewPartida != null && !oldCuentaOfPartidasNewPartida.equals(cuenta)) {
                        oldCuentaOfPartidasNewPartida.getPartidas().remove(partidasNewPartida);
                        oldCuentaOfPartidasNewPartida = em.merge(oldCuentaOfPartidasNewPartida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuenta.getId();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
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
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            List<Partida> partidas = cuenta.getPartidas();
            for (Partida partidasPartida : partidas) {
                partidasPartida.setCuenta(null);
                partidasPartida = em.merge(partidasPartida);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
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

    public Cuenta findCuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Cuenta> findCuentasByTipo(String tipo) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Cuenta c WHERE c.tipo = :tipo ORDER BY c.codigo",
                    Cuenta.class
            )
                    .setParameter("tipo", tipo)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Cuenta> findCuentasByCodido() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Cuenta c WHERE LENGTH(c.codigo) >= 4",
                    Cuenta.class
            ).getResultList();
        } finally {
            em.close();
        }

    }

    public Cuenta findCuentaPorCodigo(String codigo) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Cuenta c WHERE c.codigo = :codigo", 
                    Cuenta.class
            )
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // si no encuentra nada
        } finally {
            em.close();
        }
    }

   

}
