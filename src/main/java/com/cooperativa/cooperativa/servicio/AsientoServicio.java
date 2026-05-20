package com.cooperativa.cooperativa.servicio;

import com.cooperativa.cooperativa.control.Asiento;
import com.cooperativa.cooperativa.persistencia.AsientoJpaController;
import java.util.List;

public class AsientoServicio {

    AsientoJpaController asientojpa=new AsientoJpaController();
    
    public void crearAsiento(Asiento asiento){
        asientojpa.create(asiento);
    } 

    public List<Asiento> traerAsientos() {
        return asientojpa.findAsientoEntities();
    }
}
