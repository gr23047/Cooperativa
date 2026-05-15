package com.cooperativa.cooperativa.servicio;

import com.cooperativa.cooperativa.control.Periodo;
import com.cooperativa.cooperativa.persistencia.PeriodoJpaController;
import java.util.List;

public class PeriodoServicio {

PeriodoJpaController periodoJPA=new PeriodoJpaController();

public Periodo buscarPeriodo(Integer id){
    return periodoJPA.findPeriodo(id);
}

    public List<Periodo> traerPeriodos() {
        return periodoJPA.findPeriodoEntities();
    }
}
