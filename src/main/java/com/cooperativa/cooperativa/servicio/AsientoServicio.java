package com.cooperativa.cooperativa.servicio;

import com.cooperativa.cooperativa.control.Asiento;
import com.cooperativa.cooperativa.persistencia.AsientoJpaController;

public class AsientoServicio {

    AsientoJpaController asientojpa=new AsientoJpaController();
    
    public void crearAsiento(Asiento asiento){
        asientojpa.create(asiento);
    } 
}
