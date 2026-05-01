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
import com.cooperativa.cooperativa.control.Asiento;
import java.util.ArrayList;
import java.util.List;
import com.cooperativa.cooperativa.control.Cierre;
import com.cooperativa.cooperativa.control.Periodo;
import com.cooperativa.cooperativa.persistencia.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author godofredo
 */
public class PeriodoJpaController implements Serializable {

    public PeriodoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

     public PeriodoJpaController(){
        emf=Persistence.createEntityManagerFactory("cooperativaPU");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Periodo periodo) {
        if (periodo.getAsientos() == null) {
            periodo.setAsientos(new ArrayList<Asiento>());
        }
        if (periodo.getCierres() == null) {
            periodo.setCierres(new ArrayList<Cierre>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Asiento> attachedAsientos = new ArrayList<Asiento>();
            for (Asiento asientosAsientoToAttach : periodo.getAsientos()) {
                asientosAsientoToAttach = em.getReference(asientosAsientoToAttach.getClass(), asientosAsientoToAttach.getId());
                attachedAsientos.add(asientosAsientoToAttach);
            }
            periodo.setAsientos(attachedAsientos);
            List<Cierre> attachedCierres = new ArrayList<Cierre>();
            for (Cierre cierresCierreToAttach : periodo.getCierres()) {
                cierresCierreToAttach = em.getReference(cierresCierreToAttach.getClass(), cierresCierreToAttach.getId());
                attachedCierres.add(cierresCierreToAttach);
            }
            periodo.setCierres(attachedCierres);
            em.persist(periodo);
            for (Asiento asientosAsiento : periodo.getAsientos()) {
                Periodo oldPeriodoOfAsientosAsiento = asientosAsiento.getPeriodo();
                asientosAsiento.setPeriodo(periodo);
                asientosAsiento = em.merge(asientosAsiento);
                if (oldPeriodoOfAsientosAsiento != null) {
                    oldPeriodoOfAsientosAsiento.getAsientos().remove(asientosAsiento);
                    oldPeriodoOfAsientosAsiento = em.merge(oldPeriodoOfAsientosAsiento);
                }
            }
            for (Cierre cierresCierre : periodo.getCierres()) {
                Periodo oldPeriodoOfCierresCierre = cierresCierre.getPeriodo();
                cierresCierre.setPeriodo(periodo);
                cierresCierre = em.merge(cierresCierre);
                if (oldPeriodoOfCierresCierre != null) {
                    oldPeriodoOfCierresCierre.getCierres().remove(cierresCierre);
                    oldPeriodoOfCierresCierre = em.merge(oldPeriodoOfCierresCierre);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Periodo periodo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Periodo persistentPeriodo = em.find(Periodo.class, periodo.getId());
            List<Asiento> asientosOld = persistentPeriodo.getAsientos();
            List<Asiento> asientosNew = periodo.getAsientos();
            List<Cierre> cierresOld = persistentPeriodo.getCierres();
            List<Cierre> cierresNew = periodo.getCierres();
            List<Asiento> attachedAsientosNew = new ArrayList<Asiento>();
            for (Asiento asientosNewAsientoToAttach : asientosNew) {
                asientosNewAsientoToAttach = em.getReference(asientosNewAsientoToAttach.getClass(), asientosNewAsientoToAttach.getId());
                attachedAsientosNew.add(asientosNewAsientoToAttach);
            }
            asientosNew = attachedAsientosNew;
            periodo.setAsientos(asientosNew);
            List<Cierre> attachedCierresNew = new ArrayList<Cierre>();
            for (Cierre cierresNewCierreToAttach : cierresNew) {
                cierresNewCierreToAttach = em.getReference(cierresNewCierreToAttach.getClass(), cierresNewCierreToAttach.getId());
                attachedCierresNew.add(cierresNewCierreToAttach);
            }
            cierresNew = attachedCierresNew;
            periodo.setCierres(cierresNew);
            periodo = em.merge(periodo);
            for (Asiento asientosOldAsiento : asientosOld) {
                if (!asientosNew.contains(asientosOldAsiento)) {
                    asientosOldAsiento.setPeriodo(null);
                    asientosOldAsiento = em.merge(asientosOldAsiento);
                }
            }
            for (Asiento asientosNewAsiento : asientosNew) {
                if (!asientosOld.contains(asientosNewAsiento)) {
                    Periodo oldPeriodoOfAsientosNewAsiento = asientosNewAsiento.getPeriodo();
                    asientosNewAsiento.setPeriodo(periodo);
                    asientosNewAsiento = em.merge(asientosNewAsiento);
                    if (oldPeriodoOfAsientosNewAsiento != null && !oldPeriodoOfAsientosNewAsiento.equals(periodo)) {
                        oldPeriodoOfAsientosNewAsiento.getAsientos().remove(asientosNewAsiento);
                        oldPeriodoOfAsientosNewAsiento = em.merge(oldPeriodoOfAsientosNewAsiento);
                    }
                }
            }
            for (Cierre cierresOldCierre : cierresOld) {
                if (!cierresNew.contains(cierresOldCierre)) {
                    cierresOldCierre.setPeriodo(null);
                    cierresOldCierre = em.merge(cierresOldCierre);
                }
            }
            for (Cierre cierresNewCierre : cierresNew) {
                if (!cierresOld.contains(cierresNewCierre)) {
                    Periodo oldPeriodoOfCierresNewCierre = cierresNewCierre.getPeriodo();
                    cierresNewCierre.setPeriodo(periodo);
                    cierresNewCierre = em.merge(cierresNewCierre);
                    if (oldPeriodoOfCierresNewCierre != null && !oldPeriodoOfCierresNewCierre.equals(periodo)) {
                        oldPeriodoOfCierresNewCierre.getCierres().remove(cierresNewCierre);
                        oldPeriodoOfCierresNewCierre = em.merge(oldPeriodoOfCierresNewCierre);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = periodo.getId();
                if (findPeriodo(id) == null) {
                    throw new NonexistentEntityException("The periodo with id " + id + " no longer exists.");
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
            Periodo periodo;
            try {
                periodo = em.getReference(Periodo.class, id);
                periodo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The periodo with id " + id + " no longer exists.", enfe);
            }
            List<Asiento> asientos = periodo.getAsientos();
            for (Asiento asientosAsiento : asientos) {
                asientosAsiento.setPeriodo(null);
                asientosAsiento = em.merge(asientosAsiento);
            }
            List<Cierre> cierres = periodo.getCierres();
            for (Cierre cierresCierre : cierres) {
                cierresCierre.setPeriodo(null);
                cierresCierre = em.merge(cierresCierre);
            }
            em.remove(periodo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Periodo> findPeriodoEntities() {
        return findPeriodoEntities(true, -1, -1);
    }

    public List<Periodo> findPeriodoEntities(int maxResults, int firstResult) {
        return findPeriodoEntities(false, maxResults, firstResult);
    }

    private List<Periodo> findPeriodoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Periodo.class));
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

    public Periodo findPeriodo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Periodo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeriodoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Periodo> rt = cq.from(Periodo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
