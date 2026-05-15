package com.cooperativa.cooperativa.servicio;

import com.cooperativa.cooperativa.control.Periodo;
import com.cooperativa.cooperativa.persistencia.PeriodoJpaController;

public class PeriodoServicio {

PeriodoJpaController periodoJPA=new PeriodoJpaController();

public Periodo buscarPeriodo(Integer id){
    return periodoJPA.findPeriodo(id);
}
}
